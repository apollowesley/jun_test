# ingress-nginx
https://github.com/kubernetes/ingress-nginx
https://github.com/kubernetes/ingress-nginx/blob/master/docs/deploy/index.md

https://kubernetes.github.io/ingress-nginx/

>Ingress controller for Kubernetes using NGINX as a reverse proxy and load balancer

## bare-metal
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/static/provider/baremetal/deploy.yaml

##error
```
[root@k8s-master ingress]# grep image deploy.yaml 
          image: us.gcr.io/k8s-artifacts-prod/ingress-nginx/controller:v0.34.0@sha256:56633bd00dab33d92ba14c6e709126a76
2d54a75a6e72437adefeaaca0abb069          imagePullPolicy: IfNotPresent
          image: docker.io/jettech/kube-webhook-certgen:v1.2.2
          imagePullPolicy: IfNotPresent
          image: docker.io/jettech/kube-webhook-certgen:v1.2.2
          imagePullPolicy: IfNotPresent
[root@k8s-master ingress]


docker pull  quay.io/kubernetes-ingress-controller/nginx-ingress-controller:0.33.0

docker pull  quay.io/ingress-nginx/controller:v0.34.0
```

```
kubectl get pods -n ingress-nginx \
  -l app.kubernetes.io/name=ingress-nginx --watch
```


## metalb
https://metallb.universe.tf/installation/
https://github.com/metallb/metallb

https://zhuanlan.zhihu.com/p/103717169?utm_source=wechat_session
https://blog.csdn.net/networken/article/details/85928369
https://blog.csdn.net/networken/article/details/85928369
https://www.jianshu.com/p/d15ce6087e44

kubectl apply -f https://raw.githubusercontent.com/google/metallb/v0.8.3/manifests/metallb.yaml


```
$ cat metallb-config.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  namespace: kube-system
  name: metallb-config
data:
  config: |
    address-pools:
    - name: default
      protocol: layer2
      addresses:
      - 192.168.3.230-192.168.3.240

$ kubectl apply -f metallb-config.yaml
```
https://kubernetes.cn/docs/concepts/services-networking/ingress-controllers/
https://blog.csdn.net/bbwangj/article/details/82863042

https://blog.csdn.net/bbwangj/article/details/82940419
https://blog.csdn.net/weixin_34345560/article/details/92530840
https://blog.csdn.net/weixin_41806245/article/details/91348883
https://blog.csdn.net/bbwangj/article/details/81776597
https://blog.csdn.net/bbwangj/article/details/82558076
https://blog.csdn.net/weixin_41806245/article/details/91348535


https://blog.csdn.net/wangshuminjava/article/details/106047601
https://blog.csdn.net/shm19990131/article/details/106784758
https://blog.csdn.net/wangshuminjava/article/details/106047441
https://blog.csdn.net/wangshuminjava/article/details/106003491
https://blog.csdn.net/wangshuminjava/article/details/106002872


#ingress basic auth
https://www.jianshu.com/p/1e47ea4934db