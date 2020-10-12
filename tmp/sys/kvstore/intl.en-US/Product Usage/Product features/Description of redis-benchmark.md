# Description of redis-benchmark {#concept_zm4_dy5_ydb .concept}

Redis includes the redis-benchmark utility to test performance of the Redis service.

Options of redis-benchmark are described as follows:

``` {#codeblock_eou_ymq_1g1}
Usage: redis-benchmark [-h] [-p] [-c] [-n[-k]
 -h     Server hostname (default 127.0.0.1)
 -p     Server port (default 6379)
 -s     Server socket (overrides host and port)
 -c     Number of parallel connections (default 50)
 -n     Total number of requests (default 10000)
 -d      Data size of SET/GET value in bytes (default 2)
 -k      1=keep alive 0=reconnect (default 1)
 -r      Use random keys for SET/GET/INCR, random values for SADD
  Using this option the benchmark will get/set keys
  in the form mykey_rand:000000012456 instead of constant
  keys, the argument determines the max
  number of values for the random number. For instance
  if set to 10 only rand:000000000000 - rand:000000000009
  range will be allowed.
 -P       Pipelinerequests. Default 1 (no pipeline).
 -q       Quiet. Just show query/sec values
 —csv     Output in CSV format
 -l       Loop. Run the tests forever
 -t       Only run the comma-separated list of tests. The test
  names are the same as the ones produced as output.
 -I       Idle mode. Just open N idle connections and wait.
```

Some testing commands are listed as follows:

1.  ``` {#codeblock_ujn_uik_ema}
redis-benchmark -h 192.168.1.201 -p 6379 -c 100 -n 100000
```

    This command is used to test the performance of a Redis server. The Redis server runs on a local host and -h specifies the IP address of the host. This server provides the service port 6379. In this testing, the Redis server processes 100 concurrent connections and 100,000 requests.

2.  ``` {#codeblock_cv8_s1z_ita}
redis-benchmark -h 192.168.1.201 -p 6379 -q -d 100
```

    This command is used to test the performance of accessing 100-byte packets.

3.  ``` {#codeblock_527_cx3_7q2}
redis-benchmark -t set,lpush -n 100000 -q
```

    This command is only used to test the performance of specified operations.

4.  ``` {#codeblock_2v4_mi0_vca}
redis-benchmark -n 100000 -q script load "redis.call(‘set’,’foo’,’bar’)"
```

    This command is only used to test the performance of accessing some values.


In the actual testing, the system returns the following error: `writing to socket: connection timed out`.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13934/15620575234247_en-US.png)

Run the `netstat -an` command to check whether the ApsaraDB for Redis instance has used too many ports. If so, restart the instance to release the connections, and perform the testing again.

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13934/15620575234248_en-US.png)

You can submit a ticket to [request technical support](https://selfservice.console.aliyun.com/ticket/createIndex.htm).

