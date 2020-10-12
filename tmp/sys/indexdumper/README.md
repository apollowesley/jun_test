#介绍

用于给Elasticsearch建立索引，数据源方式支持：Binlog增量、阿里云DTS增量、数据库全量，通过配置就可以支持把数据记录导入ES，建立索引和更新索引，大家可以修改这些代码实现自己的需要


#编译

mvn -P [profile]

profile包括：dev,beta,prod

#运行

bin/startup.sh [increment|full|alincr]

increment : Binlog增量

full : 数据库全量

alincr : 阿里云DTS增量



#依赖

如果采用Binlog增量方式，需要部署阿里巴巴的canal来获取预处理后的数据变更事件


#联系

堂吉诃德
421093703@qq.com
