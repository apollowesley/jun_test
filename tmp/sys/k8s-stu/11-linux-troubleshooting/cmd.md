# curl
[curl 多场景使用](https://www.cnblogs.com/kingle-study/p/9914125.html)
[curl http_code](https://www.cnblogs.com/kingle-study/p/9764393.html)
https://blog.csdn.net/bbwangj/article/details/73518678


# sed
https://www.cnblogs.com/sunsky303/p/11014634.html

# awk
https://www.cnblogs.com/sunsky303/p/11065315.html
https://blog.csdn.net/weixin_34345560/article/details/89825807
https://blog.csdn.net/bbwangj/article/details/73518741



# rsync
[rsync 参数配置说明(https://www.cnblogs.com/kingle-study/p/9510368.html)


# lograte
[logrtate 切割详解](https://www.cnblogs.com/kingle-study/p/9597651.html)
https://blog.csdn.net/mayifan0/article/details/80453062
https://www.cnblogs.com/sunsky303/p/12187596.html

# rsyslog
https://www.cnblogs.com/sunsky303/p/12597192.html
https://www.cnblogs.com/sunsky303/p/10564638.html

# atop
[atop使用](https://www.cnblogs.com/sunsky303/p/12118173.html)

# nc
https://www.cnblogs.com/sunsky303/p/10601583.html

#signal
https://www.cnblogs.com/sunsky303/p/10838610.html

# route
https://www.cnblogs.com/sunsky303/p/10859356.html

# tcpdump
https://www.cnblogs.com/sunsky303/p/10643303.html
https://blog.csdn.net/bbwangj/article/details/80698171

# xinetd
https://www.cnblogs.com/sunsky303/p/10940700.html


# vmstat
https://blog.csdn.net/bbwangj/article/details/80698215

# dstat
https://blog.csdn.net/bbwangj/article/details/80700688

# tmux

# screen
```
Screen是一款由GNU计划开发的用于命令行终端切换的自由软件。用户可以通过该软件同时连接多个本地或远程的命令行会话，并在其间自由切换。GNU Screen可以看作是窗口管理器的命令行界面版本。它提供了统一的管理多个会话的界面和相应的功能
```

```
yum install -y screen

screen -S t01

screen ls 
screen -r xxx[id]  //恢复连接
```


# supervisor
https://blog.csdn.net/bbwangj/article/details/81059730
https://blog.csdn.net/bbwangj/article/details/82684100
https://blog.csdn.net/bbwangj/article/details/81178225

https://blog.csdn.net/bbwangj/article/details/74357094


# mail
https://blog.csdn.net/bbwangj/article/details/81330341


# samba
https://blog.csdn.net/bbwangj/article/details/81186743



## rpb-build
https://blog.csdn.net/bbwangj/article/details/82764821


## sftp
```
sftp/ssh root@192.168.14.12

get /home/www/download.txt E:/test/

put E:/test/upload.txt /home/www/    
put -r E:/test     /home/www/

SFTP命令汇总
                  cd 路径                        更改到远程目录的路径
                  lcd 路径                       更改到本地目录的路径
                  chgrp group path               将文件path的组更改为group
                  chmod mode path                将文件path的权限更改为mode
                  chown owner path               将文件path的属主更改为owner
                  exit                           退出 sftp
                  help                           显示这个帮助文本
                  get 远程路径                   下载文件
                  ln existingpath linkpath       符号链接远程文件
                  ls [选项] [路径]               显示远程目录列表
                  lls [选项] [路径]              显示本地目录列表
                  mkdir 路径                     创建远程目录
                  lmkdir 路径                    创建本地目录
                  mv oldpath newpath             移动远程文件
                  open [用户@]主机[:端口]        连接到远程主机
                  put 本地路径                   上传文件
                  pwd                            显示远程工作目录
                  lpwd                           打印本地工作目录
                  quit                           退出 sftp
                  rmdir 路径                     移除远程目录
                  lrmdir 路径                    移除本地目录
                  rm 路径                        删除远程文件
                  lrm 路径                       删除本地文件
                  symlink existingpath linkpath  符号链接远程文件
                  version                        显示协议版本

 

 

pwd/lpwd 
```