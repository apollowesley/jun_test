

#公网
https://help.aliyun.com/document_detail/28982.html

wget http://logtail-release-cn-shenzhen.oss-cn-shenzhen.aliyuncs.com/linux64/logtail.sh -O logtail.sh; chmod 755 logtail.sh; ./logtail.sh install cn-shenzhen-internet

#logtail
http://logtail-release-cn-shenzhen.oss-cn-shenzhen.aliyuncs.com/linux64/logtail-linux64.tar.gz


./logtail.sh uninstall //卸载

>安装后默认启动Logtail并注册开机启动


[root@k8s-master ~]# ll /usr/local/ilogtail/
total 68292
-rw-rw-rw- 1 root root      351 Jun 22 12:21 app_info.json
-rw-rw-rw- 1 root root     1172 Jun 22 12:21 apsara_log_conf.json
-rw-rw-rw- 1 root root        0 Jun 22 12:21 backtrace.dat
-rwxr-xr-x 1 root root   250283 Jun 22 12:21 ca-bundle.crt
lrwxrwxrwx 1 root root       36 Jun 22 12:21 ilogtail -> /usr/local/ilogtail/ilogtail_0.16.40
-rwxr-xr-x 1 root root 13029848 Jun 22 12:21 ilogtail_0.16.40
-rw-r--r-- 1 root root        5 Jun 22 12:21 ilogtail_0.16.40.pid
-rwxr-xr-x 1 root root      620 Jun 22 12:21 ilogtail_config.json
-rw-rw-rw- 1 root root     6227 Jun 22 12:23 ilogtail.LOG
-rwxr-xr-x 1 root root     8616 Jun 22 12:21 libPluginAdapter.so
-rwxr-xr-x 1 root root 55180040 Jun 22 12:21 libPluginBase.so
-rwxr-xr-x 1 root root  1413680 Jun 22 12:21 LogtailInsight
-rwxr-xr-x 1 root root      597 Jun 22 12:21 README
drwxr-xr-x 2 root root     4096 Jun 22 12:23 snapshot


[root@k8s-master ~]# systemctl status ilogtaild
● ilogtaild.service - LSB: logtail init script
   Loaded: loaded (/etc/rc.d/init.d/ilogtaild; bad; vendor preset: disabled)
   Active: inactive (dead)
     Docs: man:systemd-sysv-generator(8)
[root@k8s-master ~]# 
[root@k8s-master ~]# 


[root@k8s-master ilogtail]# systemctl status ilogtaild


shell@Alicloud:~$ echo $ALIBABA_CLOUD_ACCOUNT_ID
1717267068317753

[root@k8s-master users]# mkdir 1717267068317753
[root@k8s-master users]# ll
total 4
drwxr-xr-x 2 root root 4096 Jun 22 12:38 1717267068317753
[root@k8s-master users]# pwd
/etc/ilogtail/users

10.24.3.9


[root@k8s-master ilogtail]# systemctl restart ilogtaild 
[root@k8s-master ilogtail]# 
[root@k8s-master ilogtail]# 
[root@k8s-master ilogtail]# systemctl status ilogtaild 
● ilogtaild.service - LSB: logtail init script
   Loaded: loaded (/etc/rc.d/init.d/ilogtaild; bad; vendor preset: disabled)
   Active: active (running) since Mon 2020-06-22 12:47:08 CST; 7s ago
     Docs: man:systemd-sysv-generator(8)
  Process: 30632 ExecStop=/etc/rc.d/init.d/ilogtaild stop (code=exited, status=0/SUCCESS)
  Process: 30723 ExecStart=/etc/rc.d/init.d/ilogtaild start (code=exited, status=0/SUCCESS)
    Tasks: 16
   Memory: 3.6M
   CGroup: /system.slice/ilogtaild.service
           ├─30725 /usr/local/ilogtail/ilogtail
           └─30726 /usr/local/ilogtail/ilogtail

Jun 22 12:47:08 k8s-master systemd[1]: Starting LSB: logtail init script...
Jun 22 12:47:08 k8s-master ilogtail[30725]: 30725start worker process, deamon stat :0
Jun 22 12:47:08 k8s-master ilogtaild[30723]: ilogtail is running
Jun 22 12:47:08 k8s-master systemd[1]: Started LSB: logtail init script.
[root@k8s-master ilogtail]# 


