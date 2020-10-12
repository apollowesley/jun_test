# ubuntu

## vim
```
sudo apt-get update
sudo apt-get install vim
```

## terminal completion
[link](https://www.cnblogs.com/xiaochina/p/5817068.html)
```
useradd -m -d /home/k8s k8s
cp -R /etc/skel/.[!.]* ./        //copy default
chown -R k8s: /home/k8s 
su - k8s
```

## apt
```
sudo add-apt-repository "deb [arch=amd64] http://mirrors.aliyun.com/docker-ce/linux/ubuntu $(lsb_release -cs) stable"

apt-get update

#https
apt-get install -y apt-transport-https ca-certificates
```
>https需要证书效验，http即可


https://blog.csdn.net/sinat_30603081/article/details/90642202

https://blog.csdn.net/sinat_30603081/article/details/81162999

https://blog.csdn.net/sinat_30603081/article/details/69382433


# ping
```
apt-get install inetutils-ping
```

## nc
```
apt-get install -y netcat-openbsd     //bsd
apt-get install -y netcat-traditional //traditional
```


## sudo no passwd
```
/etc/sudoers
%sudo	ALL=(ALL:ALL) NOPASSWD:ALL

id ubuntu |grep sudo 
```

## ubuntu 16 set ip
```
vim /etc/network/interfaces
auto  lo
iface lo inet loopback
#本地网卡设置
auto ens160  //本地网卡名字
iface ens160  inet static  //静态获取
address 172.24.0.110
netmask 255.255.255.0
gateway 172.24.0.254
dns-nameserver 233.5.5.5  //dns配置
dns-nameserver 233.6.6.6

service netwoking restart
```
## ubuntu 18 set ip
```
vi /etc/netplan/50-cloud-init.yaml

network:
  ethernets:
    # 网卡名
    ens33:
      # DHCP：自动分配 IP地址，租期到了会自动续约，IP会变
      dhcp4: no #dhcp4关闭
      dhcp6: no #dhcp6关闭
      # 24 代表子网掩码 255.255.255.0 ，32 个二进制数前 24 位为 1
      addresses: [192.168.153.128/24] #设置本机IP及掩码，前三组数字要与网关一致，第四个数字范围在 128-254 之间
      gateway4: 192.168.153.2 #设置网关
      nameservers:
        addresses: [114.114.114.114, 8.8.8.8] #设置DNS
  version: 2

netplan apply   //配置生效
```