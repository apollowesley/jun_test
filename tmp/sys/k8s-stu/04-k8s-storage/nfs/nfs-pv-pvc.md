# nfs
pv,pvc,storageclass

#pv
persistent volume 简称pv是一个k8s资源对象，所以我们可以单独创建一个pv。它不和pod直接发生关系，而是通过persistent volume claim，简称pvc来实现动态绑定。pod定义里指定的是pvc，然后pvc会根据pod的要求去自动绑定合适的pv给pod使用
比如，一个配置了许多50gi pv的集群不会匹配到一个要求100gi的pvc。 只有在100gi pv被加到集群之后，这个pvc才可以被绑定。

创建pv有两种方式，静态和动态
静态，是管理员手动创建一堆pv，组成一个pv池，供pvc来绑定
动态，是指在现有pv不满足pvc的请求时，可以使用存储分类(storageclass)，描述具体过程为：pv先创建分类，pvc请求已创建的某个类（storageclass）的资源，这样就达到动态配置的效果。即通过一个叫 storage class的对象由存储系统根据pvc的要求自动创建。

其中动态方式是通过storageclass来完成的，这是一种新的存储供应方式。动态卷供给能力让管理员不必进行预先创建存储卷，而是随用户需求进行创建。

使用storageclass有什么好处呢？除了由存储系统动态创建，节省了管理员的时间，还有一个好处是可以封装不同类型的存储供pvc选用。在storageclass出现以前，pvc绑定一个pv只能根据两个条件，一个是存储的大小，另一个是访问模式。在storageclass出现后，等于增加了一个绑定维度

[link](https://blog.csdn.net/shenhonglei1234/article/details/84996226#5_NFS_164)
[link](https://blog.csdn.net/shenhonglei1234/article/details/84996515)

***

## pv
```
#nfs-pv.yml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: restapi-pv
spec:
  accessModes:
  - ReadWriteMany
  volumeMode: Filesystem
  storageClassName: slow
  capacity:
    storage: 20Gi
  persistentVolumeReclaimPolicy: Recycle
  nfs:
    path: /data/k8svolume
    server: 114.115.180.117
```
kubectl apply -f nfs-pv.yml
kubectl get pv

#存储模式
```
ReadWriteOnce——该卷可以被单个节点以读/写模式挂载
ReadOnlyMany——该卷可以被多个节点以只读模式挂载
ReadWriteMany——该卷可以被多个节点以读/写模式挂载 在命令行中

访问模式缩写为：
RWO - ReadWriteOnce
ROX - ReadOnlyMany
RWX - ReadWriteMany
```
>重要！一个卷一次只能使用一种访问模式挂载，即使它支持很多访问模式
[pv-access-mode](https://kubernetes.io/docs/concepts/storage/persistent-volumes/#access-modes)

## pvc
```
#nfs-pvc.yml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: restapi-pvc
spec:
  accessModes:
  - ReadWriteMany
  resources:
    requests:
      storage: 10Gi
  volumeMode: Filesystem
  storageClassName: slow
```
kubectl apply -f nfs-pvc.yml
kubectl get pvc
>pv全局，pvc特定namespace

## deployment-pvc
```
#deployment-pvc.yml
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
        persistentVolumeClaim:
          claimName: restapi-pvc
```
kubectl apply -f deployment-pvc.yml
kubectl get deployment
kubeclt get pv,pvc


## nfs-storage-class
https://github.com/kubernetes-incubator/external-storage/tree/master/nfs-client