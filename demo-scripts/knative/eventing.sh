#!/bin/bash

# NO-RUN, USE AS A STEP-BY-STEP
exit

# PREPARE
oc new-project serverless-eventing-demo

# BASIC WIRING
kn service create ping-event-consumer --image gcr.io/knative-releases/knative.dev/eventing-contrib/cmd/event_display --scale-window=10s
curl -X 'POST' https://ping-event-consumer-serverless-eventing-demo.apps.rosa-ozimakov.cns5.p1.openshiftapps.com -H 'Ce-Id: say-hello' -H 'Ce-Specversion: 1.0' -H 'Ce-Type: my-type' -H 'Ce-Source: mycurl' -H 'Content-Type: application/json' -d '{msg:Hello}'
kn source ping create ping-event-producer --schedule '* * * * *' --data '{ value: Ping }' --sink ksvc:ping-event-consumer

# CHANNELS & SUBS
kn channel create my-channel
kn source ping update ping-event-producer --sink channel:my-channel
kn subscription create event-consumer-sub --channel my-channel --sink ksvc:ping-event-consumer

kn service create pong-event-consumer --image gcr.io/knative-releases/knative.dev/eventing-contrib/cmd/event_display --scale-window=10s
kn subscription create event-consumer-sub2 --channel my-channel --sink ksvc:pong-event-consumer
kn source ping create pong-event-producer --schedule '* * * * *' --data '{ value: Pong }' --sink channel:my-channel

kn channel delete my-channel

# BROKERS & TRIGGERS
oc apply -f k8s/kafka/kafka.yaml
oc describe kafka kafka-serverless
#  check bootstrap url
oc apply -f k8s/kafka/kafkabroker.yaml
kn broker list
# show the topic
oc get kafkatopic

kn source ping update ping-event-producer --sink broker:kafka-broker --ce-override messagetype=ping
kn source ping update pong-event-producer --sink broker:kafka-broker --ce-override messagetype=pong

# check the topic
oc run kafka-consumer -ti \
--image=registry.redhat.io/amq7/amq-streams-kafka-33-rhel8:2.3.0 \
--rm=true \
--restart=Never \
-- bin/kafka-console-consumer.sh \
--bootstrap-server kafka-serverless-kafka-bootstrap.serverless-eventing-demo.svc:9092 \
--topic knative-broker-serverless-eventing-demo-kafka-broker \
--from-beginning --property print.headers=true

kn trigger create ping-trigger --broker kafka-broker --filter messagetype=ping --sink ksvc:ping-event-consumer
kn trigger create pong-trigger --broker kafka-broker --filter messagetype=pong --sink ksvc:pong-event-consumer

kn service create all-event-consumer --image gcr.io/knative-releases/knative.dev/eventing-contrib/cmd/event_display --scale-window=2m
kn trigger create all-trigger --broker kafka-broker --sink ksvc:all-event-consumer

# CLEANUP
oc delete project serverless-eventing-demo