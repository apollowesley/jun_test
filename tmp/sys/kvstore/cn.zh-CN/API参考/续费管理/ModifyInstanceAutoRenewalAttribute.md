# ModifyInstanceAutoRenewalAttribute {#doc_api_R-kvstore_ModifyInstanceAutoRenewalAttribute .reference}

调用ModifyInstanceAutoRenewalAttribute开启或者关闭Redis实例的到期前自动续费功能。

**说明：** 自动续费将于实例到期前7天触发。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceAutoRenewalAttribute&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|DBInstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。多个实例ID用半角逗号（‘,’）拼接，最多允许传入30个实例ID。

 |
|Action|String|否|ModifyInstanceAutoRenewalAttribute|系统规定参数，取值：ModifyInstanceAutoRenewalAttribute。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|Duration|String|否|3|自动续费周期，当AutoRenew=True时，该值有效且必传，取值范围为1-12。实例到期时，将以月为单位进行自动续费，月数等于该值。

 |
|AutoRenew|String|否|True|开启或关闭自动续费，可选值：

 -   True（开启）
-   False（关闭）

 **说明：** 默认值：False。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|52D901ED-E0A5-42FB-B9DB-39C295C37738|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceAutoRenewalAttribute
&DBInstanceId=r-bp1xxxxxxxxxxxxx
&AutoRenew=True
&Duration=1
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyInstanceAutoRenewalAttributeResponse>
      <RequestId>52D901ED-E0A5-42FB-B9DB-39C295C37738</RequestId>
</ModifyInstanceAutoRenewalAttributeResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"52D901ED-E0A5-42FB-B9DB-39C295C37738"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

