# kettlePlugins
kettle通用插件，通过json配置文件实现自定义插件的开发，配置中所有内容都支持变量形式。

# 开发步骤
## 下载本插件源码
通过git方式下载或者直接下载zip文件。
## 环境准备
### 安装maven、jdk8
略……
### 安装kettle的jar包到本地maven仓库
1、首先下载kettle6或者7的发行包  
2、使用如下命令安装jar（定位到kettle/lib下或者修改脚本中jar包的路径，或者将以下jar拷贝到自定义的目录中后执行，其中,swt-6.1.0.1-196.jar是libswt目录下相应Windows平台文件中的swt.jar，为了统一需要拷贝重命名一下，下面以6.1.0.1-196版本为例）：
```
mvn install:install-file -Dfile=./kettle-core-6.1.0.1-196.jar -DgroupId=org.pentaho.di -DartifactId=kettle-core -Dversion=6.1.0.1-196 -Dpackaging=jar
mvn install:install-file -Dfile=./kettle-dbdialog-6.1.0.1-196.jar -DgroupId=org.pentaho.di -DartifactId=kettle-dbdialog -Dversion=6.1.0.1-196 -Dpackaging=jar
mvn install:install-file -Dfile=./kettle-engine-6.1.0.1-196.jar -DgroupId=org.pentaho.di -DartifactId=kettle-engine -Dversion=6.1.0.1-196 -Dpackaging=jar
mvn install:install-file -Dfile=./kettle-ui-swt-6.1.0.1-196.jar -DgroupId=org.pentaho.di -DartifactId=kettle-ui-swt -Dversion=6.1.0.1-196 -Dpackaging=jar
mvn install:install-file -Dfile=./pentaho-metadata-6.1.0.1-196.jar -DgroupId=org.pentaho.di -DartifactId=pentaho-metadata -Dversion=6.1.0.1-196 -Dpackaging=jar
mvn install:install-file -Dfile=./metastore-6.1.0.1-196.jar -DgroupId=org.pentaho.di -DartifactId=metastore -Dversion=6.1.0.1-196 -Dpackaging=jar
mvn install:install-file -Dfile=./swt-6.1.0.1-196.jar -DgroupId=org.pentaho.di -DartifactId=swt -Dversion=6.1.0.1-196 -Dpackaging=jar
```

## 开发流程
1、使用Eclipse或者IDEA引入项目  
2、修改pom中kettle版本<kettle.version>号以及kettle安装目录<kettle.home>  
3、可以直接编译（mvn clean package）后，打开kettle检查是否成功：在转换的插件类别CommonPlugin下  

## 自定义插件开发
1、首先创建一个类继承nivalsoul.kettle.plugins.common.CommonStepRunBase  
2、重写disposeRow()方法，用于处理每一行数据，该部分可以参考已有插件的实现方式  
3、如果需要，可以重写init()和end()方法做一些初始化和清理操作  
4、编译打包插件，确保插件已放入kettle的plugins文件夹下  
 - 其中用到的poi依赖版本为3.17，httpmine为4.5.1，如果需要使用自定义输入的excel/rest输入，则需要放到kettle/lib下
 - 另外还用到了fastjson和guava，如果原来没有也需要放到kettle/lib下  

5、重启kettle，在转换的插件类别CommonPlugin下拖拽插件到转换图流程图中，配置说明如下：  
 - 插件类型直接选择“自定义”（如果想指定为特有名称，可以在nivalsoul.kettle.plugins.common.PluginType枚举中增加相应的名称即可）
 - 自定义类名填写上述自己创建的类全路径
 - 配置部分格式为json，内容为在自定义插件中所使用到的配置字段信息（如果在插件类型中新增了自己的特有插件名称，同时也可以在resource下新增与插件枚举名相同的配置json文件，这样便可以在选择该插件类别的时候自动加载默认配置）

# 已有插件的使用
1. 首先将插件已放入kettle的plugins/CommonPlugin(名字可以自定)文件夹下  
2. 根据需要添加相应的jar包kettle/lib下
3. 重启kettle
4. 在转换中添加插件（在左侧插件列表CommonPlugin下）
## 自定义输入类型
目前已实现从rest接口读取数据到字段、通过sax方式解析excel到多个字段。  
1）rest输入默认配置如下：
``` 
{
    "inputType":"rest",
	"url":"",
	"method":"get",
	"params": {},
	"resultField":"result"
}
```
其中，

* method为post的时候，参数params才有效。
* resultField表示结果字段名称。

2）excel输入的默认配置如下：
``` 
{
    "inputType":"excel",
	"filename":"",
	"header":true,
	"outputFields":[
	    {"name":"col1", "type": "String"},
	    {"name":"col2", "type": "String"},
	    {"name":"col3", "type": "String"}
	]
}
```
其中，

* filename 为excel文件名，可以使".xls"或者".xlsx"格式，支持kettle命名参数或者变量形式：${varFileName}。
* header为true表示有表头，为false表示没有表头。
* outputFields是指定的输出字段列表，可以跟表头字段不一致，如果想使用excel的表头字段作为输出字段，那么可以将header设置true，同时将outputFields设置为[]或者删掉。


## 输出到json文件
支持将数据输出到json对象或者json数组。默认配置如下：  
``` 
{
    "outputType":"array",
    "fileName":"your-json-filename.json",
    "fieldName":"data",
    "batchSize": 1000
}
```
其中，

* outputType可选“object”和“array”，分别表示输出为json对象和数组。
* fieldName是输出为json对象所指定的名称，所有数据流以数组的形式作为value(形如{"data": []})，当outputType为object时有效。
* batchSize表示多少行数据写一次文件。
 
## 输出到hive表
支持输出到hive的textfile/orc/parquet三种类型表，也可以只输出到对应的三种格式hdfs文件，不创建hive表。默认配置如下： 
``` 
{
	"hadoopUserName":"hive",
	"hdfsUrls":"192.168.100.11:8020;192.168.100.12:8020",
	"hdfsFileName":"/tmp/hiveload/aaa.orc",
	"hiveDriver":"org.apache.hive.jdbc.HiveDriver",
	"hiveUrl":"jdbc:hive2://192.168.100.11:10000/default",
	"hiveUser":"hive",
	"hivePassword":"hive",
	"createTable":"true",
	"tableType":"orc",
	"hiveTable":"xuwl_orc",
	"partitionField":[
        {"name":"dt", "type":"string"}
    ],
    "partitionValue":[
        {"name":"dt", "value":"2019-12-04"}
    ],
	"overwrite":"true",
	"fieldSeparator":"\t",
	"lineSeparator":"\n"
}
```
其中，
* hadoopUserName为指定的hdfs文件属主，为了hive执行load命令能够有权限
* hdfsUrls为HDFS的namenode节点，如果配置了HA，则把相应的节点都配上，以分号隔开
* hdfsFileName为存放hdfs文件名称
* hiveDriver、hiveUrl、hiveUser、hivePassword为hiveServer2的jdbc连接信息
* createTable表示是否创建hive表，为字符串格式的"true"或者"false"
* tableType可选[text/orc/parquet]三者之一
* hiveTable为最后的hive表名
* partitionField为分区字段列表，多个分区字段按先后顺序指定，类型名称为hive数据类型
* partitionValue为分区字段的值，和分区字段顺序一致，value指定具体值
* overwrite表示是否覆盖原表，为字符串格式的"true"或者"false"
* fieldSeparator为列分隔符，lineSeparator为行分隔符，只在tableType为text时有效  
##### 注意：
该插件未包含hadoop和hive相关的jar包，如果之前没有添加可能报错，需根据自己集群版本添加相应的jar包到kettle/lib下，主要是hadoop-auth-2.6.0.jar、hadoop-common-2.6.0.jar、hadoop-hdfs-2.6.0.jar、hive-exec-2.1.0.jar、hive-jdbc-1.1.0-cdh5.10.0.jar、protobuf-java-2.5.0.jar、htrace-core4-4.0.1-incubating.jar、fastjson-1.2.7.jar、servlet-api-2.5.jar等，如果还差看具体报错情况添加。
以上jar可以在kettle自带的bigdata插件里面或者大数据集群安装包里面找到，这里有一份：[https://yun.baidu.com/s/1zxnUL48ZiWWUnWLfknAx4g](https://yun.baidu.com/s/1zxnUL48ZiWWUnWLfknAx4g) 密码:hetg

## 读取HDFS文件
支持从HDFS读取单个text文件。默认配置如下：
```
{
	"hdfsUrls":"10.6.1.19:8020;10.6.1.20:8020",
	"fileName":"/tmp/aa.txt",
	"fileType":"text",
	"fieldSeparator":"\t",
	"lineSeparator":"\n",
	"outputFields":[
		{"name":"id", "type":"Integer"},
		{"name":"xm", "type": "String"},
		{"name":"age", "type": "String"},
		{"name":"title", "type": "String"}
	]
}
```
其中，
* hdfsUrls为HDFS的namenode节点，如果配置了HA，则把相应的节点都配上，以分号隔开
* fileName为HDFS文件名，目前只支持文件
* fieldSeparator为列分隔符，lineSeparator为行分隔符
* outputFields是指定的输出字段列表
##### 注意：
该插件也需要hadoop相关的jar包，同前面的[输出到hive表]。

## 数据转换相关
支持中文简繁体转换，汉字转拼音，base64编码解码，MD5，sha256加密等。配置如下：
```
{
    "changeConfig": [
        {"inputField": "field1", "changeType": "sc2tc", "outputField": "field2"},
        {"inputField": "field3", "changeType": "hz2py", "outputField": "field4"}
    ]
}
```
其中，inputField为需要转换的字段，outputField为转换后的新字段名，可以与inputField相同。
changeType为转换类型，含义如下：
- sc2tc(简体转繁体)
- tc2sc(繁体转简体)
- hz2py(汉字转拼音)
- base64Encode(base64加密)
- base64Decode(base64解密)
- MD5(MD5加密)
- SHA256(SHA256加密)


# 其他
如果大家在开发中遇到什么问题，可以加QQ群（195548102）咨询。

