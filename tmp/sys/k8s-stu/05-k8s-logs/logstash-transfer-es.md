# 需求说明
```
aws上es同一个az不同vpc，通过对等vpc实现互联互通(开启dns)。把es数据从一个vpc迁移到另外一个vpc,使用logstash管道的形式
```

# 环境描述
- elasticsearch-oss 7.1.1  //aws es服务
- logstash 7.1.0 //elastic

#logstash   
https://www.elastic.co/cn/downloads/past-releases/  
https://www.elastic.co/cn/downloads/past-releases/logstash-7-1-0
https://artifacts.elastic.co/downloads/logstash/logstash-7.1.1.tar.gz

https://artifacts.elastic.co/downloads/logstash/logstash-oss-7.1.1.tar.gz

https://www.elastic.co/guide/en/logstash/7.1/index.html
https://www.elastic.co/guide/en/logstash/7.1/input-plugins.html

#logstash commonly used
```
[root@ip-192-168-83-195 logstash-7.1.1]# ./bin/logstash --help

    -f, --path.config CONFIG_PATH Load the logstash config from a specific file

                                   (default: "main")
    -w, --pipeline.workers COUNT  Sets the number of pipeline workers to run.
                                   (default: 4)
    --java-execution              Use Java execution engine.
                                   (default: true)
    -b, --pipeline.batch.size SIZE Size of batches the pipeline is to work in.
                                   (default: 125)
    -u, --pipeline.batch.delay DELAY_IN_MS When creating pipeline batches, how long to wait while polling
                                  for the next event.
                                  then exit.
    -t, --config.test_and_exit    Check configuration for valid syntax and then exit.
    ....
```

# 解决问题
[aws官方logstash事例](https://docs.aws.amazon.com/zh_cn/elasticsearch-service/latest/developerguide/es-managedomains-logstash.html)

https://docs.aws.amazon.com/zh_cn/elasticsearch-service/latest/developerguide/es-managedomains-logstash.html

```
input {
    stdin {} 
}
output {
	stdout {codec => rubydebug}
	elasticsearch {
		hosts => ["https://172.31.28.36:9200"]
		cacert => "/etc/kibana/ca.crt"
	}
}

#input 
input {
    stdin {} 
}
output {
	stdout {codec => rubydebug}
	elasticsearch {
		hosts => ["https://172.31.28.36:9200"]
		cacert => "/etc/kibana/ca.crt"
	}
}

input {
	stdin {} 
	elasticsearch {
	    hosts => "https://172.31.28.36:9200"
    	index => "twitter1"
    	ssl => "true"
    	ca_file => "/etc/kibana/ca.crt"
  	}
}
output {
	stdout {codec => rubydebug}
	elasticsearch {
		hosts => ["https://172.31.28.36:9200"]
		cacert => "/etc/kibana/ca.crt"
	}
}

```



#logstash一个一个索引迁移

#查看索引
```
curl -u elastic:'password'  https://xxx.es.amazonaws.com/_cat/indices?h=index
```

```
#es.conf.tmpl   //var INDEX
input {
    elasticsearch {
    hosts => ["x.es.amazonaws.com:443"]
    ssl =>true
    user =>"xxx"
    password =>"xxx"
    index => "${INDEX}"
    size =>5000
    scroll =>"50m"
    docinfo => true
  }
}

filter {
}

output {
  elasticsearch {
    hosts => ["y.es.amazonaws.com:443"]
    ssl => true
    user => "yyy"
    password => "yyy"
    pool_max => 5000
    pool_max_per_route =>500
    index => "%{[@metadata][_index]}"
    document_type => "%{[@metadata][_type]}"
    document_id => "%{[@metadata][_id]}"
    ilm_enabled => false
  }
}


input {
    elasticsearch {
    hosts => ["xxx-1.es.amazonaws.com:443"]
    ssl =>true
    user =>"elastic"
    password =>"xxx"
    index => "xxxx"
    size =>5000
    scroll =>"50m"
    docinfo => true
  }
}

filter {
}

output {
  elasticsearch {
    hosts => ["xxx.es.amazonaws.com:443"]
    ssl => true
    user => "elastic"
    password => "xxxx"
    pool_max => 5000
    pool_max_per_route =>500
    index => "xxx"
    ilm_enabled => false
    document_type => "%{[@metadata][_type]}"
    document_id => "%{[@metadata][_id]}"
  }
}
```

```
export INDEX="index-xxx" && envsubst < ./es.conf.tmpl >es.conf
./bin/logstash -f es.conf  -w 50 -b 5000 -u 120
```

#http-->https es
```
#es.conf.tmpl 
input {
    elasticsearch {
    hosts => ["http://xxx:9200"]
    index => "${INDEX}"
    docinfo => true
  }
}

filter {
}
output {
  elasticsearch {
    hosts => ["https://xxxx-1.es.amazonaws.com:443"]
    ssl => true
    user => "xxxx"
    password => "xxxx"
    index => "%{[@metadata][_index]}"
    document_type => "%{[@metadata][_type]}"
    document_id => "%{[@metadata][_id]}"
    ilm_enabled => false
  }
}
````

```
export INDEX="health" && envsubst < ./es.conf.tmpl >es.conf
./bin/logstash -f es.conf  -w 50 -b 5000 -u 120
```


## reindex
https://www.elastic.co/guide/en/elasticsearch/reference/5.6/docs-reindex.html#docs-reindex
[link](https://blog.csdn.net/ctwy291314/article/details/82734667)
#elasticsearch.yaml
```
reindex.remote.whitelist: 'es-xxx.xxx.com:9200 
```
>01、add all es nodes  
>02、restart es nodes


```
POST /_reindex
{
	"source": {
		"remote": {
			"host": "https://es-xxx.xxx.com:9200",
			"username": "admin",
			"password": "ssdsdwD"
		},
		"index": "reind_v4"

	},
	"dest": {
		"index": "reind_v4"
	}
}



#one es todo
POST _reindex
{
  "source": {
    "index": "hot_herog-chat-2020-07-08"
  },
  "dest": {
    "index": "bang_test"
  }
}


```

```
#es修改索引字段类型
01、new index mapping //modify filed type
02、reindex
03、delete index 
04、reindex

/xxx/_count
get /xxx/_mapping
put /xxx/_mapping
```
[es修改字段](http://www.manongjc.com/detail/15-fmkqgjkagxohnza.html)
[es修改字段2](https://blog.csdn.net/yonggeit/article/details/87821689)
https://www.elastic.co/guide/en/elasticsearch/reference/6.0/removal-of-types.html
https://www.elastic.co/guide/en/elasticsearch/reference/6.0/reindex-upgrade-remote.html
https://blog.csdn.net/Peter_S/article/details/90377027

# troubleshooting
https://discuss.elastic.co/    //elastic官方论坛，大部分问题都是可以在上面搜到并解决

[排除法](https://discuss.elastic.co/t/logstash-is-unable-to-send-data-when-elastic-is-configured-with-ssl-in-7-1/183383/3)

[问题总结及解决](https://discuss.elastic.co/t/error-initialize-name-or-service-not-known/141357/4)
```
LogStash::Inputs::Elasticsearch hosts=>["{logstash_host}"], index=>"test_index*"...

I think the value inside the hosts array is not a valid host name.

Do you have a DNS entry for '{logstash_host}'?

Do you have a etc/hosts entry for '{logstash_host}'?

Use the IP or host name of the machine that Elasticsearch is running on.
```


