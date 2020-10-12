https://www.elastic.co/guide/index.html


https://discuss.elastic.co/

https://www.cnblogs.com/maoxiangyi/p/12144911.html
https://www.cnblogs.com/juncaoit/p/11230809.html


# linux es+kibana

##环境规划
- elasticsearch/kibana v7.5.0
- openjdk v1.8
- centos7

## download
https://www.elastic.co/cn/downloads/past-releases

[elasticsearch-7.5.0-linux-x86_64.tar.gz](https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.5.0-linux-x86_64.tar.gz)  
[kibana-7.5.0-linux-x86_64.tar.gz](https://artifacts.elastic.co/downloads/kibana/kibana-7.5.0-linux-x86_64.tar.gz)  
[logstash-7.5.0.tar.gz](https://artifacts.elastic.co/downloads/logstash/logstash-7.5.0.tar.gz)

>注意：迅雷下载速度杠杠的

https://www.elastic.co/guide/en/elasticsearch/reference/7.5/index.html

https://www.elastic.co/guide/en/kibana/7.5/index.html


## openjdk/jdk  1.8.x

```
tee <<-'EOF' >>/etc/profile
export JAVA_HOME=/opt/jdk/jdk1.8.0_191
export CLASSPATH=.:$JAVA_HOME/lib
export PATH=$JAVA_HOME/bin:$PATH
EOF

source /etc/profile

[root@c-3-105 jdk1.8.0_191]# java -version 
java version "1.8.0_191"
Java(TM) SE Runtime Environment (build 1.8.0_191-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.191-b12, mixed mode)
[root@c-3-105 jdk1.8.0_191]# 
```


## os 

```
tee <<EOF >>/etc/security/limits.conf
#add es turn
* - nofile 102400
* - nproc  102400
EOF

tee <<EOF >/etc/sysctl.d/es.conf
#es turn
vm.max_map_count = 655350
EOF

sysctl --system  && sysctl -p
```


## elasticsearch 

elasticsearch.ym
```
cluster.name: app-dev
node.name: node1
node.master: true
node.data: true
network.host: 192.168.3.105
http.port: 9200
transport.tcp.port: 9300
transport.tcp.compress: true
discovery.seed_hosts: ["192.168.3.105"]
cluster.initial_master_nodes: [192.168.3.105"]
```

bin/elasticsearch

## kibana




# reference

https://blog.csdn.net/bowenlaw/article/details/104539087

https://www.elastic.co/guide/en/elasticsearch/reference/7.5/reindex-upgrade-remote.html

https://www.elastic.co/guide/en/elasticsearch/reference/7.5/docs-reindex.html


number_of_shards
每个索引的主分片数，默认值是 5 。这个配置在索引创建后不能修改

number_of_replicas
每个主分片的副本数，默认值是 1 。对于活动的索引库，这个配置可以随时修改

Create an index the appropriate mappings and settings. 


Set the refresh_interval to -1
set number_of_replicas to 0 for faster reindexing



//修改当前存在的索引
curl -X PUT "192.xxx.x.xxx:9200/_settings" -H 'Content-Type: application/json' -d '{"index":{"number_of_replicas":0}}'

//未来新建的索引



put
http://192.168.3.105:9200/_template/import_polices
{
    "template": "*",
    "settings": {
        "number_of_replicas": "0",
         "refresh_interval" : -1
    }
}



POST _reindex
{
  "source": {
    "remote": {
      "host": "http://oldhost:9200"
    },
    "index": "source"
  },
  "dest": {
    "index": "dest"
  }
}


for index in i1 i2 i3 i4 i5; do
  curl -HContent-Type:application/json -XPOST localhost:9200/_reindex?pretty -d'{
    "source": {
      "index": "'$index'"
    },
    "dest": {
      "index": "'$index'-reindexed"
    }
  }'
done


POST _reindex/r1A2WoRbTwKZ516z6NEs5A:36619/_rethrottle?requests_per_second=-1
