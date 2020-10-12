# download
https://access.redhat.com/products/red-hat-enterprise-linux

# troubleshooting
https://forums.centos.org/index.php

## resolv.conf
[resolv.conf search作用](https://www.cnblogs.com/zhrngM/p/9935540.html)
```
#resolv.conf 工作流程

search的作用就是补全要访问的短域名
正确的域名解析顺序是:
1. 查找/etc/hosts
2. 根据nameserver查找域名
3. 如果在nameserver查找不到域名就进行search补全，重新走1~2步
```

## firewalld
[](https://blog.csdn.net/weixin_34163741/article/details/94649059)

## update kernel
https://www.jianshu.com/p/f467a7bb7da9

## soft raid5
```
# 分区 
fdisk/gdisk (L选择fd,linux raid auto)
#对sdb1和sdc1做RAID0
mdadm -C /dev/md0 -a yes -l 0 -n 2 /dev/sd{b,c}1
# 查看
mdadm -D /dev/md0
## 格式化
mkfs.ext4 /dev/md0
# 挂载
mkdir /mnt/raid0
mount /dev/md0 /mnt/raid0
```
## high cpu
https://blog.csdn.net/wangshuminjava/article/details/105527912



## firewalld
[](https://blog.csdn.net/weixin_34163741/article/details/94649059)


https://blog.csdn.net/bbwangj/article/details/78593281


## 百万链接优化
https://blog.csdn.net/wangshuminjava/article/details/97616650

## selinux
https://blog.csdn.net/bbwangj/article/details/77802359


## eth0
https://blog.csdn.net/bbwangj/article/details/81530381


## selinux
https://blog.csdn.net/yonggeit/article/details/72388965



## install pkg
yum install -y net-tools bash-completion

## lvm
https://cloud.tencent.com/developer/article/1114866