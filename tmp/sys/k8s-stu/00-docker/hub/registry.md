# registory
https://hub.docker.com/_/registry

https://docs.docker.com/registry/

## reigsitry dirs
/opt/registry
    data    
    conf
    auth

mkdir -p /opt/registry/{data,conf,auth}

## pull images && set htpasswd
```
docker pull registry:2.7
#generate user/password as httpasswd auth
docker run --entrypoint htpasswd registry:2.7 -Bbn admin admin  >> /opt/registry/auth/htpasswd
```

## config
[link](https://docs.docker.com/registry/configuration/)
```
vim /opt/registry/conf/config.yml
version: 0.1
log:
  fields:
    service: registry
storage:
  delete:
    enabled: true
  cache:
    blobdescriptor: inmemory
  filesystem:
    rootdirectory: /var/lib/registry
auth:
  htpasswd:
    realm: basic-realm
    path: /auth/htpasswd
http:
  addr: :5000
  headers:
    X-Content-Type-Options: [nosniff]
health:
  storagedriver:
    enabled: true
    interval: 10s
threshold: 3
```

### run registry
```
docker run -d -p 5000:5000 \
    --restart=always --name=registry\
    -v /opt/registry/conf/:/etc/docker/registry/ \
    -v /opt/registry/auth/:/auth/ \
    -v /opt/registry/data/:/var/lib/registry/ \
    registry:2.7
```

### dockerhub-private-mirror
```
vim /etc/docker/daemon.json
{
	"registry-mirrors":[],
	"insecure-registries":["172.24.0.100:5000"]
}
systemctl daemon-reload && systemctl restart docker
```
`注意：非https,必须配置daemon.json文件。`

### testing
```
//check list
[root@c-3-103 docker]# docker images   
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
nginx               latest              4392e5dad77d        2 days ago          132MB
alpine              latest              a24bb4013296        6 days ago          5.57MB
registry            2.7                 708bc6af7e5e        4 months ago        25.8MB
```

```
//add new tag images
[root@c-3-103 docker]# docker tag alpine 192.168.3.103:5000/alpine

//login
[root@c-3-103 docker]# docker login 192.168.3.103:5000
Username: admin
Password: 
WARNING! Your password will be stored unencrypted in /root/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store
Login Succeeded

//push images to registory
[root@c-3-103 docker]# docker push  192.168.3.103:5000/alpine
```

```
//list images
curl -u admin:admin  http://192.168.3.103:5000/v2/_catalog 

//list alpine images tags
curl -u admin:admin  http://192.168.3.103:5000/v2/alpine/tags/list
```

```
//清理文件系统  有点问题，清理无效
docker exec registry bin/registry garbage-collect /etc/docker/registry/config.yml
```

### registry  https

```
docker run -d -p 5000:5000 --restart=always --name registry \
  -v /opt/registry/certs:/certs \
  -v /opt/registry/data:/var/lib/registry \
  -e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/domain.crt \
  -e REGISTRY_HTTP_TLS_KEY=/certs/domain.key \
  registry:2
```

```
-e REGISTRY_HTTP_ADDR=0.0.0.0:5001

```

### docker-registry
```
[root@node3 ~]# yum info docker-registry
Loaded plugins: fastestmirror, priorities
Loading mirror speeds from cached hostfile
Available Packages
Name        : docker-registry
Arch        : x86_64
Version     : 0.9.1
Release     : 7.el7
Size        : 123 k
Repo        : extras/x86_64
Summary     : Registry server for Docker
URL         : https://github.com/docker/docker-registry
License     : ASL 2.0
Description : Registry server for Docker (hosting/delivering of repositories and images).

yum install -y docker-registry   //extras  默认通信端口5000

#systemctl enable docker-registry
#systemctl start docker-registry

systemctl enable docker-distribution
systemctl start docker-distribution

[root@node3 ~]# ss -lnt
State       Recv-Q Send-Q        Local Address:Port                       Peer Address:Port              
LISTEN      0      128                       *:22                                    *:*                  
LISTEN      0      100               127.0.0.1:25                                    *:*                  
LISTEN      0      128            192.168.3.53:10010                                 *:*                  
LISTEN      0      128                      :::5000                                 :::*                  
LISTEN      0      128                      :::22                                   :::*                  
LISTEN      0      100                     ::1:25                                   :::*   

//docker push 之前需要tag
[root@node3 ~]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
debian              7.0                 a54ffde0dc26        11 hours ago        220MB

[root@node3 ~]# docker tag debian:7.0 192.168.3.53:5000/debian:7.0
[root@node3 ~]# docker push 192.168.3.53:5000/debian:7.0
```

### registry-https
[reference](https://blog.frognew.com/2017/01/deploy-docker-registry-v2.html)

#### 生成自签名证书
因为没有公网ip和域名，所以这里修改`/etc/pki/tls/openssl.cnf`以生成带SAN 扩展的证书,在openssl.cnf文件中修改以下内容:
```
[ v3_ca ]
#指定ip registry_server
subjectAltName=IP:192.168.61.100
```

#### 创建证书目录:
mkdir -p /home/registry/certs
mkdir -p /home/registry/data
//生成自签名证书
cd /home/registry/certs
```
openssl req \
     -newkey rsa:2048 -nodes -keyout domain.key \
     -x509 -days 3650 -out domain.crt
```
>注意：根据提示引导，输入信息创建证书

//查看创建的证书和私钥:
ls
domain.crt  domain.key
openssl x509 -text -noout -in domain.crt

#### 运行registry容器
```
docker run -d -p 5000:5000 --restart=always --name registry \
  -v /home/registry/certs:/certs \
  -v /home/registry/data:/var/lib/registry \
  -e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/domain.crt \
  -e REGISTRY_HTTP_TLS_KEY=/certs/domain.key \
  registry:2
```
#### 分发证书到各个docker节点
将domain.cert拷贝到每个docker所在主机的/etc/docker/certs.d/192.168.61.100:5000/ca.crt
```
mkdir -p /etc/docker/certs.d/192.168.61.100:5000
cp domain.crt /etc/docker/certs.d/192.168.61.100:5000/ca.crt
```

#### 重启docker
`systemctl restart docker`

#### 测试pull和push
```
docker pull alpine
docker tag alpine 192.168.61.100:5000/alpine

docker push 192.168.61.100:5000/alpine

docker rmi 192.168.61.100:5000/alpine
docker rmi alpine

docker pull 192.168.61.100:5000/alpine
```

#### 开启basic认证
```
mkdir -p /home/registry/auth
cd /home/registry/auth
docker run --entrypoint htpasswd registry:2 -Bbn testuser testpassword > htpasswd
```
docker stop registry
docker rm registry

#### 启动https认证的registry
```
docker run -d -p 5000:5000 --restart=always --name registry \
  -v /home/registry/auth:/auth \
  -e "REGISTRY_AUTH=htpasswd" \
  -e "REGISTRY_AUTH_HTPASSWD_REALM=Registry Realm" \
  -e REGISTRY_AUTH_HTPASSWD_PATH=/auth/htpasswd \
  -v /home/registry/certs:/certs \
  -v /home/registry/data:/var/lib/registry \
  -e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/domain.crt \
  -e REGISTRY_HTTP_TLS_KEY=/certs/domain.key \
  registry:2
```

#### 验证login,push
docker login 192.168.61.100:5000
Username: testuser
Password:
Login Succeeded

docker push 192.168.61.100:5000/alpine
