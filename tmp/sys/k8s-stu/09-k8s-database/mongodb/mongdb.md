# mongodb
https://repo.mongodb.org/
https://gitee.com/mirrors/MongoDB
https://docs.mongodb.com/manual/

##mirrors
https://repo.mongodb.org/yum/redhat/7Server/mongodb-org/3.6/x86_64/


https://www.cnblogs.com/kingle-study/p/10059863.html
https://www.cnblogs.com/kingle-study/p/10058847.html
https://blog.csdn.net/sinat_30603081/article/details/81010181
https://www.cnblogs.com/lori/p/4467665.html
https://www.oschina.net/news/97524/mongodb-4-0-released?from=20180701
https://blog.csdn.net/bbwangj/article/details/103651140
https://blog.csdn.net/bbwangj/article/details/104235498

https://blog.csdn.net/wangshuminjava/article/details/88545322
https://blog.csdn.net/wangshuminjava/article/details/87933245
https://blog.csdn.net/wangshuminjava/article/details/87704113


# mongodb运维
[link](https://www.jianshu.com/p/ec75843d9b63)
https://www.jianshu.com/p/43f0614ec008


#mongodb binary
```
mongod	是数据库服务端，不能暴露再公共网络，主要实现服务器端数据库的数据处理、数据访问管理及其他后台管理
mongo	客户端 shell 程序
mongos	路由管理程序，用于分片集群环境下系统访问的路由管理
mongostat	运行状态监控工具
mongotop	监控工具，对读写数据进行统计

mongodump	    导出备份工具（二进制）
mongorestore	备份数据恢复工具，跟mongodump一起使用（二进制）

mongoexport	以JSON或CSV格式导出数据库数据
mongoimport	备份数据恢复工具，跟mongoexport一起使用

bsondump	将BSON文件转换为可阅读的格式
mongofiles	把任何数据类型文件上传到MongoDB中
mongooplog	以Oplog轮询方式，实现对远程服务器上的数据同步到本地磁盘
mongoperf	测试磁盘IO性能的工具
```

#mongodb config
/etc/mongod.conf

#network
```
net: 
   port: localhost
   # 绑定可访问的IP地址，IP地址用","隔开，默认localhost
   bindIp: <string>
   # 是否绑定所有IP，默认 false
   bindIpAll: false
   # 最大并发连接数，默认65536
   maxIncomingConnections: 65536
   # 客户端请求内容验证，默认true
   wireObjectCheck: true
   compression:
      # 数据压缩，默认：snappy，还支持zlib。zlib压缩率高，但是慢。
      # snappy 比 zlib 更快，但文件相对要大 20% 到 100%。
      compressors: snappy
   serviceExecutor: <string>
```
#存储配置
```
storage:
   # 实例存储数据的目录，不要随意修改，需要和安装时一直。
   dbPath: /data/db
   # 索引重建，Mongod重启会删除不完整索引，然后尝试重建不完整索引 
   indexBuildRetry: true
   # 数据库修复临时存储路径
   repairPath: <string>
   # 数据操作记录日志，只适用于Mongod
   journal:
      enabled: true
      # Mongod从内存想数据操作记录日志提交数据的最大间隔时间（ms）
      commitIntervalMs: <num>
   # 给每个数据库建立独立的子文件路径
   directoryPerDB: false
   # 数据刷新到数据库文件的间隔时间（秒）
   syncPeriodSecs: 60
   # 数据库存储引擎（wiredTiger/MMAPv1/inMemory）
   engine: wiredTiger
   # 相对于MMAPv1，wiredTiger可以压缩最大80%的空间
   wiredTiger:
      engineConfig:
         # 数据在内存中的缓存空间，默认：50%RAM-1G或256M
         cacheSizeGB: <number>
         # 压缩方式（none/snappy/zlib），压缩wiredTiger日志
         journalCompressor: snappy
         # Mongod是否将索引和集合文件分开存放
         directoryForIndexes: false
      collectionConfig:
         # 集合数据的压缩方式（none/snappy/zlib）
         blockCompressor: snappy
      indexConfig:
         # 启用索引数据的前缀压缩功能
         prefixCompression: true
```
#审计
```
auditLog:
   # syslog：以json格式保存身份验证到syslog
   # console：以json格式输出信息到标准输出
   # file：以json格式输出信息到文件
   destination: <string>
   # JSON：输出json格式文件；BSON：输出bson二进制格式文件
   format: <string>
   path: <string>
   filter: <string>
```


# docker
https://hub.docker.com/_/mongo

```
mkdir -p /data/mongo/mongod-1/{etc,data}
docker run --name mongod-1 -p 27017:27017  -v /data/mongo/mongod-1/data:/data/db -d mongo
#-v /data/mongo/mongod-1/etc:/etc/mongo 配置文件，不存在则默认

#useing
[root@c-3 mongod-1]# docker ps |grep mongo
[root@c-3 mongod-1]# docker exec -it dc9 bash
root@dc92593083f1:/# mongo  //mogo-cli
```

# yum 
```
#添加yum源
[root@gz-tencent ~]# cat > /etc/yum.repos.d/mongodb-org-4.0.repo << EOF
[mongodb-org-4.0]
name=MongoDB Repository
baseurl=https://repo.mongodb.org/yum/redhat/$releasever/mongodb-org/4.0/x86_64/
gpgcheck=1
enabled=1
gpgkey=https://www.mongodb.org/static/pgp/server-4.0.asc
EOF


[root@gz-tencent ~]# cat > /etc/yum.repos.d/mongodb-org-4.0.repo << EOF
[mongodb-org-3.6]
name=MongoDB Repository
baseurl=https://repo.mongodb.org/yum/redhat/$releasever/mongodb-org/3.6/x86_64/
gpgcheck=0
enabled=1
gpgkey=https://www.mongodb.org/static/pgp/server-4.0.asc
EOF


#安装mongo
[root@gz-tencent ~]# yum install -y mongodb-org
[root@gz-tencent ~]# service mongod start
[root@gz-tencent ~]# chkconfig mongod on

#连接shell
[root@gz-tencent ~]# mongo
```

# binary


# mongodb 基于角色的安全设置
https://www.jianshu.com/p/cf822d8c26e7


# mongodb 数据备份迁移
https://www.jianshu.com/p/d65190e16afe


# mongodb 查询使用
https://www.jianshu.com/p/116bc7d87bef


# mongodb troubleshooting
#异常关机导致的无法重启
```
删除data目录下的 *.lock文件
运行下 mongod -repair -f config文件路径再启动即可

也可以在/etc/init.d/mongod 服务启动的文件中加入 启动前删除该文件
start() {
rm -f /usr/mongodb/data/master/mongod.lock
/usr/mongodb/bin/mongod --config /usr/mongodb/config/master.conf
}

开启Journal日志。开启：--journal ；
关闭：--nojournal ,默认时间是100ms，启动时会在数据目录下创建一个journal地文件目录，在受到毁坏时，再启动MongoDB不需要再运行repair，它会自动恢复的。
```










### 备份恢复


mongodump/mongorestore
```
mongodump常用参数
   --db：指定导出的数据库
   --collection：指定导出的集合
   --excludeCollection：指定不导出的集合
   --host ：远程ip
   --username：开启身份验证后，用户的登录名
   --password：用户的密码
   --out（指定输出目录）：如果不使用这个参数，mongodump将输出文件保存在当前工作目录中名为dump的目录中
   --archive：导出归档文件，最后只会生成一个文件
   --gzip：压缩归档的数据库文件，文件的后缀名为.gz
注意： --archive 与 --out 不能一起用

//dump tables
mongodump --db database --collection collectionName

mongodump --db database --excludeCollection=collection1 --excludeCollection=collection2 

mongodump --host 127.0.0.1 --port 27017 --username user--password "pass" --out filePath

mongodump --archive=filename --db databse

mongodump --archive=filename.gz --gzip --db database 

mongodump  --gzip --db database 

//利用管道先备份在还原
mongodump --host ipAdress --port 27017 --username user--password "pass" --archive | mongorestore --archive 
```

#rmongorestore
```
mongorestore常用参数(这里只列与mongodump不同的参数)，使用
   --nsInclude ：指定还原的集合，支持通配符（*）
   --nsExclude：指定不还原的集合，支持通配符（*）
   --nsFrom：修改集合名称，原来集合的名称，支持使用变量
   --nsTo：修改集合名称，修改之后集合的名称，支持使用变量

#写法1
mongorestore --collection collection --db database filePath
#写法2(推荐写法)
mongorestore --nsInclude database.collections filePath

#--nsInclude指定要 还原 的集合，--nsExclude指定 不还原 的集合
mongorestore --nsInclude database.* --nsExclude database.*   filePath

在还原过程中修改集合的名称
mongorestore --nsInclude database.collection --nsFrom database.collectio --nsTo newCollectionName

本地数据还原到远程服务器
mongorestore --host ip  --port 27017 --username user--password 'pass' filePath

mongorestore --archive=filename --db database
mongorestore --gzip --archive=filename --db database


```
备份:
mongodump -h 127.0.0.1:12001 --collection log_aliyun_operation --db log --gzip --archive=/home/20180407.archive

还原:
mongorestore  -h 127.0.0.1:12001--gzip --archive=/home/20180407.archive

```
[root@able-k8s-master rol_db]# mongodump --help
Usage:
  mongodump <options>

Export the content of a running server into .bson files.

Specify a database with -d and a collection with -c to only dump that database or collection.

See http://docs.mongodb.org/manual/reference/program/mongodump/ for more information.

general options:
      --help                                                print usage
      --version                                             print the tool version and exit

verbosity options:
  -v, --verbose=<level>                                     more detailed log output (include multiple times for more verbosity, e.g. -vvvvv, or specify a numeric value, e.g. --verbose=N)
      --quiet                                               hide all log output

connection options:
  -h, --host=<hostname>                                     mongodb host to connect to (setname/host1,host2 for replica sets)
      --port=<port>                                         server port (can also use --host hostname:port)

authentication options:
  -u, --username=<username>                                 username for authentication
  -p, --password=<password>                                 password for authentication
      --authenticationDatabase=<database-name>              database that holds the user's credentials
      --authenticationMechanism=<mechanism>                 authentication mechanism to use

namespace options:
  -d, --db=<database-name>                                  database to use
  -c, --collection=<collection-name>                        collection to use

uri options:
      --uri=mongodb-uri                                     mongodb uri connection string

query options:
  -q, --query=                                              query filter, as a JSON string, e.g., '{x:{$gt:1}}'
      --queryFile=                                          path to a file containing a query filter (JSON)
      --readPreference=<string>|<json>                      specify either a preference name or a preference json object
      --forceTableScan                                      force a table scan

output options:
  -o, --out=<directory-path>                                output directory, or '-' for stdout (defaults to 'dump')
      --gzip                                                compress archive our collection output with Gzip
      --repair                                              try to recover documents from damaged data files (not supported by all storage engines)
      --oplog                                               use oplog for taking a point-in-time snapshot
      --archive=<file-path>                                 dump as an archive to the specified path. If flag is specified without a value, archive is written to stdout
      --dumpDbUsersAndRoles                                 dump user and role definitions for the specified database
      --excludeCollection=<collection-name>                 collection to exclude from the dump (may be specified multiple times to exclude additional collections)
      --excludeCollectionsWithPrefix=<collection-prefix>    exclude all collections from the dump that have the given prefix (may be specified multiple times to exclude additional prefixes)
  -j, --numParallelCollections=                             number of collections to dump in parallel (4 by default) (default: 4)
      --viewsAsCollections                                  dump views as normal collections with their produced data, omitting standard collections
```

```
[root@able-k8s-master rol_db]# mongorestore --help
Usage:
  mongorestore <options> <directory or file to restore>

Restore backups generated with mongodump to a running server.

Specify a database with -d to restore a single database from the target directory,
or use -d and -c to restore a single collection from a single .bson file.

See http://docs.mongodb.org/manual/reference/program/mongorestore/ for more information.

general options:
      --help                                                print usage
      --version                                             print the tool version and exit

verbosity options:
  -v, --verbose=<level>                                     more detailed log output (include multiple times for more verbosity, e.g. -vvvvv, or specify a numeric value, e.g. --verbose=N)
      --quiet                                               hide all log output

connection options:
  -h, --host=<hostname>                                     mongodb host to connect to (setname/host1,host2 for replica sets)
      --port=<port>                                         server port (can also use --host hostname:port)

authentication options:
  -u, --username=<username>                                 username for authentication
  -p, --password=<password>                                 password for authentication
      --authenticationDatabase=<database-name>              database that holds the user's credentials
      --authenticationMechanism=<mechanism>                 authentication mechanism to use

uri options:
      --uri=mongodb-uri                                     mongodb uri connection string

namespace options:
  -d, --db=<database-name>                                  database to use when restoring from a BSON file
  -c, --collection=<collection-name>                        collection to use when restoring from a BSON file
      --excludeCollection=<collection-name>                 DEPRECATED; collection to skip over during restore (may be specified multiple times to exclude additional collections)
      --excludeCollectionsWithPrefix=<collection-prefix>    DEPRECATED; collections to skip over during restore that have the given prefix (may be specified multiple times to exclude additional
                                                            prefixes)
      --nsExclude=<namespace-pattern>                       exclude matching namespaces
      --nsInclude=<namespace-pattern>                       include matching namespaces
      --nsFrom=<namespace-pattern>                          rename matching namespaces, must have matching nsTo
      --nsTo=<namespace-pattern>                            rename matched namespaces, must have matching nsFrom

input options:
      --objcheck                                            validate all objects before inserting
      --oplogReplay                                         replay oplog for point-in-time restore
      --oplogLimit=<seconds>[:ordinal]                      only include oplog entries before the provided Timestamp
      --oplogFile=<filename>                                oplog file to use for replay of oplog
      --archive=<filename>                                  restore dump from the specified archive file.  If flag is specified without a value, archive is read from stdin
      --restoreDbUsersAndRoles                              restore user and role definitions for the given database
      --dir=<directory-name>                                input directory, use '-' for stdin
      --gzip                                                decompress gzipped input

restore options:
      --drop                                                drop each collection before import
      --dryRun                                              view summary without importing anything. recommended with verbosity
      --writeConcern=<write-concern>                        write concern options e.g. --writeConcern majority, --writeConcern '{w: 3, wtimeout: 500, fsync: true, j: true}'
      --noIndexRestore                                      don't restore indexes
      --noOptionsRestore                                    don't restore collection options
      --keepIndexVersion                                    don't update index version
      --maintainInsertionOrder                              preserve order of documents during restoration
  -j, --numParallelCollections=                             number of collections to restore in parallel (4 by default) (default: 4)
      --numInsertionWorkersPerCollection=                   number of insert operations to run concurrently per collection (1 by default) (default: 1)
      --stopOnError                                         stop restoring if an error is encountered on insert (off by default)
      --bypassDocumentValidation                            bypass document validation
      --preserveUUID                                        preserve original collection UUIDs (off by default, requires drop)
[root@able-k8s-master rol_db]# 
```

