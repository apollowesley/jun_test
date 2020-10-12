# CreateBackup {#doc_api_R-kvstore_CreateBackup .reference}

调用CreateBackup为Redis实例创建数据备份。

该API对应的控制台操作请参见[备份与恢复](~~43886~~)。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=CreateBackup)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|CreateBackup|系统规定参数，取值：CreateBackup。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|BackupJobID|String|1111111|备份任务ID。

 |
|RequestId|String|2D719BD9-A536-4D8B-BEA9-CD299DE2AE1B|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=CreateBackup
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<CreateBackupResponse>
  <RequestId>2D719BD9-A536-4D8B-BEA9-CD299DE2AE1B</RequestId>
  <BackupJobID>1111111</BackupJobID>
</CreateBackupResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"2D719BD9-A536-4D8B-BEA9-CD299DE2AE1B",
	"BackupJobID":"1111111"
}
```

## 错误码 { .section}

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

