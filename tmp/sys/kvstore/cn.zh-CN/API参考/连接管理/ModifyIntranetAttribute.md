# ModifyIntranetAttribute {#doc_api_R-kvstore_ModifyIntranetAttribute .reference}

调用ModifyIntranetAttribute临时调整Redis实例的内网带宽。

该API对应的控制台操作请参见[临时调整带宽](~~102588~~)。

 **带宽调整规则** 

-   标准版和读写分离版实例的带宽经过调整后变为原值的两倍。例如，原带宽为10MB/s的标准版-双副本实例，在临时调整后带宽变为20MB/s。
-   集群版单个节点的带宽为96MB/s，调整后为192MB/s。
-   内网带宽在调整满7天后的下一个00:00还原。例如，1月1日上午10:00开始带宽调整，到1月8日上午10:00满7天，1月9日00:00带宽还原。

**说明：** 带宽临时调整的时长无法修改，您可以在到期之后再次调整。如果需要长期提升带宽，请升级实例配置。


 **注意事项** 

以下操作会导致带宽恢复为原值：

-   集群版实例的大版本升级和小版本升级。
-   所有版本实例的升配、降配以及[可用区迁移](~~106272~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyIntranetAttribute&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|Action|String|否|ModifyIntranetAttribute|系统规定参数，取值：ModifyIntranetAttribute。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|97AC8948-D7E4-457E-BE03-850CF04EA622|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyIntranetAttribute
?InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyIntranetAttributeResponse>
      <RequestId>97AC8948-D7E4-457E-BE03-850CF04EA622</RequestId>
</ModifyIntranetAttributeResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"97AC8948-D7E4-457E-BE03-850CF04EA622"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

