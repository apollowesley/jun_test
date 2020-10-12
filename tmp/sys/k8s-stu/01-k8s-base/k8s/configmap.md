# configmap
https://kubernetes.io/zh/docs/tasks/configure-pod-container/configure-pod-configmap/

```
二进制文件 + 配置文件  == 运行的服务

生产环境中很多应用程序的配置可能需要通过配置文件，命令行参数和环境变量的组合配置来完成，这些配置应该从image中解耦，以此来保持容器化应用程序的可移植性。

在K8S1.2后引入ConfigMap来处理这种类型的配置数据。ConfigMap API资源提供了将配置数据注入容器的方式，同时保证该机制对容器来说是透明的。

ConfigMap可以被用来保存单个属性，也可以用来保存整个配置文件或者JSON二进制对象。ConfigMap API资源存储键/值对配置数据，这些数据可以在pods里使用

https://blog.csdn.net/weixin_34345560/article/details/89800752
https://www.cnblogs.com/sheng-jie/p/Using-ConfigMap-with-NETCore.html
https://www.kubernetes.org.cn/3138.html
https://www.cnblogs.com/ilinuxer/p/6629228.html
https://blog.csdn.net/bbwangj/article/details/81776648
https://blog.csdn.net/wangshuminjava/article/details/105988555
https://blog.csdn.net/wangshuminjava/article/details/90265114
https://blog.csdn.net/wangshuminjava/article/details/90262115
https://blog.csdn.net/wangshuminjava/article/details/90169993
https://blog.csdn.net/wangshuminjava/article/details/90265143

- v1.2+ configmap
```

#check exist configmap
```
[root@c-3-103 ~]# kubectl get configmaps --all-namespaces
NAMESPACE              NAME                                 DATA   AGE
kube-public            cluster-info                         2      22h
kube-system            coredns                              1      22h
kube-system            extension-apiserver-authentication   6      22h
kube-system            kube-flannel-cfg                     2      22h
kube-system            kube-proxy                           2      22h
kube-system            kubeadm-config                       2      22h
kube-system            kubelet-config-1.13                  1      22h
kubernetes-dashboard   kubernetes-dashboard-settings        0      21h
ns-monitor             prometheus-conf                      1      16h
ns-monitor             prometheus-rules                     1      16h
[root@c-3-103 ~]# 

[root@c-3-103 ~]# kubectl -n kube-system describe configmap kube-flannel-cfg
```

ConfigMap也是kubernetes的一种资源对象，当然创建ConfigMap也有两种方式：
```
1）通过kubectl create configmap命令
2）通过yaml文件
```

[root@c-3-103 ~]# kubectl create configmap --help
```
[root@c-3-103 ~]# kubectl create configmap --help
Create a configmap based on a file, directory, or specified literal value. 

A single configmap may package one or more key/value pairs. 

When creating a configmap based on a file, the key will default to the basename of the file, and the value will default
to the file content.  If the basename is an invalid key, you may specify an alternate key. 

When creating a configmap based on a directory, each file whose basename is a valid key in the directory will be
packaged into the configmap.  Any directory entries except regular files are ignored (e.g. subdirectories, symlinks,
devices, pipes, etc).

Aliases:
configmap, cm

Examples:
  # Create a new configmap named my-config based on folder bar
  kubectl create configmap my-config --from-file=path/to/bar
  
  # Create a new configmap named my-config with specified keys instead of file basenames on disk
  kubectl create configmap my-config --from-file=key1=/path/to/bar/file1.txt --from-file=key2=/path/to/bar/file2.txt
  
  # Create a new configmap named my-config with key1=config1 and key2=config2
  kubectl create configmap my-config --from-literal=key1=config1 --from-literal=key2=config2
  
  # Create a new configmap named my-config from the key=value pairs in the file
  kubectl create configmap my-config --from-file=path/to/bar
  
  # Create a new configmap named my-config from an env file
  kubectl create configmap my-config --from-env-file=path/to/bar.env

Options:
      --allow-missing-template-keys=true: If true, ignore any errors in templates when a field or map key is missing in
the template. Only applies to golang and jsonpath output formats.
      --append-hash=false: Append a hash of the configmap to its name.
      --dry-run=false: If true, only print the object that would be sent, without sending it.
      --from-env-file='': Specify the path to a file to read lines of key=val pairs to create a configmap (i.e. a Docker
.env file).
      --from-file=[]: Key file can be specified using its file path, in which case file basename will be used as
configmap key, or optionally with a key and file path, in which case the given key will be used.  Specifying a directory
will iterate each named file in the directory whose basename is a valid configmap key.
      --from-literal=[]: Specify a key and literal value to insert in configmap (i.e. mykey=somevalue)
      --generator='configmap/v1': The name of the API generator to use.
  -o, --output='': Output format. One of:
json|yaml|name|go-template|go-template-file|template|templatefile|jsonpath|jsonpath-file.
      --save-config=false: If true, the configuration of current object will be saved in its annotation. Otherwise, the
annotation will be unchanged. This flag is useful when you want to perform kubectl apply on this object in the future.
      --template='': Template string or path to template file to use when -o=go-template, -o=go-template-file. The
template format is golang templates [http://golang.org/pkg/text/template/#pkg-overview].
      --validate=true: If true, use a schema to validate the input before sending it

Usage:
  kubectl create configmap NAME [--from-file=[key=]source] [--from-literal=key1=value1] [--dry-run] [options]

Use "kubectl options" for a list of global command-line options (applies to all commands).
```

#syntax format
kubectl create configmap NAME [--from-file=[key=]source] [--from-literal=key1=value1][--dry-run] [options]
>--from-file= dirrectory/file .. --from-file= ...(支持多个文件填写)
```
kubectl create configmap config-name --from-file=[key-name]=directory_path或者file-path  //多文件读取模式

kubectl create configmap config-name --from-env-file=env-file-path  //键值对文件读取

kubectl create configmap config-name --from-literal=key-name=value  //键值对形式
```


```
[root@c-3-103 cm]# cat test1.txt 
test1.1
test1.2
test1.3
[root@c-3-103 cm]# cat test2.txt 
test2.1
test2.2
test2.3
[root@c-3-103 cm]# cat env.txt 
env.1=111
env.2=222
env.3=333
env.4=
#env.5=555
```
[root@c-3-103 cm]# kubectl create configmap my-test --from-file=./  --dry-run -o yaml
[root@c-3-103 cm]# kubectl create configmap my-test --from-file=./test1.txt --dry-run -o yaml

```
#--from-file
[root@c-3-103 cm]# kubectl create configmap my-test --from-file=./test1.txt --dry-run -o yaml
apiVersion: v1
data:
  test1.txt: |
    test1.1
    test1.2
    test1.3
kind: ConfigMap
metadata:
  creationTimestamp: null
  name: my-test

[root@c-3-103 cm]# kubectl create configmap my-test --from-file=./  --dry-run -o yaml
apiVersion: v1
data:
  env.txt: |
    env.1=111
    env.2=222
    env.3=333
    env.4=
    #env.5=555
  test1.txt: |
    test1.1
    test1.2
    test1.3
  test2.txt: |
    test2.1
    test2.2
    test2.3
kind: ConfigMap
metadata:
  creationTimestamp: null
  name: my-test

[root@c-3-103 cm]# kubectl create configmap my-test --from-file=test=./test1.txt --dry-run -o yaml  //自定义key,不存在key默认文件名字
apiVersion: v1
data:
  test: |
    test1.1
    test1.2
    test1.3
kind: ConfigMap
metadata:
  creationTimestamp: null
  name: my-test

#--from-env-file
[root@c-3-103 cm]# kubectl create configmap my-env --from-env-file=./env.txt --dry-run -o yaml  //from-env-file 只能跟文件，切键值对格式#忽略，及键值对语法合规性验证
apiVersion: v1
data:
  env.1: "111"
  env.2: "222"
  env.3: "333"
  env.4: ""
kind: ConfigMap
metadata:
  creationTimestamp: null
  name: my-env

#--from-literal
[root@c-3-103 cm]# kubectl create configmap my-test  --from-literal=sb="hhaha" --from-literal=ss=sss --dry-run -o yaml
apiVersion: v1
data:
  sb: hhaha
  ss: sss
kind: ConfigMap
metadata:
  creationTimestamp: null
  name: my-test

```



#subPath
https://kubernetes.io/docs/concepts/storage/volumes/#using-subpath
>匹配挂载读取

#configmap-exp
```
apiVersion: v1
kind: ConfigMap
metadata:
  name: special-config
  namespace: default
data:
  special.level: very
  special.type: |-
    property.1=value-1
    property.2=value-2
    property.3=value-3
```

#pod use subpath
```
apiVersion: v1
kind: Pod
metadata:
  name: busybox-pod
spec:
  containers:
    - name: busybox-container
      image: busybox
      command: [ "/bin/sh", "-c", "sleep 3600" ]

      volumeMounts:
      - name: config-volume
        mountPath: /etc/special.type
        subPath: special.type    #挂载匹配的key
      - name: config-volume
        mountPath: /etc/config/special.level
        subPath: special.level
  volumes:
    - name: config-volume
      configMap:
        name: special-config
  restartPolicy: Never
```

#check mount config values
```
[root@c-3-103 cm]# kubectl get pods 
NAME                    READY   STATUS    RESTARTS   AGE
busybox-pod             1/1     Running   0          73s
redis-fdf5d7c8b-hlfzm   1/1     Running   1          20h
[root@c-3-103 cm]# 
[root@c-3-103 cm]# kubectl exec -it busybox-pod sh
/ # cat /etc/config/special.level 
very/ # 
/ # 
/ # cat /etc/special.type 
property.1=value-1
property.2=value-2
```