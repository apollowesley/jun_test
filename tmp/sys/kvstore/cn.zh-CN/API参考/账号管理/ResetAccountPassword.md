# ResetAccountPassword {#doc_api_R-kvstore_ResetAccountPassword .reference}

调用ResetAccountPassword重置Redis账号的密码。

该API仅支持4.0版本的Redis实例。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=ResetAccountPassword)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|ResetAccountPassword|系统规定参数，取值：ResetAccountPassword。

 |
|AccountName|String|是|demoaccount|账号名。以字母开头，由小写字母、数字、下划线组成，长度不超过16个字符。

 |
|AccountPassword|String|是|uWonno\_233|新密码。长度为8－32位，需包含大写字母、小写字母、特殊字符和数字中的至少三种，允许的特殊字符包括`!@#$%^&*()_+-=`。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|账号所属实例的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|8BE02313-5395-4EBE-BAE7-E90A053F1E07|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ResetAccountPassword
&AccountName=demoaccount
&AccountPassword=uWonno_233
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ResetAccountResponse>
  <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</ResetAccountResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"8BE02313-5395-4EBE-BAE7-E90A053F1E07"
}
```

## 错误码 { .section}

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

