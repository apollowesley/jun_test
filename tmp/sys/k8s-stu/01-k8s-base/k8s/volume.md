## volume
https://kubernetes.io/docs/concepts/storage/volumes/


https://www.cnblogs.com/sunsky303/p/11578206.html
```
默认情况下容器中的磁盘文件是非持久化的，对于运行在容器中的应用来说面临两个问题，
第一：当容器挂掉kubelet将重启启动它时，文件将会丢失；
第二：当Pod中同时运行多个容器，容器之间需要共享文件时；
Kubernetes 通过 volume 处理。

pod需要指定Volume的类型和内容（spec.volumes字段);
映射到容器的位置（spec.containers.volumeMounts字段);
```
kubectl explain  pod.spec.volumes
kubectl explain  pod.spec.containers.volumeMounts

```
kubectl explain  pod.spec.volumes

KIND:     Pod
VERSION:  v1

RESOURCE: volumes <[]Object>

DESCRIPTION:
     List of volumes that can be mounted by containers belonging to the pod.
     More info: https://kubernetes.io/docs/concepts/storage/volumes

     Volume represents a named volume in a pod that may be accessed by any
     container in the pod.

FIELDS:
   awsElasticBlockStore <Object>
     AWSElasticBlockStore represents an AWS Disk resource that is attached to a
     kubelet's host machine and then exposed to the pod. More info:
     https://kubernetes.io/docs/concepts/storage/volumes#awselasticblockstore

   azureDisk    <Object>
     AzureDisk represents an Azure Data Disk mount on the host and bind mount to
     the pod.

   azureFile    <Object>
     AzureFile represents an Azure File Service mount on the host and bind mount
     to the pod.

   cephfs       <Object>
     CephFS represents a Ceph FS mount on the host that shares a pod's lifetime

   cinder       <Object>
     Cinder represents a cinder volume attached and mounted on kubelets host
     machine More info:
     https://releases.k8s.io/HEAD/examples/mysql-cinder-pd/README.md

   configMap    <Object>
     ConfigMap represents a configMap that should populate this volume

   downwardAPI  <Object>
     DownwardAPI represents downward API about the pod that should populate this
     volume

   emptyDir     <Object>
     EmptyDir represents a temporary directory that shares a pod's lifetime.
     More info: https://kubernetes.io/docs/concepts/storage/volumes#emptydir

   fc   <Object>
     FC represents a Fibre Channel resource that is attached to a kubelet's host
     machine and then exposed to the pod.

   flexVolume   <Object>
     FlexVolume represents a generic volume resource that is
     provisioned/attached using an exec based plugin.

   flocker      <Object>
     Flocker represents a Flocker volume attached to a kubelet's host machine.
     This depends on the Flocker control service being running

   gcePersistentDisk    <Object>
     GCEPersistentDisk represents a GCE Disk resource that is attached to a
     kubelet's host machine and then exposed to the pod. More info:
     https://kubernetes.io/docs/concepts/storage/volumes#gcepersistentdisk

   gitRepo      <Object>
     GitRepo represents a git repository at a particular revision. DEPRECATED:
     GitRepo is deprecated. To provision a container with a git repo, mount an
     EmptyDir into an InitContainer that clones the repo using git, then mount
     the EmptyDir into the Pod's container.

   glusterfs    <Object>
     Glusterfs represents a Glusterfs mount on the host that shares a pod's
     lifetime. More info:
     https://releases.k8s.io/HEAD/examples/volumes/glusterfs/README.md

   hostPath     <Object>
     HostPath represents a pre-existing file or directory on the host machine
     that is directly exposed to the container. This is generally used for
     system agents or other privileged things that are allowed to see the host
     machine. Most containers will NOT need this. More info:
     https://kubernetes.io/docs/concepts/storage/volumes#hostpath

   iscsi        <Object>
     ISCSI represents an ISCSI Disk resource that is attached to a kubelet's
     host machine and then exposed to the pod. More info:
     https://releases.k8s.io/HEAD/examples/volumes/iscsi/README.md

   name <string> -required-
     Volume's name. Must be a DNS_LABEL and unique within the pod. More info:
     https://kubernetes.io/docs/concepts/overview/working-with-objects/names/#names

   nfs  <Object>
     NFS represents an NFS mount on the host that shares a pod's lifetime More
     info: https://kubernetes.io/docs/concepts/storage/volumes#nfs

   persistentVolumeClaim        <Object>
     PersistentVolumeClaimVolumeSource represents a reference to a
     PersistentVolumeClaim in the same namespace. More info:
     https://kubernetes.io/docs/concepts/storage/persistent-volumes#persistentvolumeclaims

   photonPersistentDisk <Object>
     PhotonPersistentDisk represents a PhotonController persistent disk attached
     and mounted on kubelets host machine

   portworxVolume       <Object>
     PortworxVolume represents a portworx volume attached and mounted on
     kubelets host machine

   projected    <Object>
     Items for all in one resources secrets, configmaps, and downward API

   quobyte      <Object>
     Quobyte represents a Quobyte mount on the host that shares a pod's lifetime

   rbd  <Object>
     RBD represents a Rados Block Device mount on the host that shares a pod's
     lifetime. More info:
     https://releases.k8s.io/HEAD/examples/volumes/rbd/README.md

   scaleIO      <Object>
     ScaleIO represents a ScaleIO persistent volume attached and mounted on
     Kubernetes nodes.

   secret       <Object>
     Secret represents a secret that should populate this volume. More info:
     https://kubernetes.io/docs/concepts/storage/volumes#secret

   storageos    <Object>
     StorageOS represents a StorageOS volume attached and mounted on Kubernetes
     nodes.

   vsphereVolume        <Object>
     VsphereVolume represents a vSphere volume attached and mounted on kubelets
     host machine
```


## emptyDir
使用emptyDir，当Pod分配到Node上时，将会创建emptyDir，并且只要Node上的Pod一直运行，Volume就会一直存。当Pod（不管任何原因）从Node上被删除时，emptyDir也同时会删除，存储的数据也将永久删除。注：删除容器不影响emptyDir。
```
kubectl explain  pod.spec.volumes.emptyDir
```

## hostpath
hostPath允许挂载Node上的文件系统到Pod里面去。如果Pod需要使用Node上的文件，可以使用hostPath。

kubectl explain  pod.spec.volumes.hostPath

[link](https://kubernetes.io/docs/concepts/storage/volumes/#hostpath)
```
 volumeMounts:
    - name: export-volume
      mountPath: /export
volumes:
  - name: export-volume
    hostPath:
      path: /tmp/nfs-provisioner
---
spec:
  volumes:	# 添加hostPath持久化
    - name: mysql
      hostPath:
        path: /data/tc-db	# 要为空目录
  containers:
    - name: mysql
      image: 10.0.0.11:5000/mysql:5.7
      ports:
      - containerPort: 3306
      volumeMounts:	# 添加volumes目录
      - name: mysql
        mountPath: /var/lib/mysql
```


```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: restapi
spec:
  replicas: 1
  selector:
    matchLabels:
      app: restapi
  template:
    metadata:
      labels:
        app: restapi
        tier: backend
        track: stable
    spec:
      containers:
      - name: restapi
        image: xiliangma/restapi:latest
        imagePullPolicy: IfNotPresent
        ports:
        - name: dev
          containerPort: 8080
        - name: prod
          containerPort: 8088
        - name: https
          containerPort: 443
        resources:
          limits:
            cpu: 1000m
            memory: 1024Mi
          requests:
            cpu: 300m
            memory: 256Mi
        livenessProbe:
          httpGet:
            path: /swagger
            port: 8080
            scheme: HTTP 
          initialDelaySeconds: 60
          periodSeconds: 30
          timeoutSeconds: 3
          failureThreshold: 3
        volumeMounts:
        - mountPath: /tmp/cache
          name: test-volume # 通过指定名称关联存储
      volumes:
      - name: test-volume
        hostPath:
          path: /data/hostpath
```

## nfs
NFS 是Network File System的缩写，即网络文件系统。Kubernetes中通过简单地配置就可以挂载NFS到Pod中，而NFS中的数据是可以永久保存的，同时NFS支持同时写操作。Pod被删除时，Volume被卸载，内容被保留。这就意味着NFS能够允许我们提前对数据进行处理，而且这些数据可以在Pod之间相互传递

kubectl explain  pod.spec.volumes.nfs
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: restapi
spec:
  replicas: 1
  selector:
    matchLabels:
      app: restapi
  template:
    metadata:
      labels:
        app: restapi
        tier: backend
        track: stable
    spec:
      containers:
      - name: restapi
        image: xiliangma/restapi:latest
        imagePullPolicy: IfNotPresent
        ports:
        - name: dev
          containerPort: 8080
        - name: prod
          containerPort: 8088
        - name: https
          containerPort: 443
        resources:
          limits:
            cpu: 1000m
            memory: 1024Mi
          requests:
            cpu: 300m
            memory: 256Mi
        livenessProbe:
          httpGet:
            path: /swagger
            port: 8080
            scheme: HTTP 
          initialDelaySeconds: 60
          periodSeconds: 30
          timeoutSeconds: 3
          failureThreshold: 3
        volumeMounts:
        - mountPath: /tmp/cache
          name: test-volume # 通过指定名称关联存储
      volumes:
      - name: test-volume
        nfs:
          path: /data/k8svolume
          server: 114.115.180.117
```