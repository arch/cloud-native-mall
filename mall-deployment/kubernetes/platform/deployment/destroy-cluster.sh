#!/bin/sh

echo "\n🏴️ Destroying Kubernetes cluster...\n"

minikube stop --profile mall

minikube delete --profile mall

echo "\n🏴️ Cluster destroyed\n"