# 如何查看 Redis 集群子实例内存 {#concept_fhm_hfz_xdb .concept}

## 背景信息 { .section}

云数据库 Redis 版集群有多个节点，用户需要查看每个子节点的内存以及 key 的数目，目前阿里云提供了`iinfo`命令用于查看某个节点的性能数据，后续会在控制台展示每个节点的数据。

```
iinfo db_idx [section]
```

其中，db\_idx 的范围是\[0, nodecount\)，nodecount 可以通过 info 命令获取， section 为 info 官方一致的值。

## 操作步骤 { .section}

1.  下载 python 客户端。

    ```
    wget "https://pypi.python.org/packages/68/44/5efe9e98ad83ef5b742ce62a15bea609ed5a0d1caf35b79257ddb324031a/redis-2.10.5.tar.gz#md5=3b26c2b9703b4b56b30a1ad508e31083"
    ```

2.  解压安装 python 客户端。

    ```
    tar -xvf redis-2.10.5.tar.gz
     cd redis-2.10.5
     sudo python setup.py install
    ```

3.  执行以下扫描脚本。

    ```
     import sys
     import redis
     from redis._compat import nativestr
     def parse_info(response):
         "Parse the result of Redis's INFO command into a Python dict"
         info = {}
         response = nativestr(response)
         def get_value(value):
             if ',' not in value or '=' not in value:
                 try:
                     if '.' in value:
                         return float(value)
                     else:
                         return int(value)
                 except ValueError:
                     return value
             else:
                 sub_dict = {}
                 for item in value.split(','):
                     k, v = item.rsplit('=', 1)
                     sub_dict[k] = get_value(v)
                 return sub_dict
         for line in response.splitlines():
             if line and not line.startswith('#'):
                 if line.find(':') != -1:
                     key, value = line.split(':', 1)
                     info[key] = get_value(value)
                 else:
                     # if the line isn't splittable, append it to the "__raw__" key
                     info.setdefault('__raw__', []).append(line)
         return info
     if __name__ == '__main__':
       if len(sys.argv) != 4:
          print 'Usage: python ', sys.argv[0], ' host port password '
          exit(1)
       db_host = sys.argv[1]
       db_port = sys.argv[2]
       db_password = sys.argv[3]
       r = redis.StrictRedis(host=db_host, port=int(db_port), password=db_password)
       nodecount = r.info()['nodecount']
       for node in range(0, nodecount):
          info = r.execute_command("iinfo", str(node))
          info_res = parse_info(info)
          print "============ node ", str(node), " ================"
          print 'used_memory_human:', info_res['used_memory_human']
          print r.execute_command("iinfo", str(node), "keyspace")
       info_res = r.info()
       print "============ total  ================"
       print 'used_memory_human:', info_res['used_memory_human']
       print r.info('keyspace')
    ```

    执行 `python check_sharding_db host port password` 之后会输出如下内容：

    ```
     ============ node  0  ================
     used_memory_human: 37.56M
     # Keyspace
     db0:keys=9887,expires=0,avg_ttl=0
     ============ node  1  ================
     used_memory_human: 37.58M
     # Keyspace
     db0:keys=9835,expires=0,avg_ttl=0
     db1:keys=1,expires=0,avg_ttl=0
     ============ node  2  ================
     used_memory_human: 41.24M
     # Keyspace
     db0:keys=9956,expires=0,avg_ttl=0
     db1:keys=1,expires=0,avg_ttl=0
     ============ node  3  ================
     used_memory_human: 37.58M
     # Keyspace
     db0:keys=9863,expires=0,avg_ttl=0
     ============ node  4  ================
     used_memory_human: 37.61M
     # Keyspace
     db0:keys=10045,expires=0,avg_ttl=0
     ============ node  5  ================
     used_memory_human: 37.58M
     # Keyspace
     db0:keys=10038,expires=0,avg_ttl=0
     ============ node  6  ================
     used_memory_human: 37.58M
     # Keyspace
     db0:keys=10055,expires=0,avg_ttl=0
     ============ node  7  ================
     used_memory_human: 37.57M
     # Keyspace
     db0:keys=9969,expires=0,avg_ttl=0
     ============ total  ================
     used_memory_human: 304.31M
     {'db1': {'keys': 2, 'expires': 0, 'avg_ttl': 0}, 'db0': {'keys': 79648, 'expires': 0, 'avg_ttl': 0}}
    ```


