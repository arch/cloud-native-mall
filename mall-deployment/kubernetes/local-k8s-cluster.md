# Running a Mall cluster

Create a named mall k8s cluster with two cpu cores and 4G memory

```bash
minikube start --cpus 2 --memory 4g --driver docker --profile mall
```

Running dashboard
```bash
minikube dashboard --profile mall
```

Get nodes of the mall cluster

```bash
kubectl get nodes
```

List all context

```bash
kubectl config get-contexts
```

view or set the current context

```bash
kubectl config current-context
```

```bash
kubectl config use-context mall
```

## Stop mall k8s cluster

```bash
minikube stop --profile mall
```

## Running PostgreSQL

step-0: optional, if pull image failed, run
```bash
minikube image load postgres:latest --profile mall
```

step-1: change the work space, pwd
```bash
cd mall-deployment/kubernetes/platform/deployment
```

step-2: run postgres in cluster
```bash
kubectl apply -f services
```