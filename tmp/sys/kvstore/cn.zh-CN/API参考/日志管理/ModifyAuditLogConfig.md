# ModifyAuditLogConfig {#doc_api_R-kvstore_ModifyAuditLogConfig .reference}

调用ModifyAuditLogConfig设置审计日志的保留天数。

该API对应的控制台操作请参见[设置审计日志保留时间](~~102046~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyAuditLogConfig&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|Retention|String|是|15|审计日志保留的天数，取值范围：7-30。

 |
|Action|String|否|ModifyAuditLogConfig|系统规定参数，取值：ModifyAuditLogConfig。

 |
|RegionId|String|否|cn-hangzhou|地域ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|036A268F-9B77-473C-B3CC-970FF56698EC|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyAuditLogConfig
&InstanceId=r-bp1xxxxxxxxxxxxx
&Retention=15
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyAuditLogConfigResponse>
      <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</ModifyAuditLogConfigResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

