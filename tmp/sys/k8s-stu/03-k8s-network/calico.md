# calico
https://blog.csdn.net/bbwangj/article/details/82669369


```
wget http://docs.projectcalico.org/v3.2/getting-started/kubernetes/installation/hosted/calico.yaml

[root@k8s-master-51 ~]# cat calico.yaml |grep image
image: quay.io/calico/node:v3.2.4
image: quay.io/calico/cni:v3.2.4
image: quay.io/calico/kube-controllers:v3.2.4

#calico.yaml
- name: CALICO_IPV4POOL_CIDR   //pod cidr相同
    value: "10.253.0.0/18"
```

kubectl apply -f https://docs.projectcalico.org/manifests/calico.yaml




https://blog.csdn.net/wangshuminjava/article/details/89323065