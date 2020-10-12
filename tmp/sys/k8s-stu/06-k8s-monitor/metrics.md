

//k8s metrics
kubectl get --raw /metrics


## Kubernetes Metrics Server
https://github.com/kubernetes-sigs/metrics-server
```
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/download/v0.3.6/components.yaml

kubectl get deployment metrics-server -n kube-system
```