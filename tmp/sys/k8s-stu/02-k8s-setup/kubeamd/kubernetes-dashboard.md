# dashboard
https://github.com/kubernetes/dashboard

#dashboard rbac
```
#dashboard-rbac.yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: dashboard
  namespace: kube-system

---

kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: dashboard
subjects:
  - kind: ServiceAccount
    name: dashboard
    namespace: kube-system
roleRef:
  kind: ClusterRole
  name: cluster-admin
  apiGroup: rbac.authorization.k8s.io
```


#dashboard access
```
#端口转发，外部可访问
kubectl port-forward kubernetes-dashboard-5bd6f767c7-z7zqt 8443:8443 --namespace=kube-system &

#内置服务，只能本机访问
kubectl proxy
```

#dashboard access token
```
kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | grep dashboard | awk '{print $1}')
```

# access privileges
```
deployments.apps is forbidden: User "system:serviceaccount:default:default" cannot list resource "deployments" in API group "apps" in the namespace "default"
```
##resolved
```
kubectl create rolebinding default-view \
  --clusterrole=view \
  --serviceaccount=default:default \
  --namespace=default

[root@k8s-360-master ~]# kubectl get rolebindings
NAME                       AGE
clusterrolebinding-7c5lh   2d6h
clusterrolebinding-bmxhm   2d5h
clusterrolebinding-f542p   2d6h
clusterrolebinding-ptvlj   2d5h
clusterrolebinding-pzn54   2d6h
clusterrolebinding-t2fr9   2d6h
clusterrolebinding-vpw5z   2d5h
clusterrolebinding-zmw6b   2d5h
default-view               2d4h
You have new mail in /var/spool/mail/root
[root@k8s-360-master ~]# kubectl get clusterrole |grep view
system:aggregate-to-view                                               2d7h
system:public-info-viewer                                              2d7h
view                                                                   2d7h

kubectl get clusterrole view -o yaml
```
```
 kubectl create clusterrolebinding metrics \
  --clusterrole=cluster-admin \
  --group=system:serviceaccounts

 kubectl create clusterrolebinding metrics \
  --clusterrole=cluster-admin \
  --user=system:serviceaccount:default:default
```

# restart kube-proxy pods
```
kubectl delete pod -n kube-system --force --grace-period=0 $( kubectl get pod -n kube-system | grep kube-proxy | awk '{printf "%s ", $1}' ) 
kubectl delete pod -n kube-system --force --grace-period=0 $( kubectl get pod -n kube-system | grep calico-node | awk '{printf "%s ", $1}' ) 
kubectl delete pod -n kube-system --force --grace-period=0 $( kubectl get pod -n kube-system | grep coredns | awk '{printf "%s ", $1}' ) 
kubectl delete pod -n kube-system --force --grace-period=0 $( kubectl get pod -n kube-system | grep kubernetes-dashboard | awk '{printf "%s ", $1}' ) 
```

## dahsboard-ingress
https://blog.csdn.net/catoop/article/details/105046078



