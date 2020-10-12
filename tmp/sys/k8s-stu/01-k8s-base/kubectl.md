# kubectl

https://www.cnblogs.com/menkeyi/p/7128809.html

```
[root@k8s-master ~]# kubectl version -o yaml |grep v  //cs版本查看,最低x.y版本相同
  gitVersion: v1.15.12
serverVersion:
  gitVersion: v1.15.12
```

## kubectl help
```
[root@k8s-master tmp]# kubectl --help
kubectl controls the Kubernetes cluster manager.

 Find more information at: https://kubernetes.io/docs/reference/kubectl/overview/

Basic Commands (Beginner):
  create         Create a resource from a file or from stdin.
  expose         Take a replication controller, service, deployment or pod and expose it as a new Kubernetes Service
  run            Run a particular image on the cluster
  set            Set specific features on objects

Basic Commands (Intermediate): 
  explain        Documentation of resources
  get            Display one or many resources
  edit           Edit a resource on the server
  delete         Delete resources by filenames, stdin, resources and names, or by resources and label selector

Deploy Commands:
  rollout        Manage the rollout of a resource
  scale          Set a new size for a Deployment, ReplicaSet, Replication Controller, or Job
  autoscale      Auto-scale a Deployment, ReplicaSet, or ReplicationController

Cluster Management Commands:
  certificate    Modify certificate resources.
  cluster-info   Display cluster info
  top            Display Resource (CPU/Memory/Storage) usage.
  cordon         Mark node as unschedulable
  uncordon       Mark node as schedulable
  drain          Drain node in preparation for maintenance
  taint          Update the taints on one or more nodes

Troubleshooting and Debugging Commands:
  describe       Show details of a specific resource or group of resources
  logs           Print the logs for a container in a pod
  attach         Attach to a running container
  exec           Execute a command in a container
  port-forward   Forward one or more local ports to a pod
  proxy          Run a proxy to the Kubernetes API server
  cp             Copy files and directories to and from containers.
  auth           Inspect authorization

Advanced Commands:
  diff           Diff live version against would-be applied version
  apply          Apply a configuration to a resource by filename or stdin
  patch          Update field(s) of a resource using strategic merge patch
  replace        Replace a resource by filename or stdin
  wait           Experimental: Wait for a specific condition on one or many resources.
  convert        Convert config files between different API versions
  kustomize      Build a kustomization target from a directory or a remote url.

Settings Commands:
  label          Update the labels on a resource
  annotate       Update the annotations on a resource
  completion     Output shell completion code for the specified shell (bash or zsh)

Other Commands:
  api-resources  Print the supported API resources on the server
  api-versions   Print the supported API versions on the server, in the form of "group/version"
  config         Modify kubeconfig files
  plugin         Provides utilities for interacting with plugins.
  version        Print the client and server version information

Usage:
  kubectl [flags] [options]

Use "kubectl <command> --help" for more information about a given command.
Use "kubectl options" for a list of global command-line options (applies to all commands).
```


### rollout
```
[root@k8s-master tmp]# kubectl rollout  --help
Manage the rollout of a resource.
  
 Valid resource types include:

  *  deployments
  *  daemonsets
  *  statefulsets

Examples:
  # Rollback to the previous deployment  /rollback deployment version
  kubectl rollout undo deployment/abc
  
  # Check the rollout status of a daemonset
  kubectl rollout status daemonset/foo

Available Commands:
  history     View rollout history
  pause       Mark the provided resource as paused
  restart     Restart a resource
  resume      Resume a paused resource
  status      Show the status of the rollout
  undo        Undo a previous rollout

Usage:
  kubectl rollout SUBCOMMAND [options]
```

#rollout opt
```
[root@k8s-master tmp]# kubectl rollout history  deployment/grafana  //历史版本
deployment.extensions/grafana 
REVISION  CHANGE-CAUSE
1         <none>
[root@k8s-master tmp]# kubectl rollout restart   deployment/grafana 
deployment.extensions/grafana restarted

[root@k8s-master tmp]# kubectl rollout status deployment  grafana   //滚动更新实时进度
Waiting for deployment "grafana" rollout to finish: 1 old replicas are pending termination...
Waiting for deployment "grafana" rollout to finish: 1 old replicas are pending termination...
Waiting for deployment spec update to be observed...
Waiting for deployment "grafana" rollout to finish: 1 old replicas are pending termination...
deployment "grafana" successfully rolled out
[root@k8s-master tmp]# 
[root@k8s-master tmp]# kubectl rollout history  deployment  grafana
deployment.extensions/grafana 
REVISION  CHANGE-CAUSE
1         <none>
2         <none>

[root@k8s-master tmp]# kubectl rollout undo deployment grafana --to-revision=1  //滚动到特定的位置
deployment.extensions/grafana rolled back
[root@k8s-master tmp]# 

[root@k8s-master tmp]# kubectl rollout history  deployment  grafana 
deployment.extensions/grafana 
REVISION  CHANGE-CAUSE
2         <none>
3         <none>

[root@k8s-master tmp]# kubectl rollout status   deployment  grafana 
Waiting for deployment "grafana" rollout to finish: 1 old replicas are pending termination...
Waiting for deployment "grafana" rollout to finish: 1 old replicas are pending termination...
Waiting for deployment spec update to be observed...
Waiting for deployment spec update to be observed...
Waiting for deployment "grafana" rollout to finish: 1 old replicas are pending termination...
deployment "grafana" successfully rolled out
[root@k8s-master tmp]# 
```





## kubeclt common-used
```
# 查看集群信息
$ kubectl cluster-info

# kubeadm会自动检查当前环境是否有上次命令执行的“残留”。如果有，必须清理后再行执行init。我们可以通过”kubeadm reset”来清理环境，以备重来
$ kubeadm reset

# 获取nodes节点
$ kubectl get nodes

# 删除node节点
$ kubectl delete node c7

# 获取pods
$ kubectl get pods --all-namespaces

# 查看某个pod的状态
$ kubectl describe pod kube-dns -n kube-system

# 重新生成 token kube1
$ kubeadm token generate
$ kubeadm token create <generated-token> --print-join-command --ttl=24h

kubectl apply -f kubernetes-dashboard.yaml 
kubectl delete -f kubernetes-dashboard.yaml 


# 我们发现deployment的create和apply命令都带有一个–record参数，这是告诉apiserver记录update的历史
# 通过kubectl rollout history可以查看deployment的update history：

kubectl apply -f deployment-demo-v0.2.yaml --record
kubectl rollout history deployment deployment-demo

# Deployment下Pod的回退操作异常简单，通过rollout undo即可完成
# rollout undo会将Deployment回退到record中的上一个revision（见上面rollout history的输出中有revision列）：
kubectl rollout undo deployment deployment-demo

# 更新svc
kubectl replace -f xxx.yaml
# 强制更新svc
kubectl replace -f xxx.yaml --force
kubectl edit

# 查看详细信息（包含错误信息）
kubectl describe pod kube-dns -n kube-system
kubectl describe deployment deployment-demo

kubectl logs kubernetes-dashboard-67589f8d6b-l7tfd -n kube-system
kubectl delete pod prometheus-tim-3864503240-rwpq5 -n kube-system

kubectl get deployment --all-namespaces
kubectl get svc  --all-namespaces
kubectl get pod  -o wide  --all-namespaces

kubectl exec nginx-9d85d49b7-7knw6 env
kubectl describe svc/nginx
kubectl get all
kubectl get rs
kubectl get rc
kubectl get deployments

kubectl get svc,ep
# k8s的LVS方案，内置了nginx
kubectl get ingress

# kubernetes在kubectl cli工具中仅提供了对Replication Controller的rolling-update支持，通过kubectl -help，我们可以查看到下面的命令usage描述：
kubectl rolling-update [metadata.name] --update-period=10s -f xxx.yaml
kubectl rolling-update hello-rc –image=index.tenxcloud.com/tailnode/hello:v2.0

# 升级镜像版本
kubectl -n default set image deployments/gateway gateway=192.168.31.149:5000/dev/core-gateway:latest

#如果在升级过程中出现问题（比如长时间无响应），可以CTRL+C结束再使用kubectl rolling-update hello-rc –-rollback进行回滚，但如果升级完成后出现问题（比如新版本程序出core），此命令无能为力，需要使用同样方法“升级”为旧版本

# kubernetes Deployment是一个更高级别的抽象，就像文章开头那幅示意图那样，Deployment会创建一个Replica Set，用来保证Deployment中Pod的副本数。
# 由于kubectl rolling-update仅支持replication controllers，因此要想rolling-updata deployment中的Pod，你需要修改Deployment自己的manifest文件并应用。
# 这个修改会创建一个新的Replica Set，在scale up这个Replica Set的Pod数的同时，减少原先的Replica Set的Pod数，直至zero。
# 而这一切都发生在Server端，并不需要kubectl参与。



# busybox
kubectl exec busybox -- nslookup kube-dns.kube-system

kubectl apply -f kubernetes-dashboard.yaml -f account.yaml
kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | grep admin-user | awk '{print $1}')

# [Kubernetes Dashboard token失效时间设置](https://blog.csdn.net/u013201439/article/details/80930285)
kubectl edit deployment kubernetes-dashboard -n kube-system

kubectl expose deployment springboot-demo-deployment --type=NodePort
minikube service springboot-demo-deployment --url
curl $(minikube service springboot-demo-deployment --url)/hello

# configMap
kubectl get configmap nginx-config
kubectl get configmap nginx-config -o yaml
kubectl edit configmap env-config
# curl -s https://paste.ubuntu.com/p/ZmyxsHB7Xt/ |sed -n '/api/,/true/p' | sed  's@true@"true"@' | kubectl  create -f -  

# 将名为foo中的pod副本数设置为3
kubectl scale --replicas=3 rs/foo
# 将由“foo.yaml”配置文件中指定的资源对象和名称标识的Pod资源副本设为3
kubectl scale --replicas=3 -f foo.yaml
# 如果当前副本数为2，则将其扩展至3。
kubectl scale --current-replicas=2 --replicas=3 deployment/mysql
# 设置多个RC中Pod副本数量。
kubectl scale --replicas=5 rc/foo rc/bar rc/baz

# 默认情况下，为了保证 master 的安全，master 是不会被调度到 app 的。你可以取消这个限制通过输入
$ kubectl taint nodes --all node-role.kubernetes.io/master-

# 修改kube-proxy访问apiserver指向keepalived的虚拟ip
kubectl get configmap -n kube-system kube-proxy -o yaml > kube-proxy-cm.yaml
sed -i "s#server:.*#server: https://${vip}:6443#g" kube-proxy-cm.yaml
kubectl apply -f kube-proxy-cm.yaml --force
kubectl delete pod -n kube-system -l k8s-app=kube-proxy
```