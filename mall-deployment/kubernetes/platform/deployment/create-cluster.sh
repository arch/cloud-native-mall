#!/bin/sh

echo "\n📦 Initializing Kubernetes cluster...\n"

minikube start --cpus 4 --memory 8g --driver docker --profile mall

echo "\n🔌 Enabling NGINX Ingress Controller...\n"

minikube addons enable ingress --profile mall

sleep 15

echo "\n📦 Deploying platform services..."

kubectl apply -f services

sleep 5

echo "\n⌛ Waiting for PostgreSQL to be deployed..."

while [ $(kubectl get pod -l db=mall-postgres | wc -l) -eq 0 ] ; do
  sleep 2
done

echo "\n⌛ Waiting for PostgreSQL to be ready..."

kubectl wait \
  --for=condition=ready pod \
  --selector=db=mall-postgres \
  --timeout=180s

echo "\n⌛ Waiting for Redis to be deployed..."

while [ $(kubectl get pod -l db=mall-redis | wc -l) -eq 0 ] ; do
  sleep 2
done

echo "\n⌛ Waiting for Redis to be ready..."

kubectl wait \
  --for=condition=ready pod \
  --selector=db=mall-redis \
  --timeout=180s

echo "\n⌛ Waiting for RabbitMQ to be deployed..."

while [ $(kubectl get pod -l db=mall-rabbitmq | wc -l) -eq 0 ] ; do
  sleep 2
done

echo "\n⌛ Waiting for RabbitMQ to be ready..."

kubectl wait \
  --for=condition=ready pod \
  --selector=db=mall-rabbitmq \
  --timeout=180s

echo "\n⛵ Enabling dashboard...\n"

minikube dashboard --profile mall