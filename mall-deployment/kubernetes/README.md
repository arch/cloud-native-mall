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
http POST 127.0.0.1/orders isbn=1234567891 quantity=3
```

verify order dispatch status is DISPATCHED
```bash
http 127.0.0.1/orders
```

## Running after integration Spring Security

create a tunnel to k8s if running on macOS
```bash
minikube tunnel --profile mall
```

trigger [Login](http://localhost:9000/user) by Browser:

Get the Cookie session id, such as
```bash
SESSION=586464fd-82a8-433f-8a26-5c07817a7f02
```

[HTTPie](https://httpie.io/docs/cli/cookies) supports send cookies to the server as regular HTTP headers.

```bash
http 127.0.0.1/books Cookie:name=name
```

test message dispatching
```bash
http POST 127.0.0.1/orders isbn=1234567891 quantity=3 Cookie:name=value
```

verify order dispatch status is DISPATCHED
```bash
http 127.0.0.1/orders Cookie:name=value
```