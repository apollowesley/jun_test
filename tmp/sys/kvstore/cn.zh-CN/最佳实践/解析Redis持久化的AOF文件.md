# 解析Redis持久化的AOF文件 {#concept_j1z_l2q_bgb .concept}

## 背景信息 {#section_r1t_w2q_bgb .section}

在日常开发测试中，为了方便查看历史命令和查看某个Key的记录，需要对AOF文件进行解析。

## Redis持久化模式 {#section_nxr_vm2_cgb .section}

-   RDB快照模式：该模式用于生成某个时间点的备份信息，并且会对当前的Key value进行编码，然后存储在rdb文件中。
-   AOF持久化模式：该模式类似binlog的形式，会记录服务器所有的写请求，在服务重启时用于恢复原有的数据。

## AOF持久化模式的详细说明 {#section_vqn_yn2_cgb .section}

Redis客户端和服务端之间通过RESP \(REdis Serialization Protocol\)进行通信。RESP协议主要由以下几种数据类型组成，每种数据类型的定义如下：

-   简单字符串：

    以+号开头，结尾为rn，比如：+OKrn。

-   错误信息：

    以-号开头，结尾为rn的字符串，比如：-ERR Readonlyrn。

-   整数：

    以冒号开头，结尾为rn，开头和结尾之间为整数，比如（:1rn）。

-   大字符串：

    以$开头，随后为该字符串长度和rn，长度限制512M，最后为字符串内容和rn，比如：$0rnrn。

-   数组：

    以\*开头，随后指定数组元素个数并通过rn划分，每个数组元素都可以为上面的四种，比如：\*1rn$4rnpingrn。


Redis客户端发送给服务端的是一个数组命令，服务端根据不同命令的实现方式进行回复，并记录到AOF文件中。

## AOF文件解析 {#section_c5v_1gq_bgb .section}

这里通过Python代码调用hiredis库来进行Redis AOF文件的解析，代码如下：

```
#!/usr/bin/env python

""" A redis appendonly file parser
"""

import logging
import hiredis
import sys

if len(sys.argv) != 2:
   print sys.argv[0], 'AOF_file'
   sys.exit()
file = open(sys.argv[1])
line = file.readline()
cur_request = line
while line:
    req_reader = hiredis.Reader()
    req_reader.setmaxbuf(0)
    req_reader.feed(cur_request)
    command = req_reader.gets()
    try:
        if command is not False:
            print command
            cur_request = ''
    except hiredis.ProtocolError:
        print 'protocol error'
    line = file.readline()
    cur_request += line
file.close
```

使用以上脚本解析一个AOF文件的结果如下。得到如下结果后方便您随时查看某个Key相关的操作。

```
['PEXPIREAT', 'RedisTestLog', '1479541381558']
['SET', 'RedisTestLog', '39124268']
['PEXPIREAT', 'RedisTestLog', '1479973381559']
['HSET', 'RedisTestLogHash', 'RedisHashField', '16']
['PEXPIREAT', 'RedisTestLogHash', '1479973381561']
['SET', 'RedisTestLogString', '79146']
```

