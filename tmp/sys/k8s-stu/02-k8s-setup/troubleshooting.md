# google_containers_registry
```
k8s.gcr.io
registry.aliyuncs.com/google_containers
```

https://blog.csdn.net/wangshuminjava/article/details/91381207
##
https://blog.csdn.net/wangshuminjava/article/details/102798495

# kubectl故障排查相关命令
[link](https://blog.csdn.net/bbwangj/article/details/82793013)

- version    显示客户端服务端版本信息
- api-versions  以group/version格式显示服务器支持的api版本
- api-resources group的简称
- explain 显示资源文档信息
- get 取得对象列表
- describe 取得对象的详细信息
- logs 取得对象的日志
- exec 在pod中执行命令
- cp  对容器copy文件
  
>注意：kubernetes的组件注意版本的一致性，或者小幅度的震荡的
```
[root@k8s-master ~]# kubectl version -o yaml

[root@k8s-master ~]# kubectl api-resources --namespaced=true //区分namespace
[root@k8s-master ~]# kubectl api-resources --namespaced=false //不区分namespace 有状态的服务pv/

[root@k8s-master ~]# kubectl explain deployment  //解释说明kind的参数

[root@k8s-master ~]# kubectl cluster-info  //api-servers信息

[root@k8s-master ~]# kubectl get pods -A -o wide  // -A all-namespaces  -o wide  detial info

[root@k8s-master ~]# kubectl -n kubernetes-dashboard get pods

[root@k8s-master ~]# kubectl -n kubernetes-dashboard get deployment   //查看nnamespace下的deployment

[root@k8s-master ~]# kubectl -n kubernetes-dashboard get deployment kubernetes-dashboard -o yaml   //查看具体deployment的信息 -o yaml导出
```

#explain 信息类别及简称
```
clusters (仅对federation apiservers有效)
componentstatuses (缩写 cs)
configmaps (缩写 cm)
daemonsets (缩写 ds)
deployments (缩写 deploy)
endpoints (缩写 ep)
events (缩写 ev)
hori
zontalpodautoscalers (缩写 hpa)
ingresses (缩写 ing)
jobs
limitranges (缩写 limits)
namespaces (缩写 ns)
networkpolicies
nodes (缩写 no)
persistentvolumes (缩写 pv)
persistentvolumeclaims (缩写 pvc)
pods (缩写 po)
podsecuritypolicies (缩写 psp)
podtemplates
replicasets (缩写 rs)
replicationcontrollers (缩写 rc)
resourcequotas (缩写 quota)
secrets
serviceaccounts (缩写 sa)
services (缩写 svc)
statefulsets
storageclasses
thirdpartyresources
```

#kubectl 编辑扩缩
- edit 编辑服务器侧资源、deployment等，有些编辑后无法更新如pod等
- replace 替换运行的资源
- path 更新部分资源
- apply 更新应用资源yaml
- scale
- autoscal
- cordon 设定node不可用
- uncordon 设定node可以使用
- drain 设定node进入维护模式

```
#edit
kubectl edit service nginx //edit sevice 可以实时生效

#replace
kubectl get service nginx -o yaml >nginx_forreplace.yaml //运行时的配置导出，修改后再replace上去即可生效
kubectl replace -f nginx_forreplace.yaml

#patch
kubectl patch pod nginx-2476590065-1vtsp -p '{"spec":{"containers":[{"name":"nginx","image":"192.168.32.131:5000/nginx:1.13-alpine"}]}}'

#apply 更新是前后文件名必须一致，内容可以不一样
kubectl delete -f nginx/
kubectl create -f nginx/
kubectl apply  -f nginx/

#scale
kubectl get deployments -o wide
kubectl scale --current-replicas=1 --replicas=3 deployment/nginx  //一般对deployment操作
kubectl scale --replicas=10 deployment/nginx

#autoscale
kubectl autoscale deployment nginx --min=2 --max=5

#cordon/uncordon 警戒线，设置不可用
kubectl get nodes
kubectl cordon/uncordon {NODENAME}

#daint
执行daint，实际做了两件事
1、设定node节点cordon 不可用
2、驱逐node节点上的pod到别的node节点上

kubectl get nodes
kubectl daint {NODENAME}
```  

# k8s 1.13+ 支持国内拉取image
```
kubeadm init --image-repository registry.aliyuncs.com/google_containers

registry.aliyuncs.com/google_containers
```

# master-taints
k8s-master去污点，其master节点默认拒绝将业务pod调度运行于其上的。

官方的术语就是：master默认被赋予了一个或者多个"污点（taints"，"污点”的作用是让该节点拒绝将pod调度运行于其上

那么存在某些情况，比如想让master也成为工作节点可以调度pod运行怎么办呢？

>生产环境不推荐,建议master保持应有的作用
```
[root@c-3-102 ~]# kubectl taint node -h

kubectl taint node [node] key=value[effect]
#[effect] 可取值: 
[ NoSchedule | PreferNoSchedule | NoExecute ]

NoSchedule: 一定不能被调度
PreferNoSchedule: 尽量不要调度
NoExecute: 不仅不会调度, 还会驱逐Node上已有的Pod

//delete effect on nodes
kubectl taint node node1 key1-
kubectl taint node node1 key2:NoSchedule-

//noschedule
[root@general-k8s-master ~]# kubectl taint nodes general-k8s-master    node-role.kubernetes.io=master:NoSchedule
```

#实战操作驱除master上污点策略
```
[root@c-3-102 ~]# kubectl get nodes
NAME      STATUS   ROLES    AGE   VERSION
c-3-102   Ready    master   70m   v1.15.12
c-3-103   Ready    <none>   68m   v1.15.12

[root@c-3-102 ~]# kubectl describe node c-3-102 |grep -i taint
Taints:             node-role.kubernetes.io/master:NoSchedule   //默认master NoSchedule
```

```
[root@c-3-102 ~]# kubectl describe node c-3-102 |grep -i taint
Taints:             node-role.kubernetes.io/master:NoSchedule

//去除污点策略NoSchedule
[root@c-3-102 ~]# kubectl taint nodes c-3-102 node-role.kubernetes.io/master-
node/c-3-102 untainted
[root@c-3-102 ~]# 
[root@c-3-102 ~]# kubectl describe node c-3-102 |grep -i taint
Taints:             <none>
[root@c-3-102 ~]# 

```

# kubernetes-dashboard ssl过期
[reference_link](https://www.jianshu.com/p/8021285cc37d)

>Chrome上打开kubernetes dashboard

#查看dashboard 日志
```
[root@c-3-102 ~]# kubectl get pods -n kubernetes-dashboard 
NAME                                         READY   STATUS    RESTARTS   AGE
dashboard-metrics-scraper-6cbcff8d57-c2wzh   1/1     Running   0          34m
kubernetes-dashboard-6fd5d88555-kcfff        1/1     Running   0          4m34s
[root@c-3-102 ~]# 
[root@c-3-102 ~]# kubectl logs -n kubernetes-dashboard -f pods/kubernetes-dashboard-6fd5d88555-kcfff
```

#dashboard pod日志
```
2020/06/14 07:52:38 http: TLS handshake error from 192.168.3.102:54193: remote error: tls: unknown certificate
2020/06/14 07:52:38 http: TLS handshake error from 192.168.3.102:54194: remote error: tls: unknown certificate
```

#更新kubenetes-dashboard ssl证书
```
[root@c-3-102 ~]# mkdir key && cd key
[root@c-3-102 key]# openssl genrsa -out dashboard.key 2048

[root@c-3-102 key]# openssl req -new -out dashboard.csr -key dashboard.key -subj '/CN=cn'

[root@c-3-102 key]# openssl x509 -req -in dashboard.csr -signkey dashboard.key -out dashboard.crt
```

#更新k8s-secrets
```
//查看certs
[root@c-3-102 ~]# kubectl get secrets -n kubernetes-dashboard  
NAME                               TYPE                                  DATA   AGE
dashboard-admin-token-r4l4g        kubernetes.io/service-account-token   3      17m
default-token-skhjt                kubernetes.io/service-account-token   3      38m
kubernetes-dashboard-certs         Opaque                                2      9m10s
kubernetes-dashboard-csrf          Opaque                                1      38m
kubernetes-dashboard-key-holder    Opaque                                2      38m
kubernetes-dashboard-token-qfzqk   kubernetes.io/service-account-token   3      38m
[root@c-3-102 ~]# 

//删除secret certs
[root@c-3-102 key]# kubectl delete secret        kubernetes-dashboard-certs    -n kubernetes-dashboard 

//新创建secret certs
[root@c-3-102 key]# kubectl create secret generic kubernetes-dashboard-certs  --from-file=dashboard.key --from-file=dashboard.crt -n  kubernetes-dashboard
```
#删除当前dashboard pod
```
[root@c-3-102 key]# kubectl get pods -n kubernetes-dashboard
NAME                                         READY   STATUS    RESTARTS   AGE
dashboard-metrics-scraper-6cbcff8d57-c2wzh   1/1     Running   0          29m
kubernetes-dashboard-6fd5d88555-9k2ql        1/1     Running   0          29m
[root@c-3-102 key]# 
[root@c-3-102 key]# kubectl delete pods/kubernetes-dashboard-6fd5d88555-9k2ql   -n kubernetes-dashboard
pod "kubernetes-dashboard-6fd5d88555-9k2ql" deleted

//根据deployment自动修复不存在的pod
[root@c-3-102 key]# kubectl get pods -n kubernetes-dashboard
NAME                                         READY   STATUS    RESTARTS   AGE
dashboard-metrics-scraper-6cbcff8d57-c2wzh   1/1     Running   0          30m
kubernetes-dashboard-6fd5d88555-kcfff        1/1     Running   0          10s
[root@c-3-102 key]# kubectl logs -n kubernetes-dashboard -f pods/kubernetes-dashboard-6fd5d88555-kcfff
```

#打开地址查看日志
https://192.168.3.102:30000/#/login
```
2020/06/14 08:21:57 [2020-06-14T08:21:57Z] Outcoming response to 10.10.110.128:55018 with 200 status code
```

# kubernetes-dashboard 不断重启
[link](https://www.jianshu.com/p/e359d3fe238f)
>现象是特点的dashboard版本，在非master节点上与api-servers servcie通信终端 time out,调度到master节点即可解决
- mater 去污点(taint) //去除污点就可以调度pod到master节点
- dashboard yaml文件container调度策略到master节点

#错误日志
```
kubernetes-dashboard   kubernetes-dashboard-6fd5d88555-ldfvz        0/1     CrashLoopBackOff   1091       5d15h
```
#dashboard pods logs
```
[root@k8s-master ~]# kubectl -n kubernetes-dashboard logs -f pods/kubernetes-dashboard-6fd5d88555-m59f2
2020/06/20 07:43:48 Starting overwatch
2020/06/20 07:43:48 Using namespace: kubernetes-dashboard
2020/06/20 07:43:48 Using in-cluster config to connect to apiserver
2020/06/20 07:43:48 Using secret token for csrf signing
2020/06/20 07:43:48 Initializing csrf token from kubernetes-dashboard-csrf secret
panic: Get https://10.20.0.1:443/api/v1/namespaces/kubernetes-dashboard/secrets/kubernetes-dashboard-csrf: dial tcp 10.20.0.1:443: i/o timeout
goroutine 1 [running]:
github.com/kubernetes/dashboard/src/app/backend/client/csrf.(*csrfTokenManager).init(0xc0004ceb40)  ....
```

#问题解决
```
images:
	kubernetes-dashboard
	dashboard-metrics-scraper
container schedual pods on master

#container调度到特定的nodeName节点上 
    spec:
      nodeName: k8s-master  //get nodes, master节点名字
```
#运行查看
```
[root@k8s-master k8s-yaml]# kubectl -n kubernetes-dashboard get pods,deployment,svc
NAME                                             READY   STATUS    RESTARTS   AGE
pod/dashboard-metrics-scraper-64485885f4-4n6xp   1/1     Running   0          10m
pod/kubernetes-dashboard-54459bf4c6-2v66m        1/1     Running   0          10m

NAME                                              READY   UP-TO-DATE   AVAILABLE   AGE
deployment.extensions/dashboard-metrics-scraper   1/1     1            1           5d16h
deployment.extensions/kubernetes-dashboard        1/1     1            1           5d16h

NAME                                TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)         AGE
service/dashboard-metrics-scraper   ClusterIP   10.20.138.148   <none>        8000/TCP        5d16h
service/kubernetes-dashboard        NodePort    10.20.225.57    <none>        443:30000/TCP   5d16h
```

# kubeadm init error to resove
#kublet/docker的cgroupfs driver不一致，极端情况下导致静态pod启动异常
```
[preflight] Running pre-flight checks
	[WARNING IsDockerSystemdCheck]: detected "cgroupfs" as the Docker cgroup driver. The recommended driver is "systemd". Please follow the guide at https://kubernetes.io/docs/setup/cri/
[preflight] Pulling images required for setting up a Kubernetes cluster
```
#docker修正
```
/etc/docker/daemon.json
{
  "exec-opts": ["native.cgroupdriver=systemd"]  //cgroupfs/systemd
}

systemctl daemon-reload &&　systemctl restart docker 

[root@rancher ~]# docker info |grep Cgroup
Cgroup Driver: systemd
```
#kubelet 修改  
>一般不建议修改kubelet的Cgroup Driver
```

```
#kubeadm init cluster 静态pod启动不了  
#问题描述  
```
[kubelet-check] It seems like the kubelet isn't running or healthy.
[kubelet-check] The HTTP call equal to 'curl -sSL http://localhost:10248/healthz' failed with error: Get http://localhost:10248/healthz: dial tcp [::1]:10248: connect: connection refused.

Unfortunately, an error has occurred:
	timed out waiting for the condition

This error is likely caused by:
	- The kubelet is not running
	- The kubelet is unhealthy due to a misconfiguration of the node in some way (required cgroups disabled)

If you are on a systemd-powered system, you can try to troubleshoot the error with the following commands:
	- 'systemctl status kubelet'
	- 'journalctl -xeu kubelet'

Additionally, a control plane component may have crashed or exited when started by the container runtime.
To troubleshoot, list all containers using your preferred container runtimes CLI, e.g. docker.
Here is one example how you may list all Kubernetes containers running in docker:
	- 'docker ps -a | grep kube | grep -v pause'  //查看是否存在历史启动的静态pod
	Once you have found the failing container, you can inspect its logs with:
	- 'docker logs CONTAINERID'
error execution phase wait-control-plane: couldn't initialize a Kubernetes cluster
```

#问题解决
```
centos7.7
/var/log/message   //docker/kubelet cgroup driver 不一致导致的，修改为一致即可解决
```

## kubeadm join token
#默认初始化的join token 24h
```
//new  join token
kubeadm token create --print-join-command

kubeamd token list
```

## work节点
#错误日志  
[link](https://stackoverflow.com/questions/59279546/kubeletnotready-failed-to-initialize-csinodeinfo)

```
[root@general-k8s-master ~]# kubectl get nodes
NAME                 STATUS     ROLES    AGE    VERSION
general-k8s-master   Ready      master   6d5h   v1.15.12
general-k8s-node1    NotReady   <none>   6d4h   v1.18.3
rancher              Ready      <none>   6d5h   v1.15.12

//在work节点上  journal -u kubelet -f
Jun 23 17:42:24 general-k8s-node1 kubelet[32709]: E0623 17:42:24.849921   32709 csi_plugin.go:277] Failed to initialize CSINode: error updating CSINode annotation: timed out waiting for the cond
ition; caused by: the server could not find the requested resourceJun 23 17:42:42 general-k8s-node1 kubelet[32709]: E0623 17:42:42.895771   32709 reflector.go:178] k8s.io/client-go/informers/factory.go:135: Failed to list *v1.CSIDriver: the server could not fi
nd the requested resource
```

#问题解决
```
//new configure

/var/lib/kubelet/config.yaml
featureGates:
  CSIMigration: false

systemctl restart kubelet && journal -u kubelet -f
```

## k8s节点维护
http://kubernetes.kansea.com/docs/user-guide/kubectl/kubectl_cordon/  

http://kubernetes.kansea.com/docs/user-guide/kubectl/kubectl_drain/
```
把维护的节点标记为不可调度
驱逐节点上已经运行的pod
节点重启操作(维护的具体行为)
恢复至初始状态
```
##方案1
```
01、标记维护节点状态

kubectl cordon node1
# 通过如下命令查看该节点是否有taint
kubectl describe node node1
# 此时node的taint显示：
Taints:             node.kubernetes.io/unschedulable:NoSchedule

# 通过如下命令查看是否标记成功
kubectl get node
NAME     STATUS                     ROLES         AGE    VERSION
master   Ready                      etcd,master   112d   v1.15.1
node1    Ready,SchedulingDisabled   worker        112d   v1.15.1
node2    Ready                      worker        112d   v1.15.1

02、驱逐节点上pod
kubectl taint node node1 key=node1:NoExecute

# 通过如下命令查看该节点是否有taint
kubectl describe node node1
此时node的taint显示：
Taints:             key=node1:NoExecute
                    node.kubernetes.io/unschedulable:NoSchedule
03、重启节点
kubectl uncordon node1   //取消节点维护
kubectl taint node node1 key-  //取消节点taint
```

##方案2
```
kubectl drain node1 --delete-local-data --ignore-daemonsets  //驱逐含有daemonset
reboot
kubectl uncordon node1 //取消维护标记

讲解：
drain的意思是驱逐pod并标记节点进入维护状态，简单的说, drain = NoExecute + cordon
--delete-local-data 的意思是：即使存在使用emptyDir的Pod（节点耗尽后将删除的本地数据）也要继续。
--ignore-daemonsets 的意思是：忽略daemonsets控制器

关于ds控制器讲解:
ds控制器的特性是在每一个节点上面去运行一个pod,ds控制器所运行的pod,驱逐也驱逐不走,也不受cordon的控制,只能中断,后面重启后,它会自己启动。

驱逐讲解:
NoSchedule:新的pod不许调度过来 已经运行的依旧在该节点上运行
NoExecute:新的pod不许调度过来 已经运行的pod也驱逐走
PreferNoSchedule:尽量不要调度
```

```
kubectl get nodes -o wide
kubectl describe node/node_name
kubectl drain $node --ignore-daemonsets --delete-local-data

kubectl drain node_name --ignore-daemonsets
kubectl uncordon name

//节点驱逐
#!/bin/bash
K8S_VERSION=1.14.7-eks-1861c5
nodes=$(kubectl get nodes -o jsonpath="{.items[?(@.status.nodeInfo.kubeletVersion==\"v$K8S_VERSION\")].metadata.name}")
for node in ${nodes[@]}
do
    echo "Draining $node"
    kubectl drain $node --ignore-daemonsets --delete-local-data
done

//取消节点驱逐 uncordon
#!/bin/bash
K8S_VERSION=1.14.7-eks-1861c5
nodes=$(kubectl get nodes -o jsonpath="{.items[?(@.status.nodeInfo.kubeletVersion==\"v$K8S_VERSION\")].metadata.name}")
for node in ${nodes[@]}
do
    echo "Undo draining $node"
    kubectl uncordon $node
done
```


## kubeadm pull imges
[link](https://blog.csdn.net/bbwangj/article/details/85017765)
```
镜像仓库为k8s.gcr.io  v1.13+ 新增自定义镜像
registry.aliyuncs.com/google_containers

kubeadm config images list //需要拉取的镜像信息
```

## pod "Back-off restarting failed container"
```
command: [ "/bin/bash", "-ce", "tail -f /dev/null" ]
```

## kubeadm 续期证书
https://www.jianshu.com/p/e0b14f946104