# RestartInstance {#doc_api_R-kvstore_RestartInstance .reference}

调用RestartInstance重启运行中的Redis实例。

该API对应的控制台操作请参见[重启实例](~~102634~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=RestartInstance&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|RestartInstance|系统规定参数，取值：RestartInstance。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|EffectiveTime|String|否|1|重启时间：

 -   0（立即重启）
-   1（可运维时间段重启）

 **说明：** 默认为0。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|InstanceId|String|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|TaskId|String|111111111|任务ID。

 |
|RequestId|String|EFC9161F-15E3-4A6E-8A99-C33331F464|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=RestartInstance
&InstanceId=r-bp1xxxxxxxxxxxxx
&EffectiveTime=1
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<RestartInstanceResponse>
      <RequestId>EFC9161F-15E3-4A6E-8A99-C33331F464</RequestId>
      <InstanceId>r-bp1xxxxxxxxxxxxx</InstanceId>
      <TaskId>111111111</TaskId>
</RestartInstanceResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"InstanceId":"r-bp1xxxxxxxxxxxxx",
	"RequestId":"EFC9161F-15E3-4A6E-8A99-6661F464",
	"TaskId":"111111111"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

