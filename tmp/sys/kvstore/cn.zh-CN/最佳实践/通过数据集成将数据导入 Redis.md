# 通过数据集成将数据导入 Redis {#concept_qcb_wcw_wdb .concept}

## 数据集成简介 {#section_itg_zcw_wdb .section}

[数据集成](https://www.aliyun.com/product/cdp/)（Data Integration）是阿里集团对外提供的可跨异构数据存储系统的、可靠、安全、低成本、可弹性扩展的数据同步平台，为20多种数据源提供不同网络环境下的离线\(全量/增量\)数据进出通道。详细的数据源类型列表请参考[支持的数据源类型](https://help.aliyun.com/document_detail/53008.html)。您可以通过数据集成向云数据库 Redis 版进行数据的导入数据。

## 一、创建 Redis 数据源 {#section_jtg_zcw_wdb .section}

Redis 数据源支持写入 Redis 的通道，可以通过脚本模式配置同步任务 。

**说明：** 

-   只有项目管理员角色才能够新建数据源，其他角色的成员仅能查看数据源。

-   如您想用子账号创建数据集成任务，需赋予子账号相应的权限。具体请参考：[开通阿里云主账号](https://help.aliyun.com/document_detail/56141.html)[设置子账号](https://help.aliyun.com/document_detail/56143.html)。


**操作步骤**

1.  以开发者身份进入[阿里云数加平台](https://workbench.data.aliyun.com/console)，单击项目列表下对应**项目操作**栏中的**进入工作区**。

1.  单击顶部菜单栏中**数据集成**模块的**数据源**。
2.  单击**新增数据源**。
3.  在新建数据源对话框中，选择数据源类型为 **Redis**。
4.  配置 Redis 数据源的各个信息项，如下图所示。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3165/3143_zh-CN.png)

    **说明：** 

    若账号没有授权数据集成默认角色，需要前往 RAM 进行角色授权。

    配置项具体说明如下：

    -   **数据源名称**：由英文字母、数字、下划线组成且需以字符或下划线开头，长度不超过60个字符。

    -   **数据源描述**：对数据源进行简单描述，不得超过80个字符。

    -   **数据源类型**：当前选择的数据源类型为 Redis：有公网IP的自建数据库。

    -   **服务地址**：格式为 `host:port`。

    -   **添加访问地址**：添加访问地址，格式为 `host:port`。

    -   **密码**：数据库对应的密码 。

5.  完成上述信息项的配置后，单击**测试连通性**。
6.  测试连通性通过后，单击**确定**。

**二、配置脚本模式的同步任务**

1.  以项目管理员身份进入[数加管理控制台](https://workbench.data.aliyun.com/console)，单击大数据开发套件下对应项目操作栏中的**进入工作区**。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3165/3145_zh-CN.png)

2.  进入顶部菜单栏中的数据集成页面，选择**脚本模式**，如下图。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3165/3146_zh-CN.png)

    **说明：** 

    Redis 不支持向导模式。进入脚本界面你可以选择相应的模板，此模板包含了同步任务的主要参数，将相关的信息填写完整，但是脚本模式不能转化成向导模式。

3.  在导入模板对话框中选择需要的**来源类型**和**目标类型**，并单击**确认**。如下图所示：

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3165/3147_zh-CN.png)

4.  在脚本模式配置页面，根据自身情况进行配置，如有问题可单击右上方的 **Redis Writer 帮助手册**进行查看。如下图所示：

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3165/3149_zh-CN.png)

    **说明**：RedisWriter 脚本案例如下：

    ```
    {
       "type": "job",
       "configuration": {
         "setting": {
           "speed": {
             "concurrent": "1",//并发数
             "mbps": "1"//同步能达到的最大数率
           },
           "errorLimit": {
             "record": "0"
           }
         },
         "reader": {
           "parameter": {
             "splitPk": "id",//切分键
             "column": [
               "id",
               "name",
               "year"
             ],
             "table": "person",//表名
             "where": "",//
             "datasource": "px_mysql"//数据源名，建议数据源都先添加数据源后再配置同步任务,此配置项填写的内容必须要与添加的数据源名称保持一致
           },
           "plugin": "mysql"
         },
         "writer": {
           "parameter": {
             "expireTime": {
               "seconds": "1000"//相对当前时间的秒数，该时间指定了从现在开始多长时间后数据失效
             },
             "keyFieldDelimiter": "\u0001",//写入 redis 的 key 分隔符。比如: key=key1\u0001id,如果 key 有多个需要拼接时，该值为必填项，如果 key 只有一个则可以忽略该配置项。
             "writeMode": {
               "valueFieldDelimiter": "\u0001",//value 类型是 string 时，value 之间的分隔符，比如 value1\u0001value2\u0001value3；
               "type": "string",//value类型
               "mode": "set"//写入的模式,存储这个数据，如果已经存在则覆盖
             },
             "batchSize": "1000",//一次性批量提交的记录数大小
             "dateFormat": "yyyy-MM-dd HH:mm:ss",//时间格式
             "keyIndexes": [
               0,
               1
             ],//keyIndexes 表示源端哪几列需要作为 key（第一列是从 0 开始），如果是第一列和第二列需要组合作为 key，那么 keyIndexes 的值则为 [0,1]。
             "datasource": "px_redis_datasource"//数据源名，建议数据源都先添加数据源后再配置同步任务,此配置项填写的内容必须要与添加的数据源名称保持一致
           },
           "plugin": "redis"
         }
       },
       "version": "1.0"
     }
    ```

    运行结果如下：

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3165/3151_zh-CN.png)


-   RedisWriter 参数说明请参考 [RedisWriter 配置](https://help.aliyun.com/document_detail/50349.html)。

