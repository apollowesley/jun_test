# OCI runtime create failed
[link](https://blog.csdn.net/xdshust/article/details/90268206)


#错误描述
```
docker ""process_linux.go:297: copying bootstrap data to pipe caused"
```

#运行环境
```
[root@general-k8s-master ~]# uname -a
Linux general-k8s-master 3.10.0-123.9.3.el7.x86_64 #1 SMP Thu Nov 6 15:06:03 UTC 2014 x86_64 x86_64 x86_64 GNU/Linux
[root@general-k8s-master ~]# 
[root@general-k8s-master ~]# cat /etc/*release
CentOS Linux release 7.7.1908 (Core)
NAME="CentOS Linux"
VERSION="7 (Core)"
ID="centos"
ID_LIKE="rhel fedora"
VERSION_ID="7"
PRETTY_NAME="CentOS Linux 7 (Core)"
ANSI_COLOR="0;31"
CPE_NAME="cpe:/o:centos:centos:7"
HOME_URL="https://www.centos.org/"
BUG_REPORT_URL="https://bugs.centos.org/"

CENTOS_MANTISBT_PROJECT="CentOS-7"
CENTOS_MANTISBT_PROJECT_VERSION="7"
REDHAT_SUPPORT_PRODUCT="centos"
REDHAT_SUPPORT_PRODUCT_VERSION="7"

CentOS Linux release 7.7.1908 (Core)
CentOS Linux release 7.7.1908 (Core)

docker-ce  //Server Version: 19.03.8

```

#问题解决
```
报错原因：docker版本过高，内核版本过低造成。
解决办法：降低docker的版本或升级内核版本
```

# container执行脚本不退出
https://blog.csdn.net/sinat_30603081/article/details/78593777
