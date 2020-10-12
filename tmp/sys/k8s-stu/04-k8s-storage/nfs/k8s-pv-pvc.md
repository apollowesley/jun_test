#pv/pvc  
[link](https://www.jianshu.com/p/88a844a49789)
[link](https://www.jianshu.com/p/a1089f9bb36e)

#PersistentVolume(pv)

#PersistentVolumeClaim(pvc)

#StorageClass

#pv挂载的策略
ReadWriteOnce：可读可写，只能被一个Node节点挂载  RWO
ReadWriteMany：可读可写，可以被多个Node节点挂载  ROX
ReadOnlyMany：只读，能被多个Node节点挂载         RWX

## pv nfs作为后端存储
[link](https://blog.csdn.net/shenhonglei1234/article/details/80827570)  
[nfs pv/pvc](https://www.jianshu.com/p/65ed4bdf0e89)

[nfs简单入门](https://www.cnblogs.com/xiaochina/p/7113465.html)  
[nfs使用样例](https://www.cnblogs.com/xiaochina/p/6230395.html)  
[nfs参数](https://www.cnblogs.com/kingle-study/p/9395220.html)

01、setup nfs
>centos7.x

```
[root@k8s-master ~]# yum install -y nfs nfs-utils

[root@k8s-master ~]# mkdir /nfs_data
[root@k8s-master ~]# chmod -R 777 /nfs_data/  //保证可写 chown -R +w /nfs_data

[root@k8s-master ~]# cat /etc/exports
/nfs_data 10.0.0.0/8(rw,async,no_root_squash)

systemctl enable rpcbind && systemctl start rpcbind
systemctl enable nfs-server && systemctl start nfs-server   //nfs-server依赖rpcbind注册

[root@k8s-master ~]# exportfs -a   //刷新nfs配置到内核，如果存在异常配置会提示，注意修正
[root@k8s-master ~]# showmount -e  //查看本机存在的nfs许可访问目录
Export list for k8s-master:
/nfs_data 10.0.0.0/8
```

02、testing nfs-client
```
[root@k8s-master ~]# showmount -e 10.24.3.9
Export list for 10.24.3.9:
/nfs_data 10.0.0.0/8

[root@k8s-master ~]# mount -t nfs 10.24.3.9:/nfs_data   /mnt

[root@k8s-master ~]# touch /mnt/sb && rm -rf /mnt/* 
[root@k8s-master ~]# umount /mnt
```

03、pv/pvc yaml创建
#配置编写查看
```
[root@k8s-master ~]# kubectl api-resources |grep pv  //查看api-resource
persistentvolumeclaims            pvc                                         true         PersistentVolumeClaim
persistentvolumes                 pv                                          false        PersistentVolume

[root@k8s-master ~]# kubectl explain PersistentVolume
[root@k8s-master ~]# kubectl explain PersistentVolume.spec
```

#执行创建pv/pvc
```
#nfs-pv.yaml 请查看example.yaml #nfs-pv

[root@k8s-master ~]# kubectl apply -f nfs-pv.yaml 
persistentvolume/nfs-pv created
[root@k8s-master ~]# 
[root@k8s-master ~]# kubectl get pv
NAME     CAPACITY   ACCESS MODES   RECLAIM POLICY   STATUS      CLAIM   STORAGECLASS   REASON   AGE
nfs-pv   2Gi        RWO            Recycle          Available           mvpbang                 3s
[root@k8s-master ~]# 
```

```
#nfs-pvc-mvpbang.yaml 请查看example.yaml #nfs-pvc-mvpbang

[root@k8s-master ~]# kubectl apply -f nfs-pvc-mvpbang.yaml 
[root@k8s-master ~]# kubectl get pv,pvc
NAME                      CAPACITY   ACCESS MODES   RECLAIM POLICY   STATUS   CLAIM                     STORAGECLASS   REASON   AGE
persistentvolume/nfs-pv   2Gi        RWO            Recycle          Bound    default/nfs-pvc-mvpbang   mvpbang                 7m19s

NAME                                    STATUS   VOLUME   CAPACITY   ACCESS MODES   STORAGECLASS   AGE
persistentvolumeclaim/nfs-pvc-mvpbang   Bound    nfs-pv   2Gi        RWO            mvpbang        37s
[root@k8s-master ~]# 
[root@k8s-master ~]# kubectl get pv,pvc -o wide
NAME                      CAPACITY   ACCESS MODES   RECLAIM POLICY   STATUS   CLAIM                     STORAGECLASS   REASON   AGE     VOLUMEMODE
persistentvolume/nfs-pv   2Gi        RWO            Recycle          Bound    default/nfs-pvc-mvpbang   mvpbang                 8m50s   Filesystem

NAME                                    STATUS   VOLUME   CAPACITY   ACCESS MODES   STORAGECLASS   AGE    VOLUMEMODE
persistentvolumeclaim/nfs-pvc-mvpbang   Bound    nfs-pv   2Gi        RWO            mvpbang        2m8s   Filesystem
[root@k8s-master ~]# 
```

#pod use pvc
```
#pod-pvc-mvpbang.yaml  请查看example.yaml  pod-pvc-mvpbang
```

# error
pod使用pvc(nfs后端存储)mount失败  
[link](https://serverfault.com/questions/988535/nfs-client-provisioner-stucked-at-containercreating)  
[link](https://blog.51cto.com/lizhiyuan/2335456)  

```
MountVolume.SetUp failed for volume "nfs-pv" : mount failed: exit status 32 Mounting command: systemd-run Mounting arguments: --description=Kubernetes transient mount for /var/lib/kubelet/pods/0466bd61-8c0b-4d34-ae3b-3d6b1ff5254f/volumes/kubernetes.io~nfs/nfs-pv --scope -- mount -t nfs 10.24.3.9:/nfs_data /var/lib/kubelet/pods/0466bd61-8c0b-4d34-ae3b-3d6b1ff5254f/volumes/kubernetes.io~nfs/nfs-pv Output: Running scope as unit run-12513.scope. mount: wrong fs type, bad option, bad superblock on 10.24.3.9:/nfs_data, missing codepage or helper program, or other error (for several filesystems (e.g. nfs, cifs) you might need a /sbin/mount.<type> helper program) In some cases useful info is found in syslog - try dmesg | tail or so.
```

#问题解决
```
在all node安装nfs-utils/nfs-common  注意：不同系统或发行版本安装的包是不一样的
```