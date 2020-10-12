# alpine
alpine轻量级基于busybox的发行版，特别适合基于docker的base images.

https://alpinelinux.org/
https://pkgs.alpinelinux.org/packages
http://dl-cdn.alpinelinux.org/

https://hub.docker.com/_/alpine
http://dl-cdn.alpinelinux.org/alpine/  ###镜像库

###/etc/apk/repositories
http://dl-cdn.alpinelinux.org/alpine/v3.11/main
http://dl-cdn.alpinelinux.org/alpine/v3.11/community
###替换国内TUNA源
sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories


[link](https://www.cnblogs.com/xiaochina/p/10480774.html)



#add china mirrros
```
清华TUNA镜像源：https://mirror.tuna.tsinghua.edu.cn/alpine/
中科大镜像源：http://mirrors.ustc.edu.cn/alpine/
阿里云镜像源：http://mirrors.aliyun.com/alpine/

echo "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/main" > /etc/apk/repositories \
    && echo "https://mirror.tuna.tsinghua.edu.cn/alpine/v3.4/community" >> /etc/apk/repositories \
    && echo "https://mirror.tuna.tsinghua.edu.cn/alpine/edge/testing" >> /etc/apk/repositories

apk --update add --no-cache
```



# APK
```
/ # apk --help
apk-tools 2.10.5, compiled for x86_64.

Installing and removing packages:
  add       Add PACKAGEs to 'world' and install (or upgrade) them, while ensuring that all dependencies are met
  del       Remove PACKAGEs from 'world' and uninstall them

System maintenance:
  fix       Repair package or upgrade it without modifying main dependencies
  update    Update repository indexes from all remote repositories
  upgrade   Upgrade currently installed packages to match repositories
  cache     Download missing PACKAGEs to cache and/or delete unneeded files from cache

Querying information about packages:
  info      Give detailed information about PACKAGEs or repositories
  list      List packages by PATTERN and other criteria
  search    Search package by PATTERNs or by indexed dependencies
  dot       Generate graphviz graphs
  policy    Show repository policy for packages

Repository maintenance:
  index     Create repository index file from FILEs
  fetch     Download PACKAGEs from global repositories to a local directory
  verify    Verify package integrity and signature
  manifest  Show checksums of package contents

Use apk <command> --help for command-specific help.
Use apk --help --verbose for a full command listing.

This apk has coffee making abilities

//common userd
apk add  openssh --no-cache


$ apk add asterisk=1.6.0.21-r0
$ apk add 'asterisk<1.6.1'
$ apk add 'asterisk>1.6.1

apk update #更新最新本地镜像源
apk upgrade #升级软件
apk add --upgrade busybox #指定升级部分软件包
apk search #查找所以可用软件包
apk search -v #查找所有可用软件包及其描述内容
apk search -v 'acf*' #通过软件包名称查找软件包
apk search -v -d 'docker' #通过描述文件查找特定的软件包
apk info #列出所有已安装的软件包
apk info -a zlib #显示完整的软件包信息
apk info --who-owns /sbin/lbu #显示指定文件属于的包
apk add --allow-untrusted /path/to/file.apk  #本地安装

apk add docker --update-cache --repository http://mirrors.ustc.edu.cn/alpine/v3.4/main/ --allow-untrusted
```

#virtual packages
apk add --virtual mypacks gcc vim
apk del mypacks
>注意：同一个-t参数会覆盖之前的所有安装包，所有对动态链接库最好不使用-t 

#安装编译环境
```
apk add --virtual .build-base --no-cache autoconf automake gcc g++  make linux-headers bsd-compat-headers 

apk del .build-base
```



[root@bcc ~]# docker pull alpine
[root@bcc ~]# docker run --rm -it alpine  sh   //entrypoint  /bin/sh -c


#sync times
```
echo "Asia/Shanghai" > /etc/timezone
apk add –no-cache tzdata
TZ=Asia/Shanghai
ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
```



## alpine images bash
#Dockerfile
```
FROM alpine:3.7
MAINTAINER mvpbang mvpbang@qq.com
###alpine+bash
ADD .bashrc /root/
RUN set -x \
    apk update \
    && apk add --no-cache bash bash-doc bash-completion \
    && sed -i 's/ash/bash/g' /etc/passwd \
    && source /root/.bashrc
CMD ["/bin/bash"]
```

#.bashrc
```
export HISTTIMEFORMAT="%d/%m/%y %T "
export PS1='\u@\h:\W \$ '
alias ll='ls -alF'
alias ls='ls --color=auto'
source /etc/profile.d/bash_completion.sh
```


docker build -t alpine:v1 .

docker run --rm -it alpine:v1 ip a