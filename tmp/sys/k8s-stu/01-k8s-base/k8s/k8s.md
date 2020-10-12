# kubernetes
https://kubernetes.io/docs/concepts/

# kubernetes
https://github.com/kubernetes
https://github.com/kubernetes/kubernetes
https://gitee.com/mirrors/Kubernetes

https://kubernetes.io/docs/home/


https://kubernetes.io/docs/concepts/

https://blog.csdn.net/bbwangj/article/details/82696047


##aliyuncontainer
https://github.com/AliyunContainerService
https://help.aliyun.com/  //帮助文档
# kubernetes archit
https://blog.csdn.net/bbwangj/article/details/81776532
https://blog.csdn.net/bbwangj/article/details/81904350


## kube-apiservers
https://blog.csdn.net/wangshuminjava/article/details/90601973
https://blog.csdn.net/wangshuminjava/article/details/90545795

https://blog.csdn.net/bbwangj/article/details/81904421
https://blog.csdn.net/wangshuminjava/article/details/92798225
https://blog.csdn.net/wangshuminjava/article/details/92778881
https://blog.csdn.net/wangshuminjava/article/details/92776543
https://blog.csdn.net/wangshuminjava/article/details/91042070


## kube-schedule
https://blog.csdn.net/bbwangj/article/details/82557759

## kube-controller-manager
https://blog.csdn.net/bbwangj/article/details/82557705

## kube-proxy
https://blog.csdn.net/bbwangj/article/details/82557566


https://blog.csdn.net/wangshuminjava/article/details/91385095
https://blog.csdn.net/wangshuminjava/article/details/92586505
#label
https://blog.csdn.net/wangshuminjava/article/details/92619413

##
https://blog.csdn.net/bbwangj/article/details/82389174
https://blog.csdn.net/bbwangj/article/details/82868094
https://blog.csdn.net/bbwangj/article/details/82790026


# kubernetes 特性
- 资源限制
- 自我修复
- 横向缩放
- 服务发现及负载均衡
- 自动部署及回滚
- 密钥配置及管理
- 存储编排
- 批处理

#Kubernetes的三种外部访问方式
[link](https://blog.csdn.net/shenhonglei1234/article/details/84971771)

[traefik](https://blog.csdn.net/shenhonglei1234/article/details/84981014)

#postman access k8s-api
[rest-api](https://blog.csdn.net/shenhonglei1234/article/details/81633127)


#vs k8s
[inof](https://marketplace.visualstudio.com/items?itemName=ms-kubernetes-tools.vscode-kubernetes-tools)
[visual Studio Code Kubernetes Tools](https://blog.csdn.net/shenhonglei1234/article/details/95471305)


#k8s 拉取国外镜像
[link](https://www.jianshu.com/p/11a804080230)

https://www.cnblogs.com/sunsky303/p/11088349.html

https://www.cnblogs.com/sunsky303/p/11023420.html


https://blog.csdn.net/weixin_34345560/article/details/89559399
## kubectl pretty
https://www.cnblogs.com/sunsky303/p/11023473.html

## CKA
```
CKA（Certified Kubernetes Administrator）是由CNCF与Linux基金会合作创立Kubernetes管理员认证计划，用于证明持有人有履行Kubernetes管理的知识，技能等相关的能力。CKA证书也是全球范围内，企业认可的Kubernetes证书。
```

```
Chapter 01：Kubernetes介绍
-Kubernetes的前世今生
-Kubernetes 相关认证介绍
-Kubernetes架构与核心组件介绍

Chapter 02：集群搭建
-配置基础环境
-部署Kubernetes single master集群
-node节点管理

Chapter 03：API介绍
-API介绍
-kubectl和API

Chapter 04：Label与Annotation
-Label与Annotation的区别
-Label与Annotation的使用

Chapter 05：Namespaces
-Namespaces的介绍
-Namespaces管理

Chapter 06：Pod
-Pod介绍与原理讲解
-Pod创建与删除
-Pod生命周期管理
-Static Pods
-Init Containers

Chapter 07：Pod控制器
-ReplicaSet
-Deployment
-DaemonSet
-Job
-CronJob
-Statefulset

Chapter 08：Services
-Service
-CoreDNS

Chapter 09：健康检查
-LivenessProbe
-ReadnessProbe

Chapter 10：数据与存储
-Persistent Volumes
-Persistent Volume Claim
-EmptyDir
-ConfigMap
-Secret

Chapter 11：调度
-nodeSelector
-nodeName
-Taints
-Tolerations
-Affinity and AntiAffinity

Chapter 12：日常维护
-事件查看
-日志信息查看
-监控信息查看
-维护模式（Cordon）
-疏散POD（Drain）

Chapter 13: 网络方案介绍
-Canal原理介绍及部署实践
-Calico原理介绍及部署实践

Chapter 14：应用自动弹性伸缩
-HPA介绍
-通过CPU、内存监控指标实现应用自动弹性
-通过自定义监控指标实现应用弹性伸缩

Chapter 15：Kubernetes 负载均衡
-kube-proxy的Service的iptables模式与IPVS模式的对比
-IPTABLES模式实现原理
-IPVS模式实现原理
-Ingress的原理讲解和使用

Chapter 16：Kubernetes 安全
-访问API
-身份的验证与授权
-基于角色访问权限配置
-POD 安全策略配置
-Namespace资源配额管理
-网络安全策略配置

Chapter 17：包管理工具Helm
-Helm介绍
-安装Helm
-构建Helm Charts
-Helm Repository
-部署Charts
-升级与回滚

Chapter 18：生产级环境部署
-高可用架构方案设计
-机器配置选型
-规划资源预留比例
-容器跨主机网络方案选型
-集群备份与恢复

Chapter 19：Kubernetes监控告警
-常见监控解决方案
-Prometheus架构介绍
-部署prometheus监控Kubernetes集群
-使用altermanager进行监控告警

Chapter 20：镜像管理工具
-Harbor的介绍
-Harbor部署
-Harbor高可用解决方案介绍
-Harbor镜像安全扫描配置

Chapter 21：日志收集和分析工具
-常见容器日志收集工具和收集方案介绍
-EFK集群部署
-通过EFK进行容器日志收集分析和处理

Chapter 22：持久化存储解决方案
-常见存储介绍
-Ceph分布式存储介绍
-ceph-rbd对接Kubernetes
```



#node cordon/uncordon drain
[link](https://www.jianshu.com/p/752be98e9077)



#cronjob
https://www.jianshu.com/p/104742c7bcf7


#pod亲和性
https://www.jianshu.com/p/afc65a78e6e9


## 添加TLS证书
https://blog.csdn.net/bbwangj/article/details/82503675

```
openssl genrsa -out ingress-key.pem 2048 
openssl req -new -x509 -key ingress-key.pem -out ingress.pem -subj /C=CN/ST=ShenZhen/L=ShenZhen/O=ingcreations/OU=ingcreations/CN=ing-local.com

kubectl create secret tls ingress-secret --key ./ingress-key.pem --cert ./ingress.pem 
```

## resource
```
官网解释：Meaning of memory，Mi表示（1Mi=1024x1024）,M表示（1M=1000x1000）（其它单位类推， 如Ki/K Gi/G）
```

```
apiVersion: v1
kind: Pod
metadata:
  name: nginx
spec:
  containers:
  - name: nginx1
    image: nginx:test
    ports:
    - containerPort: 80
    resources:
      limits:
        cpu: 200m
        memory: 128Mi
      requests:
        cpu: 200m
        memory: 128ssMi
```
>jvm heap设置小于128