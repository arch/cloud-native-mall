# Inspect a Mall cluster

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

# Load image into Kubernetes cluster

```bash
minikube image load postgres:latest --profile mall
```

# Test with Ingress

macOS create a tunnel to k8s
```bash
minikube tunnel --profile mall
```

test REST api in a new terminal
```bash
http 127.0.0.1/books
```

test message dispatching
```bash
http POST 127.0.0.1/orders  isbn=1234567891 quantity=3
```

verify order dispatch status is DISPATCHED
```bash
http 127.0.0.1/orders
```