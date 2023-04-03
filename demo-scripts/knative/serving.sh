#!/bin/bash

oc new-project serverless-demo

kn service create springboot-pi-service --image quay.io/rh_ee_ozimakov/springboot-pi-service:1.0
kn service create quarkus-pi-service --image quay.io/rh_ee_ozimakov/quarkus-pi-service:1.0

curl -i https://springboot-pi-service-serverless-demo.apps.rosa-ozimakov.cns5.p1.openshiftapps.com/pi/7
curl -i https://quarkus-pi-service-serverless-demo.apps.rosa-ozimakov.cns5.p1.openshiftapps.com/pi/7

k6 run -e PI_ENDPOINT="https://springboot-pi-service-serverless-demo.apps.rosa-ozimakov.cns5.p1.openshiftapps.com" k6.js
k6 run -e PI_ENDPOINT="https://quarkus-pi-service-serverless-demo.apps.rosa-ozimakov.cns5.p1.openshiftapps.com" k6.js

kn service update quarkus-pi-service --image quay.io/rh_ee_ozimakov/quarkus-pi-service:2.0
kn service update springboot-pi-service --image quay.io/rh_ee_ozimakov/springboot-pi-service:2.0

kn revision list