# DeleteAccount {#doc_api_R-kvstore_DeleteAccount .reference}

调用DeleteAccount删除一个Redis账号。

-   该API仅支持4.0版本的Redis实例；
-   实例的状态为运行中时才能调用该API。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=DeleteAccount)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DeleteAccount|系统规定参数，取值：DeleteAccount。

 |
|AccountName|String|是|demoaccount|账号名。以小写字母开头，由小写字母、数字、下划线组成，长度不超过16个字符。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|账号所属实例的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|8129F11A-D70B-43A6-9455-CE9EAA71F095|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DeleteAccount
&AccountName=demoaccount
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DeleteAccountResponse>
  <RequestId>8129F11A-D70B-43A6-9455-CE9EAA71F095</RequestId>
</DeleteAccountResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"8129F11A-D70B-43A6-9455-CE9EAA71F095"
}
```

## 错误码 { .section}

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

