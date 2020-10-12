# CreateCacheAnalysisTask {#doc_api_R-kvstore_CreateCacheAnalysisTask .reference}

调用CreateCacheAnalysisTask手动发起缓存分析任务。

 **前提条件** 

-   Redis实例的引擎版本为4.0或以上；
-   Redis实例的小版本为最新，小版本是否需要升级请参见[如何确认Redis实例的小版本是不是最新版](~~129203~~)。

 **功能说明** 

云数据库Redis版的缓存分析功能在每日全量备份数据时对缓存结构进行分析，找出每个数据节点中占用空间最大的80个key，保存分析结果供您查看。

除了每天的自动分析，您还可以手动发起实时的缓存分析任务，可供选择的发起方法如下：

-   按照本文的说明调用CreateCacheAnalysisTask。
-   在Redis控制台发起实时分析，操作步骤请参见[缓存分析](~~102093~~)。

发起缓存分析任务后，您可以调用[DescribeCacheAnalysisReport](~~128808~~)查看分析结果。

**说明：** 

-   云数据库Redis版只会保存每天最新的一条分析结果，如您先通过手动操作进行了分析，当日稍晚时间系统进行了自动分析，系统会保留系统自动分析的结果，删除之前手动发起分析任务获得的结果。
-   系统自动进行缓存分析的时间由数据全量备份的时间决定，您可以在控制台的[备份与恢复](~~43886~~)页修改该时间。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=CreateCacheAnalysisTask&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|Action|String|否|CreateCacheAnalysisTask|系统规定参数，取值：CreateCacheAnalysisTask。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|BBC1E3D6-7C88-4DF5-9A3D-0DB1E6D9EE19|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=CreateCacheAnalysisTask
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<CreateCacheAnalysisTaskResponse>
      <RequestId>BBC1E3D6-7C88-4DF5-9A3D-0DB1E6D9EE19</RequestId>
</CreateCacheAnalysisTaskResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"BBC1E3D6-7C88-4DF5-9A3D-0DB1E6D9EE19"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

