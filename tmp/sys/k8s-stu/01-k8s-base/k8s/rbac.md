# rbcc(role base access )
https://docs.aws.amazon.com/zh_cn/eks/latest/userguide/dashboard-tutorial.html

[link](https://blog.csdn.net/shenhonglei1234/article/details/106225983)
https://www.jianshu.com/p/fb071a6516e1
https://blog.csdn.net/weixin_43384009/article/details/105980976
https://www.jianshu.com/p/9991f189495f

```
role--->rolebinding
clusterrole --->clusterbinding
user
group
serviceaccount
```

#clusterrole
```
[root@k8s-360-master ~]# kubectl get clusterrole  //exist default clusterrole
cluster-admin
edit
view


[root@k8s-360-master ~]# kubectl get clusterrole cluster-admin -o yaml    //superrole
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  annotations:
    rbac.authorization.kubernetes.io/autoupdate: "true"
  creationTimestamp: "2020-07-06T02:19:51Z"
  labels:
    kubernetes.io/bootstrapping: rbac-defaults
  name: cluster-admin
  resourceVersion: "43"
  selfLink: /apis/rbac.authorization.k8s.io/v1/clusterroles/cluster-admin
  uid: 2bfd9227-bf2f-11ea-9c8d-0800271aec30
rules:
- apiGroups:
  - '*'
  resources:
  - '*'
  verbs:
  - '*'
- nonResourceURLs:
  - '*'
  verbs:
  - '*'
```


#add rbac cluster-admin
```
kubectl create serviceaccount  dashboard-admin -n kubernetes-dashboard
kubectl create clusterrolebinding  dashboard-admin --clusterrole=cluster-admin --serviceaccount=kubernetes-dashboard:dashboard-admin

kubectl describe secrets -n kubernetes-dashboard $(kubectl -n kubernetes-dashboard get secret | awk '/dashboard-admin/{print $1}')
```

#有点小问题
```
[root@c-3-102 ~]# kubectl create clusterrolebinding serviceaccount-cluster-admin \
   --clusterrole=cluster-admin \
   --user=system:serviceaccount:kubernetes-dashboard:kubernetes-dashboard

[root@c-3-102 ~]# kubectl delete  clusterrolebinding serviceaccount-cluster-admin

kubectl create clusterrolebinding permissive-binding \
  --clusterrole=cluster-admin \
  --user=admin \
  --user=kubelet \
  --group=system:serviceaccounts
```

#create role
kubectl create role -h
```
Available Commands:
  clusterrole         Create a ClusterRole.
  clusterrolebinding  Create a ClusterRoleBinding for a particular ClusterRole
  configmap           Create a configmap from a local file, directory or literal value
  cronjob             Create a cronjob with the specified name.
  deployment          Create a deployment with the specified name.
  job                 Create a job with the specified name.
  namespace           Create a namespace with the specified name
  poddisruptionbudget Create a pod disruption budget with the specified name.
  priorityclass       Create a priorityclass with the specified name.
  quota               Create a quota with the specified name.
  role                Create a role with single rule.
  rolebinding         Create a RoleBinding for a particular Role or ClusterRole
  secret              Create a secret using specified subcommand
  service             Create a service using specified subcommand.
  serviceaccount      Create a service account with the specified name
```

#rbac应用
>Role --> User -->Rolebinding  角色授权的流程

kubectl create进行创建角色(role)，指定角色名称，–verb指定权限，–resource指定资源或者资源组，–dry-run：此模式不会真的创建

kubectl create role pods-reader --verb=get,list,watch --resource=pods --dry-run -o yaml
kubectl create role pod-reader --verb=get --resource=pods --resource-name=readablepod --resource-name=anotherpod  --dry-run -o yaml
kubectl create role foo --verb=get,list,watch --resource=pods,pods/status --dry-run -o yaml

01、create role/user
kubectl create role pods-reader --verb=get,list,watch --resource=pods --resource-name=readablepod --dry-run -o yaml >pods-reader.yaml

[root@k8s-master ~]# kubectl apply -f pods-reader.yaml 
role.rbac.authorization.k8s.io/pod-reader created
[root@k8s-master ~]# 
[root@k8s-master ~]# kubectl get role
NAME         AGE
pods-reader   10s
[root@k8s-master ~]# kubectl get role pods-reader -o yaml

02、rolebinding
```
[root@k8s-master ~]# kubectl create rolebinding  -h

#Create a RoleBinding for user1, user2, and group1 using the admin ClusterRole
kubectl create rolebinding admin --clusterrole=admin --user=user1 --user=user2 --group=group1

kubectl create rolebinding NAME --clusterrole=NAME|–role=NAME [–user=username] [–group=groupname]
>kubectl create进行创建角色绑定，指定角色绑定的名称，–role|–clusterrole指定绑定哪个角色，–user指定哪个用户

kubectl create rolebinding NAME --clusterrole=NAME|--role=NAME [--user=username] [--group=groupname]
[--serviceaccount=namespace:serviceaccountname] [--dry-run] [options]
```

#导出rolebinding资源定义清单文件
kubectl explain role　　#查看资源定义清单字段
kubectl explain rolebinding　　#查看资源定义清单字段
kubectl create rolebinding test-read-pods --role=pods-reader --user=test --dry-run -o yaml > rolebinding-demo.yaml

```
[root@k8s-master sb]# kubectl apply -f rolebinding-demo.yaml 
[root@k8s-master sb]# kubectl get rolebinding
NAME                       AGE
clusterrolebinding-9qrlz   11d
clusterrolebinding-kcxfp   11d
clusterrolebinding-ppt46   11d
clusterrolebinding-z2ckg   11d
test-read-pods             34s
[root@k8s-master sb]# kubectl describe rolebinding/test-read-pods
Name:         test-read-pods
Labels:       <none>
Annotations:  kubectl.kubernetes.io/last-applied-configuration:
                {"apiVersion":"rbac.authorization.k8s.io/v1","kind":"RoleBinding","metadata":{"annotations":{},"creationTimestamp":null,"name":"te
st-read-...Role:
  Kind:  Role
  Name:  pods-reader  //绑定的角色
Subjects:
  Kind  Name  Namespace
  ----  ----  ---------
  User  test    //绑定的用户
```

#绑定集群角色
>ClusterRole–>ClusterRoleBinding–>User

kubectl explain clusterrole　　#查看资源定义清单字段
kubectl explain clusterrolebinding　　#查看资源定义清单字段

#导出clusterrole的资源定义清单文件
kubectl create clusterrole cluster-read --verb=get,list,watch --resource=pods -o yaml >clusterrole-demo.yaml
[root@k8s-master sb]# kubectl apply -f clusterrole-demo.yaml 

#查看
kubectl config view
```
[root@k8s-master sb]# kubectl config view  //当前kubectl配置
apiVersion: v1
clusters:
- cluster:
    certificate-authority-data: DATA+OMITTED
    server: https://10.24.3.9:6443
  name: kubernetes
contexts:
- context:
    cluster: kubernetes
    user: kubernetes-admin
  name: kubernetes-admin@kubernetes
current-context: kubernetes-admin@kubernetes
kind: Config
preferences: {}
users:
- name: kubernetes-admin
  user:
    client-certificate-data: REDACTED
    client-key-data: REDACTED
```

导出yaml资源定义清单文件：
kubectl create clusterrolebinding test-read-all-pods --clusterrole=cluster-read --user=test --dry-run -o yaml >clusterrolebinding-demo.yaml

```
[root@k8s-master sb]# kubectl apply -f clusterrolebinding-demo.yaml 
[root@k8s-master sb]# kubectl get clusterrole |grep cluster
cluster-admin                                                          11d
cluster-owner                                                          11d
cluster-read                                                           4m27s
proxy-clusterrole-kubeapiserver                                        11d
system:controller:clusterrole-aggregation-controller                   11d
```

#clusterrole --> rolebinding --> user
#删除之前的clusterrolebinding
kubectl delete clusterrolebinding test-read-all-pods
#导出yaml资源定义清单文件
kubectl create rolebinding test-read-pods --clusterrole=cluster-read --user=test --dry-run -o yaml >rolebinding-clusterrole-dmeo.yaml
#创建rolebinding，将test绑定到clusterrole
kubectl apply -f rolebinding-clusterrole-dmeo.yaml
#查看rolebinding
kubectl  get rolebinding test-read-pods
kubectl  describe rolebinding test-read-pods
#切换账号测试知test可以访问当前namespace的pod，但是不能访问其他namespace的pod;
因为这种绑定方式，clusterrole是被降级的；


RBAC不仅可以对user进行访问权限的控制，还可以通过group和serviceaccount进行访问权限控制。user即单个用户，group是对一个组内的user进行授权；通过RBAC对serviceaccount进行访问授权时，就可以实现Pod对其他资源的访问权限进行控制。也就是说，当我们对serviceaccount进行rolebinding或clusterrolebinding，会使创建Pod拥有对应角色的权限和apiserver进行通信。


#kubectl切换context
[root@k8s-master sb]# kubectl config --help




```
apiVersion: v1
kind: ServiceAccount
metadata:
  name: admin-user
  namespace: kube-system


---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: admin-user
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
- kind: ServiceAccount
  name: admin-user
  namespace: kube-system


kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | grep admin-user | awk '{print $1}')

```



```


2020/05/25 10:12:00 Non-critical error occurred during resource retrieval: namespaces is forbidden: User "system:serviceaccount:kubernetes-dashboard:kubernetes-dashboard" cannot list resource "namespaces" in API group "" at the cluster scope


//rbac
kubectl create clusterrolebinding serviceaccount-cluster-admin --clusterrole=cluster-admin --group=system:serviceaccount 

kubectl create clusterrolebinding serviceaccount-cluster-admin --clusterrole=cluster-admin --user=system:serviceaccount 


[root@c-3-100 ~]# kubectl  api-resources  |grep clusterrolebinding
clusterrolebindings                            rbac.authorization.k8s.io      false        ClusterRoleBinding
[root@c-3-100 ~]# 



[root@c-3-100 ~]# kubectl create clusterrolebinding --help
Create a ClusterRoleBinding for a particular ClusterRole.

Examples:
  # Create a ClusterRoleBinding for user1, user2, and group1 using the cluster-admin ClusterRole
  kubectl create clusterrolebinding cluster-admin --clusterrole=cluster-admin --user=user1 --user=user2 --group=group1

Options:
      --allow-missing-template-keys=true: If true, ignore any errors in templates when a field or map key is missing in
the template. Only applies to golang and jsonpath output formats.
      --clusterrole='': ClusterRole this ClusterRoleBinding should reference
      --dry-run='none': Must be "none", "server", or "client". If client strategy, only print the object that would be
sent, without sending it. If server strategy, submit server-side request without persisting the resource.
      --group=[]: Groups to bind to the clusterrole
  -o, --output='': Output format. One of:
json|yaml|name|go-template|go-template-file|template|templatefile|jsonpath|jsonpath-file.
      --save-config=false: If true, the configuration of current object will be saved in its annotation. Otherwise, the
annotation will be unchanged. This flag is useful when you want to perform kubectl apply on this object in the future.
      --serviceaccount=[]: Service accounts to bind to the clusterrole, in the format <namespace>:<name>
      --template='': Template string or path to template file to use when -o=go-template, -o=go-template-file. The
template format is golang templates [http://golang.org/pkg/text/template/#pkg-overview].
      --validate=true: If true, use a schema to validate the input before sending it

Usage:
  kubectl create clusterrolebinding NAME --clusterrole=NAME [--user=username] [--group=groupname]
[--serviceaccount=namespace:serviceaccountname] [--dry-run=server|client|none] [options]

Use "kubectl options" for a list of global command-line options (applies to all commands).
[root@c-3-100 ~]# 
[root@c-3-100 ~]# 


[root@c-3-100 ~]# 
[root@c-3-100 ~]# kubectl delete clusterrolebinding serviceaccount-cluster-admin
clusterrolebinding.rbac.authorization.k8s.io "serviceaccount-cluster-admin" deleted
[root@c-3-100 ~]# 
[root@c-3-100 ~]# 
[root@c-3-100 ~]# kubectl create clusterrolebinding serviceaccount-cluster-admin --clusterrole=cluster-admin --user=system:serviceaccount 

2020/05/25 10:43:36 Non-critical error occurred during resource retrieval: nodes is forbidden: 
User "system:serviceaccount:kubernetes-dashboard:kubernetes-dashboard" 
cannot list resource "nodes" in API group "" at the cluster scope: RBAC: clusterrole.rbac.authorization.k8s.io "kubernetes-dashboard" not found

kubectl create clusterrolebinding serviceaccount-cluster-admin --clusterrole=cluster-admin


//ok
kubectl create clusterrolebinding serviceaccount-cluster-admin --clusterrole=cluster-admin --user=system:serviceaccount:kubernetes-dashboard:kubernetes-dashboard


//token
[root@c-3-100 ~]# kubectl get secrets -n kubernetes-dashboard
NAME                               TYPE                                  DATA   AGE
default-token-cvl6b                kubernetes.io/service-account-token   3      6m38s
kubernetes-dashboard-certs         Opaque                                0      6m38s
kubernetes-dashboard-csrf          Opaque                                1      6m38s
kubernetes-dashboard-key-holder    Opaque                                2      6m38s
kubernetes-dashboard-token-h8tnd   kubernetes.io/service-account-token   3      6m38s
[root@c-3-100 ~]# 


kubectl describe secrets -n kubernetes-dashboard kubernetes-dashboard-token-fj8ww   | grep token | awk 'NR==3{print $2}'



[root@c-3-100 ~]# kubectl describe secrets -n kubernetes-dashboard kubernetes-dashboard-token-h8tnd    | grep token | awk 'NR==3{print $2}'


[root@c-3-100 ~]# kubectl get secrets -n kubernetes-dashboard
NAME                               TYPE                                  DATA   AGE
default-token-cvl6b                kubernetes.io/service-account-token   3      10m
kubernetes-dashboard-certs         Opaque                                0      10m
kubernetes-dashboard-csrf          Opaque                                1      10m
kubernetes-dashboard-key-holder    Opaque                                2      10m
kubernetes-dashboard-token-h8tnd   kubernetes.io/service-account-token   3      10m

[root@c-3-100 ~]# kubectl get pods -A
NAMESPACE              NAME                                         READY   STATUS    RESTARTS   AGE
kube-system            calico-kube-controllers-789f6df884-ns67c     1/1     Running   0          20m
kube-system            calico-node-jkkwt                            1/1     Running   0          20m
kube-system            calico-node-mjqlj                            1/1     Running   0          20m
kube-system            coredns-7ff77c879f-lj2c9                     1/1     Running   0          43m
kube-system            coredns-7ff77c879f-tgf89                     1/1     Running   0          43m
kube-system            etcd-c-3-100                                 1/1     Running   1          43m
kube-system            kube-apiserver-c-3-100                       1/1     Running   1          43m
kube-system            kube-controller-manager-c-3-100              1/1     Running   3          43m
kube-system            kube-proxy-64j6k                             1/1     Running   1          32m
kube-system            kube-proxy-sqbzl                             1/1     Running   1          43m
kube-system            kube-scheduler-c-3-100                       1/1     Running   3          43m
kubernetes-dashboard   dashboard-metrics-scraper-6b4884c9d5-7qrhd   1/1     Running   0          11m
kubernetes-dashboard   kubernetes-dashboard-7bfbb48676-xrbsx        1/1     Running   0          11m

kubectl logs -f -n kubernetes-dashboard kubernetes-dashboard-7bfbb48676-z76b5

kubectl create clusterrolebinding serviceaccount-cluster-admin  –clusterrole=cluster-admin  –-user=system:serviceaccount:kubernetes-dashboard:kubernetes-dashboard
```

<<<<<<< HEAD
## rbac
https://www.jianshu.com/p/9991f189495f
=======

```


https://blog.csdn.net/wangshuminjava/article/details/92794687
https://blog.csdn.net/wangshuminjava/article/details/92796303
https://blog.csdn.net/bbwangj/article/details/81835553
https://blog.csdn.net/bbwangj/article/details/85059718
https://blog.csdn.net/bbwangj/article/details/81835562


## token
https://blog.csdn.net/wangshuminjava/article/details/92791083
https://blog.csdn.net/wangshuminjava/article/details/92790990
>>>>>>> 8c788b937b5e8131fdb55609a3d1bfd7c3193cac
