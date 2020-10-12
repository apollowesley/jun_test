# kibana

```
Discover
　　从发现页可以交互地探索ES的数据。可以访问与所选索引模式相匹配的每一个索引中的每一个文档。您可以提交搜索查询、筛选搜索结果和查看文档数据。还可以看到匹配搜索查询和获取字段值统计的文档的数量。如果一个时间字段被配置为所选择的索引模式，则文档的分布随着时间的推移显示在页面顶部的直方图中。

Visualize
　　可视化能使你创造你的Elasticsearch指标数据的可视化。然后你可以建立仪表板显示相关的可视化。Kibana的可视化是基于Elasticsearch查询。通过一系列的Elasticsearch聚合提取和处理您的数据，您可以创建图表显示你需要知道的关于趋势，峰值和骤降。您可以从搜索保存的搜索中创建可视化或从一个新的搜索查询开始。

Dashboard
　　一个仪表板显示Kibana保存的一系列可视化。你可以根据需要安排和调整可视化，并保存仪表盘，可以被加载和共享。

Monitoring
　　从图中可以发现，默认Kibana是没有该选项的。其实，Monitoring是由X-Pack集成提供的。

　　该X-pack监控组件使您可以通过Kibana轻松地监控ElasticSearch。您可以实时查看集群的健康和性能，以及分析过去的集群、索引和节点度量。此外，您可以监视Kibana本身性能。当你安装X-pack在群集上，监控代理运行在每个节点上收集和指数指标从Elasticsearch。安装在X-pack在Kibana上，您可以查看通过一套专门的仪表板监控数据。

Graph
　　X-Pack图的能力使你发现一个Elasticsearch索引项是如何相关联的。你可以探索索引条款之间的连接，看看哪些连接是最有意义的。从欺诈检测到推荐引擎，对各种应用中这都是有用的，例如，图的探索可以帮助你发现网站上黑客的目标的漏洞，所以你可以硬化你的网站。或者，您可以为您的电子商务客户提供基于图表的个性化推荐。X-pack提供简单，但功能强大的图形开发API，和Kibana交互式图形可视化工具。使用X-pack图有工作与开销与现有Elasticsearch指标你不需要任何额外的数据存储的特征。



Timelion
　　Timelion是一个时间序列数据的可视化，可以结合在一个单一的可视化完全独立的数据源。它是由一个简单的表达式语言驱动的，你用来检索时间序列数据，进行计算，找出复杂的问题的答案，并可视化的结果。

　　这个功能由一系列的功能函数组成，同样的查询的结果，也可以通过Dashboard显示查看。

Management
　　管理中的应用是在你执行你的运行时配置kibana，包括初始设置和指标进行配置模式，高级设置，调整自己的行为和Kibana，各种“对象”，你可以查看保存在整个Kibana的内容如发现页，可视化和仪表板。
　　这部分是pluginable，除此之外，X-pack可以给Kibana增加额外的管理能力。

　　你可以使用X-pack安全控制哪些用户可以访问Elasticsearch数据通过Kibana。当你安装X-pack，Kibana用户登录。他们需要有kibana_user作用以及获得的指标，他们将在Kibana的工作。如果用户加载Kibana仪表板，访问数据的一个索引，他们未被授权查看，他们得到一个错误，表明指数不存在。X-pack安全目前并不提供一种方法来控制哪些用户可以负荷的仪表板。

Dev Tools
　　原先的交互式控制台Sense，使用户方便的通过浏览器直接与Elasticsearch进行交互。从Kibana 5开始改名并直接内建在Kibana，就是Dev Tools选项。
```
## es+kibana
```
docker pull nshou/elasticsearch-kibana
docker run -d -p 9200:9200 -p 5601:5601 nshou/elasticsearch-kibana
```


## es
```
docker pull docker.elastic.co/elasticsearch/elasticsearch:6.7.1

docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -d -p 9200:9200 -p 9300:9300  -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:6.7.1

```

## kibana
https://www.elastic.co/downloads/kibana


http://ip:5601/status  //kibana status

x-pack  //需要在es/kibana都安装
```
bin/elasticsearch-plugin install x-pack
action.auto_create_index: .security,.monitoring*,.watches,.triggered_watches,.watcher-history*

bin/kibana-plugin install x-pack

##离线安装
bin/elasticsearch-plugin install file:////usr/local/x-pack-5.2.2.zip
bin/kibana-plugin install file:////usr/local/x-pack-5.2.2.zip
```

```
docker pull docker.elastic.co/kibana/kibana:6.7.1

docker run --link 19b445e8d6ef:elasticsearch -p 5601:5601 docker.elastic.co/kibana/kibana:6.7.1
```

## install plugin
```
docker exec -it elasticsearch /bin/bash
cd /usr/share/elasticsearch/plugins/

elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.7.1/elasticsearch-analysis-ik-6.7.1.zip

elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v6.8.1/elasticsearch-analysis-ik-6.8.1.zip

docker restart elasticsearch
```

## es config
```
docker exec -it elasticsearch /bin/bash
cd /usr/share/elasticsearch/config/
vi elasticsearch.yml
#add allow cors
http.cors.enabled: true
http.cors.allow-origin: "*"

docker restart elasticsearch
```

https://blog.csdn.net/bbwangj/article/details/81291425

## alerting
https://opendistro.github.io/for-elasticsearch/features/alerting.html
https://aws.amazon.com/cn/blogs/china/iot-alerting-open-distro-for-elasticsearch/

## kibana历史索引
#问题描述
```
kibana升级之后 原本保存的对象（dashboards, visualizations, index patterns）(丢失)。迁移已运行后，在Elasticsearch会出现多个Kibana索引：.kibana_6, .kibana_5等。Kibana使用的是.kibana（.kibana是其他索引的别名）所指向的索引
```
#解决问题
```
get /_alias   //check .kibana index

get .kibana_x/_search  //check index data
get .kibana_y/_search


post /_aliases
{
  "actions": [
    {
      "remove": {
        "index": ".kibana_1",   //remove .kibana_1 alias 
        "alias": ".kibana"
      }
    }
  ]
}

post /_aliases
{
  "actions": [
    {
      "add": {
        "index": ".kibana_7",
        "alias": ".kibana"
      }
    }
  ]
}
```
>查看当前.kibana索引中的内容，发现原来的数据存在，刷新页面!
