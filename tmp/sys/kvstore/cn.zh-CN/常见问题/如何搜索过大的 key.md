# 如何搜索过大的 key {#concept_frf_r2z_xdb .concept}

## 背景信息 { .section}

Redis 提供了 list、hash、zset 等复杂类型的数据结构，业务在使用的时候可能由于 key 设计不合理导致某个 key 过大。由于 redis 简单的单线程模型，业务在获取或者删除大 key 的时候都会有一定的影响，另外在集群模式下由于大 key 的产生还很容易导致某个子节点的内存满。综上所述我们需要搜索工具来发现过大的 key。

对于 Redis 主从版本可以通过`scan`命令进行扫描，对于集群版本提供了`ISCAN`命令进行扫描，命令规则如下，其中节点个数 node 可以通过`info`命令来获取。

```
ISCAN idx cursor [MATCH pattern] [COUNT count]
```

其中，idx 为节点的 id，从0开始，16到64 GB 的集群实例为8个节点故 idx 为0到7，128 GB 和256 GB 的为16个节点。

## 操作步骤 { .section}

1.  执行以下命令下载 python 客户端。

    ```
    wget "https://pypi.python.org/packages/68/44/5efe9e98ad83ef5b742ce62a15bea609ed5a0d1caf35b79257ddb324031a/redis-2.10.5.tar.gz#md5=3b26c2b9703b4b56b30a1ad508e31083"
    ```

2.  解压安装 python 客户端。

    ```
     tar -xvf redis-2.10.5.tar.gz
     cd redis-2.10.5
     sudo python setup.py install
    ```

3.  创建以下扫描脚本。

    ```
     import sys
     import redis
     def check_big_key(r, k):
       bigKey = False
       length = 0 
       try:
         type = r.type(k)
         if type == "string":
           length = r.strlen(k)
         elif type == "hash":
           length = r.hlen(k)
         elif type == "list":
           length = r.llen(k)
         elif type == "set":
           length = r.scard(k)
         elif type == "zset":
           length = r.zcard(k)
       except:
         return
       if length > 10240:
         bigKey = True
       if bigKey :
         print db,k,type,length
     def find_big_key_normal(db_host, db_port, db_password, db_num):
       r = redis.StrictRedis(host=db_host, port=db_port, password=db_password, db=db_num)
       for k in r.scan_iter(count=1000):
         check_big_key(r, k)
     def find_big_key_sharding(db_host, db_port, db_password, db_num, nodecount):
       r = redis.StrictRedis(host=db_host, port=db_port, password=db_password, db=db_num)
       cursor = 0
       for node in range(0, nodecount) :
         while True:
           iscan = r.execute_command("iscan",str(node), str(cursor), "count", "1000")
           for k in iscan[1]:
             check_big_key(r, k)
           cursor = iscan[0]
           print cursor, db, node, len(iscan[1])
           if cursor == "0":
             break;
     if __name__ == '__main__':
       if len(sys.argv) != 4:
          print 'Usage: python ', sys.argv[0], ' host port password '
          exit(1)
       db_host = sys.argv[1]
       db_port = sys.argv[2]
       db_password = sys.argv[3]
       r = redis.StrictRedis(host=db_host, port=int(db_port), password=db_password)
       nodecount = r.info()['nodecount']
       keyspace_info = r.info("keyspace")
       for db in keyspace_info:
         print 'check ', db, ' ', keyspace_info[db]
         if nodecount > 1:
           find_big_key_sharding(db_host, db_port, db_password, db.replace("db",""), nodecount)
         else:
           find_big_key_normal(db_host, db_port, db_password, db.replace("db", ""))
    ```

4.  执行`python find_bigkey host 6379 password`命令查找大 key。

    **说明：** 

    该命令支持查找 Redis 主从版本和集群版本的大 key ，默认大 key 的阈值为10240。string 类型的 value 大于10240的是大 key，list 长度大于10240认为是大 key，hash field 的数目大于10240认为是大 key。

    另外默认该脚本每次搜索1000个 key，对业务的影响比较低，不过最好在业务低峰期进行操作，避免 scan 命令对业务的影响。


