# centos7
# nfs
#nfs-server
```
yum install nfs-utils rpcbind  -y

systemctl enable rpcbind && systemctl enable nfs
systemctl start rpcbind && systemctl start nfs
```

#nfs-client
```
yum install nfs-utils -y
```
>使用nfs端的节点必须安装nfs-utils

#nfs-user && able write
```
userdel -r nfs
useradd -s /sbin/nologin nfs  && mkdir -p /nfs-share && chmod a+w /nfs-share
```

#nfs-share-dirs
```
tee <<EOF >/etc/exports
/nfs-share 10.0.0.0/8(rw,sync,no_root_squash)
/nfs-share 192.168.0.0/16(rw,sync,no_root_squash)
EOF

[root@c-3-103 ~]# exportfs -a
[root@c-3-103 ~]# showmount -e
Export list for c-3-103:
/nfs-share 192.168.0.0/16,10.0.0.0/8
```

# ubuntu

```
#nfs-server
sudo apt install -y nfs-kernel-server
#sudo apt install portmap

#nfs-share-data
mkdir -p /data/share{1..5}

#nfs-config
sudo vi /etc/exports
/data/share1 10.24.0.0/16(rw,sync,no_subtree_check,no_root_squash)
/data/share2 10.24.0.0/16(rw,sync,no_subtree_check,no_root_squash)
/data/share3 10.24.0.0/16(rw,sync,no_subtree_check,no_root_squash)
/data/share4 10.24.0.0/16(rw,sync,no_subtree_check,no_root_squash)
/data/share5 10.24.0.0/16(rw,sync,no_subtree_check,no_root_squash)

#allow access nfs services
10.24.*

sudo service nfs-kernel-server restart
sudo exportfs -rv
sudo showmount -e localhost

#check stat
sudo nfsstat
sudo rpcinfo
```

#nfs-client
```
sudo apt install -y nfs-common
sudo showmount -e 114.115.170.116
 sudo mount -t nfs 114.115.170.116:/data/share1  /tmp/k8svolume
```
>在k8s上使用nfs pvc,节点必须安装nfs-client


```
showmount:
    -e	显示NFS服务器的共享列表
    -a	显示本机挂载的文件资源的情况NFS资源的情况
    -v	显示版本号
```



```
访问权限选项
设置输出目录只读：ro
设置输出目录读写：rw
用户映射选项
  all_squash：将远程访问的所有普通用户及所属组都映射为匿名用户或用户组（nfsnobody）；
  no_all_squash：与all_squash取反（默认设置）；
  root_squash：将root用户及所属组都映射为匿名用户或用户组（默认设置）；
  no_root_squash：与rootsquash取反；
  anonuid=xxx：将远程访问的所有用户都映射为匿名用户，并指定该用户为本地用户（UID=xxx）；
  anongid=xxx：将远程访问的所有用户组都映射为匿名用户组账户，并指定该匿名用户组账户为本地用户组账户（GID=xxx）；

其它选项
  secure：限制客户端只能从小于1024的tcp/ip端口连接nfs服务器（默认设置）；
  insecure：允许客户端从大于1024的tcp/ip端口连接服务器；
  sync：将数据同步写入内存缓冲区与磁盘中，效率低，但可以保证数据的一致性；
  async：将数据先保存在内存缓冲区中，必要时才写入磁盘；
  wdelay：检查是否有相关的写操作，如果有则将这些写操作一起执行，这样可以提高效率（默认设置）；
  no_wdelay：若有写操作则立即执行，应与sync配合使用；
  subtree：若输出目录是一个子目录，则nfs服务器将检查其父目录的权限(默认设置)；
  no_subtree：即使输出目录是一个子目录，nfs服务器也不检查其父目录的权限，这样可以提高效率；
```


https://blog.csdn.net/bbwangj/article/details/81776624
https://blog.csdn.net/weixin_41806245/article/details/100657034