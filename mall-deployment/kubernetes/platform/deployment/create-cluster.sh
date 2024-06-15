#!/bin/sh

echo "\n📦 Initializing Kubernetes cluster...\n"

minikube start --cpus 2 --memory 6g --driver docker --profile mall

echo "\n🔌 Enabling NGINX Ingress Controller...\n"

minikube addons enable ingress --profile mall

sleep 15

echo "\n📦 Loading Keycloak image into Kubernetes cluster..."

minikube image load quay.io/keycloak/keycloak:25.0.0 --profile mall

echo "\n📦 Deploying Keycloak..."

kubectl apply -f services/keycloak-conf.yml
kubectl apply -f services/keycloak.yml

sleep 5

echo "\n⌛ Waiting for Keycloak to be deployed..."

while [ $(kubectl get pod -l app=mall-keycloak | wc -l) -eq 0 ] ; do
  sleep 2
done

echo "\n⌛ Waiting for Keycloak to be ready..."

kubectl wait \
  --for=condition=ready pod \
  --selector=app=mall-keycloak \
  --timeout=300s

echo "\n⌛ Ensuring Keycloak Ingress is created..."

kubectl apply -f services/keycloak.yml

echo "\n📦 Deploying PostgreSQL..."

kubectl apply -f services/postgresql.yml

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

echo "\n📦 Deploying Redis..."

kubectl apply -f services/redis.yml

sleep 5

echo "\n⌛ Waiting for Redis to be deployed..."

while [ $(kubectl get pod -l db=mall-redis | wc -l) -eq 0 ] ; do
  sleep 2
done

echo "\n⌛ Waiting for Redis to be ready..."

kubectl wait \
  --for=condition=ready pod \
  --selector=db=mall-redis \
  --timeout=180s

echo "\n📦 Deploying RabbitMQ..."

kubectl apply -f services/rabbitmq.yml

sleep 5

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