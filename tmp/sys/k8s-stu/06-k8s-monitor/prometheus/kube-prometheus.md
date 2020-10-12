# Prometheus Operator
#kube-prometheus
https://github.com/coreos/kube-prometheus


[link](https://www.jianshu.com/p/0e4981381507)

#v1.18.x
https://codeload.github.com/coreos/kube-prometheus/tar.gz/v0.5.0


#Create the namespace and CRDs, and then wait for them to be availble before creating the remaining resources

kubectl create -f manifests/setup
```
pulled image "quay.io/coreos/prometheus-operator:v0.38.1"
pull image "quay.io/coreos/kube-rbac-proxy:v0.4.1"
```

kubectl create -f manifests/
```
Pulling image "quay.io/prometheus/alertmanager:v0.20.0"
Pulling image "grafana/grafana:6.6.0"
Pulling image "quay.io/coreos/kube-state-metrics:v1.9.5"
Pulling image "quay.io/prometheus/node-exporter:v0.18.1"
```


```
ubuntu@u-3-60:~/prometheus/kube-prometheus-0.5.0$ grep 'image' manifests/*

image: quay.io/prometheus/alertmanager:v0.20.0
image: grafana/grafana:6.6.0
image: quay.io/coreos/kube-state-metrics:v1.9.5
image: quay.io/coreos/kube-rbac-proxy:v0.4.1
image: quay.io/coreos/kube-rbac-proxy:v0.4.1
image: quay.io/prometheus/node-exporter:v0.18.1
image: quay.io/coreos/kube-rbac-proxy:v0.4.1
image: quay.io/coreos/k8s-prometheus-adapter-amd64:v0.5.0
image: quay.io/prometheus/prometheus:v2.15.2
```

```
ubuntu@u-3-60:~/prometheus/kube-prometheus-0.5.0$ kubectl -n monitoring get pods -o wide
NAME                                   READY   STATUS    RESTARTS   AGE   IP             NODE           NOMINATED NODE   READINESS GATES
alertmanager-main-0                    2/2     Running   0          18m   10.42.0.12     192.168.3.61   <none>           <none>
alertmanager-main-1                    2/2     Running   0          17m   10.42.1.11     192.168.3.60   <none>           <none>
alertmanager-main-2                    2/2     Running   0          39m   10.42.0.9      192.168.3.61   <none>           <none>
grafana-5c55845445-4nwhv               1/1     Running   0          39m   10.42.1.9      192.168.3.60   <none>           <none>
kube-state-metrics-957fd6c75-qln24     3/3     Running   0          39m   10.42.1.8      192.168.3.60   <none>           <none>
node-exporter-2wvnw                    2/2     Running   0          16s   192.168.3.61   192.168.3.61   <none>           <none>
node-exporter-8q7rt                    2/2     Running   0          39m   192.168.3.60   192.168.3.60   <none>           <none>
prometheus-adapter-5949969998-qdnx6    1/1     Running   0          10m   10.42.0.14     192.168.3.61   <none>           <none>
prometheus-k8s-0                       3/3     Running   1          39m   10.42.0.11     192.168.3.61   <none>           <none>
prometheus-k8s-1                       3/3     Running   1          39m   10.42.1.10     192.168.3.60   <none>           <none>
prometheus-operator-574fd8ccd9-f7gdz   2/2     Running   0          72m   10.42.1.6      192.168.3.60   <none>           <none>
ubuntu@u-3-60:~/prometheus/kube-prometheus-0.5.0$

```

quay.io/prometheus/prometheus:v2.15.2
docker pull quay.mirrors.ustc.edu.cn/prometheus/prometheus:v2.15.2
docker tag quay.mirrors.ustc.edu.cn/prometheus/prometheus:v2.15.2  quay.io/prometheus/prometheus:v2.15.2