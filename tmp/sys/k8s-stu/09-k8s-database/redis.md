# redis

##pika
https://github.com/Qihoo360/pika

https://blog.csdn.net/weixin_34345560/article/details/91414617

## redis 3.x

```
yum install -y gcc gcc-c++ make autoconf automake

$ wget http://download.redis.io/releases/redis-3.2.8.tar.gz
$ tar xzf redis-3.2.8.tar.gz

cd redis-3.2.8/deps/   //编译依赖
make geohash-int hiredis jemalloc linenoise lua


$ cd redis-3.2.8
$ make
```

## redis 4.x


## redis 5.x
https://juejin.im/post/5d16206b518825597909b5f9
https://blog.csdn.net/h2604396739/article/details/89261094


https://blog.csdn.net/sinat_30603081/article/details/78661318
https://www.cnblogs.com/kingle-study/p/10044775.html


#redis数据结构
- string
- list
- dict
- zset
- hash

## redis-cli 
```
redis-cli -h 127.0.0.1 -p 6379

config *
info 
config get xxx
config set xxx

set kv01 'are you ok'
get kv01

del kv01
exist kv01
expire kv01 30  //key过期30s

```




## redis master/slave
#master redis.conf
```

```
>主从机制，主节点挂了，从节点无法提升为主，读写分离提升性能

#slave redis.conf
```
port 6380
bind 127.0.0.1
slaveof 127.0.0.1:6379  //master redis
```

## redis sentinel(哨兵)
```
Redis的Sentinel 系统用于管理多个Redis服务器（instance）该系统执行以下三个任务：
1、监控（Monitoring）  :Sentinel 会不断地检查你的主服务器和从服务器是否运作正常。
2、提醒（Notification）: 当被监控的某个 Redis 服务器出现问题时， Sentinel 可以通过 API 向管理员或者其他应用程序发送通知。
3、自动故障迁移（Automatic failover）： 当一个主服务器不能正常工作时， Sentinel 会开始一次自动故障迁移操作， 它会将失效主服务器的其中一个从服务器升级为新的主服务器， 并让失效主服务器的其他从服务器改为复制新的主服务器； 当客户端试图连接失效的主服务器时， 集群也会向客户端返回新主服务器的地址， 使得集群可以使用新主服务器代替失效服务器。
```




```
sentinel monitor mymaster 127.0.0.1 6379 2 //当前的主master,2个sentinel选举成功后，才有效
sentinel down-after-milliseconds mymaster 60000 //判断主master挂机的时间（毫秒）
sentinel failover-timeout mymaster 180000 //失败的超时时间
sentinel parallel-syncs mymaster 1  //选项指定了在执行故障转移时， 最多可以有多少个从服务器同时对新的主服务器进行同步， 这个数字越小， 完成故障转移所需的时间就越长
```

```
查看redis-sentinel下的主从服务器
SENTINEL masters ：列出所有被监视的主服务器，以及这些主服务器的当前状态。
SENTINEL slaves ：列出给定主服务器的所有从服务器，以及这些从服务器的当前状态。
SENTINEL get-master-addr-by-name ： 返回给定名字的主服务器的 IP 地址和端口号。 如果这个主服务器正在执行故障转移操作， 或者针对这个主服务器的故障转移操作已经完成， 那么这个命令返回新的主服务器的 IP 地址和端口号。
SENTINEL reset ： 重置所有名字和给定模式 pattern 相匹配的主服务器。
SENTINEL failover ： 当主服务器失效时， 在不询问其他 Sentinel 意见的情况下， 强制开始一次自动故障迁移 （不过发起故障转移的 Sentinel 会向其他 Sentinel 发送一个新的配置，其他 Sentinel 会根据这个配置进行相应的更新）。
```

>哨兵机制主节点挂了哨兵节点可以投票选举master节点


## redis + twemproxy
https://github.com/twitter/twemproxy
```
twemproxy作为redis代理
```



```
$ git clone git@github.com:twitter/twemproxy.git
$ cd twemproxy
$ autoreconf -fvi
$ ./configure --enable-debug=log
$ make


# cd /usr/local/src/nutcracker-0.3.0/conf
# cp nutcracker.yml /etc/
# vim /etc/nutcracker.yml
alpha:
  listen: 192.168.128.128:22121
  hash: fnv1a_64
  distribution: ketama
  auto_eject_hosts: true
  redis: true
  server_retry_timeout: 2000
  server_failure_limit: 1
  servers: --两台redis服务器的地址和端口
   - 192.168.2.1:6379:1   
   - 192.168.2.2:6379:1


nutcracker -t /etc/nutcracker.yml 
nutcracker -d -c /etc/nutcracker.yml
ps -ef|grep nutcracker

redis-cli -h xx -p yy
```







## redis key淘汰策略
```
LRU(Least Recently Used)最近最少使用算法是众多置换算法中的一种。

maxmemory
Redis中有一个maxmemory概念，主要是为了将使用的内存限定在一个固定的大小

设置maxmemory
127.0.0.1:6379> CONFIG GET maxmemory
1) "maxmemory"
2) "0"
127.0.0.1:6379> CONFIG SET maxmemory 100MB
OK
127.0.0.1:6379> CONFIG GET maxmemory
1) "maxmemory"
2) "104857600"

置换策略
当Redis内存使用达到maxmemory时，需要选择设置好的maxmemory-policy进行对老数据的置换。
下面是可以选择的置换策略：

noeviction: 不进行置换，表示即使内存达到上限也不进行置换，所有能引起内存增加的命令都会返回error
allkeys-lru: 优先删除掉最近最不经常使用的key，用以保存新数据
volatile-lru: 只从设置失效（expire set）的key中选择最近最不经常使用的key进行删除，用以保存新数据
allkeys-random: 随机从all-keys中选择一些key进行删除，用以保存新数据
volatile-random: 只从设置失效（expire set）的key中，选择一些key进行删除，用以保存新数据
volatile-ttl: 只从设置失效（expire set）的key中，选出存活时间（TTL）最短的key进行删除，用以保存新数据
volatile-lru和volatile-random经常在一个Redis实例既做cache又做持久化的情况下用到，然而，更好的选择使用两个Redis实例来解决这个问题。设置是失效时间expire会占用一些内存，而采用allkeys-lru就没有必要设置失效时间，进而更有效的利用内存。

3 置换策略是如何工作的
理解置换策略的执行方式是非常重要的，比如：

客户端执行一条新命令，导致数据库需要增加数据（比如set key value）
Redis会检查内存使用，如果内存使用超过maxmemory，就会按照置换策略删除一些key
新的命令执行成功
我们持续的写数据会导致内存达到或超出上限maxmemory，但是置换策略会将内存使用降低到上限以下。

如果一次需要使用很多的内存（比如一次写入一个很大的set），那么，Redis的内存使用可能超出最大内存限制一段时间。

设置转换策略

127.0.0.1:6379> CONFIG GET maxmemory-policy
1) "maxmemory"
2) "0"
127.0.0.1:6379> CONFIG SET maxmemory-policy allkeys-lru
OK
127.0.0.1:6379> CONFIG GET maxmemory-policy
1) "maxmemory-policy"
2) "allkeys-lru"
```



```

docker run --rm -it -v -v /home/ingcreations/redis_cluster:/data:rw   redis  bash 

redis-check-aof --fix appendonly.aof

//redis 
ingcreations@vm-1:~/redis_cluster$ cat redis-cluster.tmpl 
port ${PORT}
protected-mode no
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
cluster-announce-ip 172.16.243.229
cluster-announce-port ${PORT}
cluster-announce-bus-port 1${PORT}
appendonly yes

ingcreations@vm-1:~/redis_cluster$ cat redis_nodes_conf.sh 
for port in `seq 7000 7005`; do \
  mkdir -p ./${port}/conf \
  && PORT=${port} envsubst < ./redis-cluster.tmpl > ./${port}/conf/redis.conf \
  && mkdir -p ./${port}/data; \
done


ingcreations@vm-1:~/redis_cluster$ cat redis_nodes_start.sh 
for port in `seq 7000 7005`; do \
  sudo docker run -d -ti -p ${port}:${port} -p 1${port}:1${port} \
  -v /home/ingcreations/redis_cluster/${port}/conf/redis.conf:/usr/local/etc/redis/redis.conf:rw \
  -v /home/ingcreations/redis_cluster/${port}/data:/data:rw \
  --restart always --name redis-${port} --net redis-net \
  --sysctl net.core.somaxconn=1024 redis redis-server /usr/local/etc/redis/redis.conf; \
done


ingcreations@vm-1:~/redis_cluster$ cat redis_nodes_stop.sh 
sudo docker stop `sudo docker ps -a | grep redis-7* | awk '{print $1}'`
sudo docker rm `sudo docker ps -a | grep redis-7* | awk '{print $1}'`


ingcreations@vm-1:~/redis_cluster$ cat redis_cluster_start.sh 
sudo docker run -it --link redis-7000:redis --net redis-net --rm redis redis-cli --cluster create 172.16.243.229:7000 172.16.243.229:7001 172.16.243.229:7002 172.16.243.229:7003 172.16.243.229:7004 172.16.243.229:7005 17
2.16.243.228:7006 172.16.243.228:7007 172.16.243.228:7008 --cluster-replicas 1


```


#redis备份恢复
https://blog.csdn.net/weixin_34345560/article/details/92139278


##redis哨兵机制
https://blog.csdn.net/lpp_dd/article/details/75040708
