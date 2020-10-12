# ModifyAccountDescription {#doc_api_R-kvstore_ModifyAccountDescription .reference}

调用ModifyAccountDescription修改Redis账号的描述。

该API仅支持4.0版本的Redis实例。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=ModifyAccountDescription)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|AccountDescription|String|是|this is a test account|账号描述。

 -   需以中文、英文字母开头，不能以http: //或https: //开头；
-   可以包含中文、英文字母、“\_”、“ -”、数字；
-   长度为2~256个字符。

 |
|AccountName|String|是|demoaccount|账号名。以字母开头，由小写字母、数字、下划线组成，长度不超过16个字符。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|账号所属实例的ID。

 |
|Action|String|否|ModifyAccountDescription|系统规定参数，取值：ModifyAccountDescription。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyAccountDescription
&InstanceId=r-bp1xxxxxxxxxxxxx
&AccountName=demoaccount
&AccountDescription=this is a test account
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyAccountDescriptionResponse>
  <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</ModifyAccountDescriptionResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76"
}
```

## 错误码 { .section}

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

