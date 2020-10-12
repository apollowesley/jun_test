# elk-docker
https://www.docker.elastic.co/
https://github.com/elastic/dockerfiles    //dockerfile

docker pull docker.elastic.co/elasticsearch/elasticsearch:7.7.1

```
https://hub.docker.com/_/kibana
https://hub.docker.com/_/elasticsearch
https://hub.docker.com/_/logstash
```

https://blog.csdn.net/sinat_30603081/article/details/90694660


### opt-evn
- docker-ce 18.06.3
- centos7  2c4g

### docker-hub elk images
docker  pull elasticsearch
docker  pull kibana
docker  pull logstash  







### es-network 
docker network create esnetwork --driver=bridge --subnet "10.10.0.0/16"
docker run -d -p 9200:9200 -p 9300:9300 --name myelastic --network esnetwork elasticsearch
docker run -d -p 5601:5601 --network esnetwork --name mykibana -e ELASTICSEARCH_URL=http://myelastic:9200  kibana

# es-link
docker  pull elasticsearch:7.1.1
docker  pull kibana:7.1.1
docker  pull filebeat:7.1.1

docker run -d --name elasticsearch  -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "ES_JAVA_OPTS=-Xms256m -Xmx256m" elasticsearch:7.1.1
docker run -d -p 5601:5601 --name kibana  --link elasticsearch:elasticsearch -e ELASTICSEARCH_HOSTS=http://elasticsearch:9200  kibana:7.1.1


FROM kibana:7.1.1
RUN kibana-plugin install https://d3g5vo6xdbdb9a.cloudfront.net/downloads/elasticsearch-plugins/opendistro-alerting/opendistro_alerting-1.1.0.0.zip


https://discuss.opendistrocommunity.dev/


### logstash-test
docker run -it --name mylogstash --network esnetwork -d  logstash -e 'input { stdin { } } output { elasticsearch { hosts=>[ "myelastic:9200" ] } stdout { } }'

//终端测试
docker run -it --name mylogstash --network esnetwork  logstash -e 'input { stdin { } } output { elasticsearch { hosts=>[ "myelastic:9200" ] } stdout { } }'

docker run -it --network esnetwork --rm -v /home/elk/config-dir:/etc/conf.d -v /tmp:/tmp logstash -f /etc/conf.d/logstash.conf

http://192.168.3.103:5601/app/kibana



docker pull docker.elastic.co/kibana/kibana-oss:7.5.0
https://www.docker.elastic.co/r/kibana/kibana


https://github.com/elastic/dockerfiles/blob/7.5/kibana/bin/kibana-docker

```
# Run Kibana, using environment variables to set longopts defining Kibana's
# configuration.
#
# eg. Setting the environment variable:
#
#       ELASTICSEARCH_STARTUPTIMEOUT=60
#
# will cause Kibana to be invoked with:
#
#       --elasticsearch.startupTimeout=60
```

elasticsearch.hosts
elasticsearch.ssl.verificationMode: node
elasticsearch.username
elasticsearch.password

https://github.com/elastic/dockerfiles/blob/7.5/kibana/bin/kibana-docker


https://github.com/opendistro-for-elasticsearch/alerting-kibana-plugin
```
#docker.elastic.co/kibana/kibana-oss:7.1.1

SERVER_HOST	0.0.0.0
KIBANA_INDEX	.kibana_oss
ELASTICSEARCH_USERNAME	elastic
ELASTICSEARCH_PASSWORD	Ing3423!_e232
ELASTICSEARCH_HOSTS	https://vpc-vpc-general-es-east1-y3igsj7ryx3kekny2rwb3tfffu.us-east-1.es.amazonaws.com:443
CONSOLE_ENABLED	false
```


https://opendistro.github.io/for-elasticsearch/