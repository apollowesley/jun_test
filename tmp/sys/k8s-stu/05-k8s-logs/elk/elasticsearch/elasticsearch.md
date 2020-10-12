# elasticsearch
https://discuss.elastic.co/search  //discuss-elastic
https://www.elastic.co/guide/en/elasticsearch/reference/7.x/index.html


## analysis-plugin
#ik
```
https://github.com/medcl/elasticsearch-analysis-ik/releases/
```

## sql-plugin
https://blog.csdn.net/wangshuminjava/article/details/105982432

## template_mapping
https://www.elastic.co/guide/en/elasticsearch/reference/6.3/indices-templates.html
https://www.elastic.co/guide/en/elasticsearch/reference/7.8/mapping-params.html

```
get /_template/
put /_template/hot
{
}

delete /_template/hot
```

## 字段类型Field type详解
https://www.elastic.co/guide/en/elasticsearch/reference/7.x/mapping-types.html


>其中string类型 ELasticsearch 5.X之后的字段类型不再支持string 由text或keyword取代。 如果仍使用string，会给出警告。

[link](https://www.cnblogs.com/gscq073240/articles/9533629.html)

https://blog.csdn.net/limingcai168/article/details/85780964

```
字段类型概述
一级分类	二级分类	具体类型
核心类型	字符串类型	text,keyword
整数类型	integer,long,short,byte
浮点类型	double,float,half_float,scaled_float
逻辑类型	boolean
日期类型	date
范围类型	range
二进制类型	binary
复合类型	数组类型	array
对象类型	object
嵌套类型	nested
地理类型	地理坐标类型	geo_point
地理地图	geo_shape
特殊类型	IP类型	ip
范围类型	completion
令牌计数类型	token_count
附件类型	attachment
```
[字段类型介绍](https://segmentfault.com/a/1190000016686631)


## restapi
[link](https://blog.csdn.net/apple9005/article/details/88874195)

get /ui_click  //check index info

get /ui_click/_mapping
get /ui_click/_settings
get /ui_click/_count

```
put /ui_click_bang_test
{
    "mappings" : {
      "properties" : {
        "accountId" : {
          "type" : "keyword"
        },
        "evtName" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        }
      }
    }
}
```

```
GET /xxx/yyy/_search
{
  "query": {
    "match_all": {}
  }
}

get /hot-exp_log*/_count  //支持通配

get /hot-exp_log*/_search?pretty
{
  "query": {
    "match_all": {}
  }
}

#delete index only data
POST index_name/type_name/_delete_by_query
{
  "query": {"match_all": {}}
}

curl -u用户名:密码 -XPOST '192.168.1.123:9200/index_name/type_name/_delete_by_query?refresh&slices=5&pretty' -H 'Content-Type: application/json' 
-d'{
  "query": {
    "match_all": {}
  }
}'

```

# 运维操作
## 删除历史索引
https://blog.csdn.net/wangshuminjava/article/details/106430552

## es 冷热数据迁移
https://blog.csdn.net/wangshuminjava/article/details/107150733

## es setting
https://blog.csdn.net/wangshuminjava/article/details/106855142

# es数据备份
https://www.jianshu.com/p/b57ad97598de

# blogs
[从ELK到Elastic Stack](https://blog.csdn.net/weixin_44601635/article/details/103118163)
[Elasticsearch分析与检索功能实操](https://blog.csdn.net/weixin_44601635/article/details/103118195)
[Logstash输入与输出](https://blog.csdn.net/weixin_44601635/article/details/103118445)
[]()
>"Elastic Stack（ELK）宝典"

https://blog.csdn.net/sinat_30603081/article/details/90694634
https://blog.csdn.net/sinat_30603081/article/details/90694746
https://blog.csdn.net/sinat_30603081/article/details/90694670

# 数据迁移
https://cloud.tencent.com/developer/article/1145944

https://www.wenyuanblog.com/blogs/elasticsearch-backup-and-migration.html


##  reindex
https://discuss.elastic.co/t/dec-8th-2017-cn-elasticsearch-reindex-api-6-x/110257/2

[reindex全介绍](https://blog.csdn.net/ctwy291314/article/details/82734667)
https://blog.csdn.net/haohaodaren/article/details/102841580
https://www.cnblogs.com/DonneZhang/p/es.html

[reindex_heap](https://www.jianshu.com/p/06a426454889)

```
##step1: add config
在elasticsearch.yml配置文件中添加白名单，这里的白名单表示允许远程指定ip上的es访问我的es
在elasticsearch.yml文件中添加：reindex.remote.whitelist: [“ip:9200”,”ip2:9200”]
注意：1、多个ip地址时用逗号间隔 2、在源es与目标es上都需要进行配置


POST _reindex
{
  "source": {
    "remote": {
      "host": "http://10.10.10.102:9200", //es ip/port
      "socket_timeout": "30s",  //read timeout
      "connect_timeout": "30s" //connect timeout
    },
    "index": "voice2017-11",  //src index
    "size": 1000,  //bulk size
    "query": {}  //filter conditation
  },
  "dest": {
    "index": "voice2017-11"  //dest index
  }
}

get /voice2017-11/_count   //view  count
```

```
POST /_reindex?slices=auto&wait_for_completion=false
{
  "source": {
    "index": "battle_finish",
    "size": 500
  },
  "dest": {
    "index": "battle_finish"
  }
}

get /_tasks/xxxx    //check reindex process

POST /_reindex
{
  "source": {
    "remote": {
      "host": "http://xxx:9200",
      "socket_timeout": "60m",
      "connect_timeout": "300s"
    },
    "index": "battle_finish",
    "size": 500
  },
  "dest": {
    "index": "battle_finish"
  }
}


POST <https://endpoint of destination Elasticsearch>/_reindex 
{
      "source": {
        "remote": {
          "host": "https://endpoint-of-source-elasticsearch-cluster-1.es.amazonaws.com"
        },
        "index": "source-index-name"
      },
      "dest": {
        "index": "destination-index-name"
      }
}
```
##提升reindex效率
https://blog.csdn.net/laoyang360/article/details/81589459
https://www.cnblogs.com/sanduzxcvbnm/p/12881954.html
[link](http://itindex.net/detail/58624-%E5%B9%B2%E8%B4%A7-elasticsearch-reindex)


## es数据迁移到aws es
>aws es是https的vpc地址
[link](https://docs.aws.amazon.com/zh_cn/elasticsearch-service/latest/developerguide/es-managedomains-logstash.html)

[logstash](https://www.elastic.co/guide/en/logstash/7.1/index.html)

01、采用logstash管道实现数据的在线迁移  
#es的账户认证
```
input {
  beats {
    port => 5044
  }
}

output {
  elasticsearch {
    hosts => ["https://domain-endpoint:443"]
    ssl => true
    index => "%{[@metadata][beat]}-%{[@metadata][version]}-%{+YYYY.MM.dd}"
    user => "some-user"
    password => "some-user-password"
    ilm_enabled => false
  }
}
```

02、IAM认证数据迁移
https://github.com/awslabs/logstash-output-amazon_es

bin/logstash-plugin install logstash-output-amazon_es

export AWS_ACCESS_KEY_ID="your-access-key"
export AWS_SECRET_ACCESS_KEY="your-secret-key"
export AWS_SESSION_TOKEN="your-session-token"

```
input {
  s3 {
    bucket => "my-s3-bucket"
    region => "us-east-1"
  }
}

output {
  amazon_es {
    hosts => ["domain-endpoint"]
    ssl => true
    region => "us-east-1"
    index => "production-logs-%{+YYYY.MM.dd}"
  }
}
```

# mysql-es
```
./bin/logstash-plugin install logstash-input-jdbc

tar -zxvf mysql-connector-java-8.0.16.tar.gz 
#mysql.conf

input {
  jdbc {
    jdbc_driver_library => "/home/elk/mysql-connector-java-8.0.16.jar"
    jdbc_driver_class => "com.mysql.jdbc.Driver"
    jdbc_connection_string => "jdbc:mysql://192.168.3.92:3306/plus"
    jdbc_default_timezone => "UTC"
    jdbc_user => "root"
    jdbc_password => "111111"
    schedule => "* * * * *"
    use_column_value => true
    statement => "SELECT * from tablename where updated_at>=:sql_last_value"
    tracking_column => "updated_at"
    tracking_column_type => "timestamp"
    jdbc_fetch_size => 10
    type => "sns_feeds"
  }
}
filter {
    mutate {
        remove_field => [ "@timestamp" ]
    }
}
output{
    stdout { }  
    elasticsearch {
        hosts => "https://aws_domain:443"
        index => "%{type}"
        document_id => "%{id}"
        ssl => true
    }
}
```

#es告警
https://blog.csdn.net/mayifan0/article/details/78023783

# open-distro
open distro for elasticsearch

```
docker pull amazon/opendistro-for-elasticsearch-kibana:1.1.0
amazon/opendistro-for-elasticsearch-kibana:1.1.0  -->es 7.1.1

https://opendistro.github.io/for-elasticsearch/
https://opendistro.github.io/for-elasticsearch-docs/
https://hub.docker.com/r/amazon/opendistro-for-elasticsearch-kibana

https://github.com/opendistro-for-elasticsearch/opendistro-build/blob/opendistro-1.1/kibana/docker/build/kibana/bin/kibana-docker

kibana.yml
server.name: kibana
server.host: "0.0.0.0"
elasticsearch.hosts: https://localhost:9200
elasticsearch.ssl.verificationMode: none
elasticsearch.username: kibanaserver
elasticsearch.password: kibanaserver
elasticsearch.requestHeadersWhitelist: ["securitytenant","Authorization"]

securitytenant 

opendistro_security.multitenancy.enabled: true
opendistro_security.multitenancy.tenants.preferred: ["Private", "Global"]
opendistro_security.readonly_mode.roles: ["kibana_read_only"]

es
opendistro_security.restapi.roles_enabled: ["all_access", "security_rest_api_access"]

```

##kibana
```
# eg. Setting the environment variable:
#
#       ELASTICSEARCH_STARTUPTIMEOUT=60
#
# will cause Kibana to be invoked with:
#
#       --elasticsearch.startupTimeout=60

docker.elastic.co/kibana/kibana:7.1.1
docker.elastic.co/kibana/kibana-oss:7.1.1

console.enabled
server.host
kibana.index: ".kibana_1"
elasticsearch.hosts
elasticsearch.ssl.verificationMode: node
elasticsearch.username
elasticsearch.password

http://localhost:5601/status 
```

## elastic数据迁移
[aliyun 迁移方案](https://github.com/awslabs/logstash-output-amazon_es)
https://www.jianshu.com/p/50ef4c9090f0

//aws
https://github.com/awslabs/logstash-output-amazon_es


## elastic turn
https://www.jianshu.com/p/41c31ce2ac98
[refresh](https://www.cnblogs.com/shaosks/p/7561524.html)