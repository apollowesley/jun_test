# harbor
https://github.com/goharbor/harbor  
https://goharbor.io/  

https://github.com/goharbor/harbor/releases/tag/v2.0.0

https://github.com/goharbor/harbor/tree/master/docs


[harbor 升级](https://blog.csdn.net/shenhonglei1234/article/details/84857108)

[harbor镜像扫描](https://blog.csdn.net/shenhonglei1234/article/details/102836131)

## Docker-Harbor私有仓库安装
[link](https://www.jianshu.com/p/049f671c9b3f)  
[link](https://www.cnblogs.com/Honeycomb/p/10320108.html)  
[harbor 仓库搭建](https://www.cnblogs.com/kingle-study/p/10422534.html)
https://blog.csdn.net/weixin_41806245/article/details/88420329


## opt-env
- centos7 2c4g
- docker-ce 18.06.3
- harbor v1.5.3

>[download](https://github.com/goharbor/harbor/releases)

## extract && config

```
[root@c-3-103 ~]# tar zxf harbor-offline-installer-v1.5.3.tgz 
```


### login 
http://192.168.3.103/harbor  
admin/Harbor12345


### troubleshooting

```
[root@c-3-103 ~]# docker login 192.168.3.103
Username: admin
Password: 
Error response from daemon: Get https://192.168.3.103/v2/: x509: cannot validate certificate for 192.168.3.103 because it doesn't con
[root@c-3-103 ~]# 

```

在 Red Hat (CentOS etc) 上, 命令如下:
cp yourdomain.com.crt /etc/pki/ca-trust/source/anchors/reg.yourdomain.com.crt
update-ca-trust



### harbor https

```
docker-compose down -v    

harbor.cfg/harbor.yaml 
hostname = 192.168.3.103
ui_url_protocol = https
ssl_cert =
ssl_cert_key = 
```

### 生成证书

- 生成ca证书
```
openssl req \
    -newkey rsa:4096 -nodes -sha256 -keyout ca.key \
    -x509 -days 365 -out ca.crt  
```
- 生成证书签名
```
openssl req \
    -newkey rsa:4096 -nodes -sha256 -keyout yourdomain.com.key \
    -out yourdomain.com.csr
```
- FQDN方式生成注册表主机的证书
```
openssl x509 -req -days 3650 \
     -in yourdomain.com.csr -CA ca.crt \
     -CAkey ca.key -CAcreateserial \
     -out yourdomain.com.crt
```
>注意:以上yourdomain.com替换为要使用的FQDN必须和harbor中的hostname以及ssl_cert配置相同

### 配置启动

```
cp yourdomain.com.crt /data/cert/
cp yourdomain.com.key /data/cert/ 
```

- Harbor生成配置文件  
`./prepare`


- 重启Harbor  
```
docker-compose down -v
docker-compose up -d
docker-compose logs -f
docker-compose ps
```

### troubelshooting

```
ExecStart=/usr/bin/dockerd --insecure-registry=192.168.6.113

```

通过将证书复制到docker自有的目录下并重启Harbor和docker即可登录
` sudo cp xxx.crt /etc/docker/cert.d/xxxx(你的注册地址如不存在创建即可)/

- 将证书加入系统级别信任  
```
//Red Hat (CentOS etc)
cp yourdomain.com.crt /etc/pki/ca-trust/source/anchors/reg.yourdomain.com.crt

update-ca-trust
```


### reference
https://www.jianshu.com/p/a2a1ae705b5a
https://blog.51cto.com/linsj/2321283?source=dra



### openssl签发证书
Openssl 自签名证书生成脚本
[link](https://github.com/michaelliao/itranswarp.js/blob/master/conf/ssl/gencert.sh)

```
#!/bin/sh

# create self-signed server certificate:

read -p "Enter your domain [www.example.com]: " DOMAIN

echo "Create server key..."

openssl genrsa -des3 -out $DOMAIN.key 1024

echo "Create server certificate signing request..."

SUBJECT="/C=US/ST=Mars/L=iTranswarp/O=iTranswarp/OU=iTranswarp/CN=$DOMAIN"

openssl req -new -subj $SUBJECT -key $DOMAIN.key -out $DOMAIN.csr

echo "Remove password..."

mv $DOMAIN.key $DOMAIN.origin.key
openssl rsa -in $DOMAIN.origin.key -out $DOMAIN.key

echo "Sign SSL certificate..."

openssl x509 -req -days 3650 -in $DOMAIN.csr -signkey $DOMAIN.key -out $DOMAIN.crt

echo "TODO:"
echo "Copy $DOMAIN.crt to /etc/nginx/ssl/$DOMAIN.crt"
echo "Copy $DOMAIN.key to /etc/nginx/ssl/$DOMAIN.key"
echo "Add configuration in nginx:"
echo "server {"
echo "    ..."
echo "    listen 443 ssl;"
echo "    ssl_certificate     /etc/nginx/ssl/$DOMAIN.crt;"
echo "    ssl_certificate_key /etc/nginx/ssl/$DOMAIN.key;"
echo "}"
```
