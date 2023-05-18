#!/bin/bash

kn func create -c
kn func deploy -c

oc apply -f https://raw.githubusercontent.com/openshift-knative/kn-plugin-func/serverless-1.28.0/pipelines/resources/tekton/task/func-s2i/0.1/func-s2i.yaml
oc apply -f https://raw.githubusercontent.com/openshift-knative/kn-plugin-func/serverless-1.28.0/pipelines/resources/tekton/task/func-deploy/0.1/func-deploy.yaml

kn func deploy --remote
curl -i https://quarkus-pi-func-serving-demo.apps.rosa-ozimakov2.qhxt.p1.openshiftapps.com/ -H "Content-Type: application/json" -d "{ \"n\": 7 }"