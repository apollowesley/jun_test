# SwitchNetwork {#doc_api_R-kvstore_SwitchNetwork .reference}

调用SwitchNetwork切换Redis实例的网络类型，支持从经典网络切换为VPC网络。

该API对应的控制台操作请参见[切换为专有网络](~~43880~~)。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=SwitchNetwork)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|SwitchNetwork|系统规定参数，取值：SwitchNetwork。

 |
|InstanceId|String|是|r-j6cxxxxxxxxxxxxxx|需要切换网络类型的实例的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|ClassicExpiredDays|String|否|30|保留经典网络IP的时间，单位：天。 可选值：14、30、60、120。若选择保留经典网络地址，则该参数必选。

 **说明：** 切换到VPC网络后，如需修改经典网络IP的保留时间请参见[ModifyInstanceNetExpireTime](~~61010~~)。

 |
|RetainClassic|String|否|True|是否保留经典网络 IP：

 -   True（保留）
-   False（不保留）

 **说明：** 默认值为False。

 |
|TargetNetworkType|String|否|VPC|需要切换到的网络类型，当前仅支持经典网络切换到VPC，因此该参数仅有默认值VPC。

 |
|VSwitchId|String|否|vsw-sdrxxxxxxxxxxxxxxxxxx|实例切换网络类型后VPC内的交换机ID，如果传入了VpcId则此参数为必选。

 |
|VpcId|String|否|vpc-bp1xxxxxxxxxxxxxxxxxx|实例切换到VPC网络后所属的VPC ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|F0997EE8-F4C2-4503-9168-85177ED78C70|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com
?Action=SwitchNetwork
&InstanceId=r-j6cxxxxxxxxxxxxx
&TargetNetworkType=VPC
&VpcId=vpc-bp1xxxxxxxxxxxxxxxxxx
&VSwitchId=vsw-sdrxxxxxxxxxxxxxxxxxx
&RetainClassic=True
&ClassicExpiredDays=30
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<SwitchNetworkResponse>
  <requestId>F0997EE8-F4C2-4503-9168-85177ED78C70</requestId>
</SwitchNetworkResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"requestId":"F0997EE8-F4C2-4503-9168-85177ED78C70"
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|InvalidToken.Malformed|The Specified parameter "Token" is not valid.|Token验证失败|
|400|VpcServiceError|Invoke vpc service failed.|调用vpc服务失败。|
|400|IzNotSupportVpcError|Specify iz not support vpc.|指定 iz不支持Vpc。|
|400|IzNotSupportSwitchNetworkError|Specify iz not support switch network.|指定 iz不支持交换网络。|
|400|VpcId is wrong.|VpcId is wrong.|VpcId错误|

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

