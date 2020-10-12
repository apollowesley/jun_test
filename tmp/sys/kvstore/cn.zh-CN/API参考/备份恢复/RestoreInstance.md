# RestoreInstance {#doc_api_R-kvstore_RestoreInstance .reference}

调用RestoreInstance将备份文件中的数据恢复到指定的Redis实例中。

该API对应的控制台操作请参见[备份与恢复](~~43886~~)。

**说明：** 调用此API会使用备份数据覆盖Redis实例的现有数据，风险较大，请务必谨慎操作。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=RestoreInstance&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|RestoreInstance|系统规定参数，取值：RestoreInstance。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|需要进行数据恢复的实例的ID。

 |
|BackupId|String|是|111111111|备份文件ID。您可以调用[DescribeBackups](~~61081~~)查询。

 |
|RegionId|String|否|cn-hangzhou|地域ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com
?Action=RestoreInstance
&InstanceId=r-bp1xxxxxxxxxxxxx
&BackupId=111111111
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<RestoreInstanceResponse>
      <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</RestoreInstanceResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

