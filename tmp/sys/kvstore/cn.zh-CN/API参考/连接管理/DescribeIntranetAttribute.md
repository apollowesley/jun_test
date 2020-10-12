# DescribeIntranetAttribute {#doc_api_R-kvstore_DescribeIntranetAttribute .reference}

调用DescribeIntranetAttribute查询Redis实例当前的内网带宽。如果使用了临时调整带宽功能，还可查询临时带宽的过期时间。

您可以通过以下方式之一临时调整Redis实例的内网带宽：

-   调用[ModifyIntranetAttribute](~~128674~~)调整。
-   在Redis控制台调整，详细步骤请参见[临时调整带宽](~~102588~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeIntranetAttribute&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|Action|String|否|DescribeIntranetAttribute|系统规定参数，取值：DescribeIntranetAttribute。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|ExpireTime|String|2019-08-13T16:00:00Z|-   如果没有临时调整带宽则为`0`。
-   如果处于临时调整带宽状态则为临时带宽的过期时间。

 |
|IntranetBandwidth|Integer|20|实例当前的内网带宽，单位MB/s。

 |
|RequestId|String|846C9278-4D71-419F-BBE8-57C00A0A46EF|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeIntranetAttribute
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeIntranetAttributeResponse>
      <IntranetBandwidth>20</IntranetBandwidth>
	  <ExpireTime>2019-08-13T16:00:00Z</ExpireTime>
	  <RequestId>846C9278-4D71-419F-BBE8-57C00A0A46EF</RequestId>
</DescribeIntranetAttributeResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"IntranetBandwidth":20,
	"ExpireTime":"2019-08-13T16:00:00Z",
	"RequestId":"846C9278-4D71-419F-BBE8-57C00A0A46EF"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

