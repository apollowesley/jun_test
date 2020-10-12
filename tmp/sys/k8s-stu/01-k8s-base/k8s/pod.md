https://blog.csdn.net/weixin_41806245/article/details/90640646
https://blog.csdn.net/bbwangj/article/details/81812675
https://blog.csdn.net/bbwangj/article/details/81776493
https://blog.csdn.net/wangshuminjava/article/details/106232151
https://blog.csdn.net/wangshuminjava/article/details/89958809
https://blog.csdn.net/wangshuminjava/article/details/89957643

https://blog.csdn.net/wangshuminjava/article/details/90045167

https://blog.csdn.net/wangshuminjava/article/details/90519297
https://blog.csdn.net/wangshuminjava/article/details/90512154
https://blog.csdn.net/wangshuminjava/article/details/90370094
#nodeselect
https://blog.csdn.net/wangshuminjava/article/details/89950100
https://www.cnblogs.com/fuyuteng/p/9460534.html

#secure
https://www.cnblogs.com/sunsky303/p/11090540.html


#lifecycle 钩子处理
https://www.cnblogs.com/sunsky303/p/11571545.html


#cpu
https://www.cnblogs.com/sunsky303/p/11544540.html


#亲和度
https://www.cnblogs.com/sunsky303/p/11130629.html
https://cloud.tencent.com/developer/article/1346260

#probe
https://blog.csdn.net/weixin_34345560/article/details/89781447
https://blog.csdn.net/wangshuminjava/article/details/90370020
https://blog.csdn.net/wangshuminjava/article/details/90369894
https://blog.csdn.net/wangshuminjava/article/details/90369599

## pod lifecycle
https://www.jianshu.com/p/1120911f92a1
https://www.cnblogs.com/lovelinux199075/p/11284419.html
https://cn-blogs.cn/archives/8381.html
https://www.lagou.com/lgeduarticle/55523.html


#resource
```
  resources:
      requests:
        memory: "128Mi"
        cpu: 2
      limits:
        memory: "128Mi"
        cpu: 2


kubectl top pods
kubectl top pods --containers
kubectl top nodes
```

# k8s pods
```
Port: Port is the port number which makes a service visible to other services running within the same K8s cluster. In other words, in case a service wants to invoke another service running within the same Kubernetes cluster, it will be able to do so using port specified against “port” in the service spec file

Target Port: Target port is the port on the POD where the service is running

Nodeport: Node port is the port on which the service can be accessed from external users using [Kube-Proxy]

1. nodePort
外部机器可访问的端口。
比如一个Web应用需要被其他用户访问，那么需要配置type=NodePort，而且配置nodePort=30001，那么其他机器就可以通过浏览器访问scheme://node:30001访问到该服务，例如http://node:30001。
　例如MySQL数据库可能不需要被外界访问，只需被内部服务访问，那么不必设置NodePort

2. targetPort
容器的端口（最根本的端口入口），与制作容器时暴露的端口一致（DockerFile中EXPOSE），例如docker.io官方的nginx暴露的是80端口。

3. port
kubernetes中的服务之间访问的端口，尽管mysql容器暴露了3306端口（参考https://github.com/docker-library/mysql/的DockerFile），但是集群内其他容器需要通过33306端口访问该服务，外部机器不能访问mysql服务，因为他没有配置NodePort类型
```

[pod生命周期](https://www.dazhuanlan.com/2019/11/04/5dbf2f7da84c5/)