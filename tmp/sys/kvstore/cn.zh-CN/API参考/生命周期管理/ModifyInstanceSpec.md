# ModifyInstanceSpec {#doc_api_R-kvstore_ModifyInstanceSpec .reference}

调用ModifyInstanceSpec变更Redis实例的规格。

-   各规格的详情请参见[实例规格表](~~107984~~)。
-   该API对应的控制台操作请参见[变更配置](~~26353~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceSpec&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-j6cxxxxxxxxxxxxx|目标实例的ID。

 |
|Action|String|否|ModifyInstanceSpec|系统规定参数，取值：ModifyInstanceSpec。

 |
|RegionId|String|否|cn-hangzhou|地域ID。

 |
|InstanceClass|String|否|redis.master.small.default|变更后的实例规格，各规格的InstanceClass值请参见[实例规格表](~~107984~~)。

 |
|BusinessInfo|String|否|000000000|活动ID、业务信息。

 |
|CouponNo|String|否|youhuiquan\_promotion\_option\_id\_for\_blank|优惠码，默认值：`youhuiquan_promotion_option_id_for_blank`。

 |
|ForceUpgrade|Boolean|否|true|是否强制变配，可选值：

 -   false（否）；
-   true（是）。

 **说明：** 默认值：true。

 |
|AutoPay|Boolean|否|true|是否自动付款，可选值：

 -   true（是）
-   false（否）

 默认值：false。

 **说明：** 当值为`false`时，请在实例即将到期时到控制台[手动续费](~~26352~~)。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|EffectiveTime|String|否|Immediately|变更执行时间，可选值：

 -   Immediately（立即执行）
-   MaintainTime（运维时间执行）

 默认值：Immediately。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|OrderId|String|111111111111111|订单ID。

 |
|RequestId|String|0DA1D7EF-C80D-432C-8758-7D225182626B|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceSpec
&InstanceId=r-j6cxxxxxxxxxx3d4
&InstanceClass=redis.master.small.default
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyInstanceSpecResponse>
      <OrderId>111111111111111</OrderId>
      <RequestId>0DA1D7EF-C80D-432C-8758-7D225182626B</RequestId>
</ModifyInstanceSpecResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"0DA1D7EF-C80D-432C-8758-7D225182626B",
	"OrderId":"111111111111111"
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|PaymentMethodNotFound|No payment method has been registered on the account.|帐户上没有登记付款方法。|
|400|HasRenewChangeOrder|This instance has a renewChange order.|实例还有续费变配订单。|

访问[错误中心](https://error-center.alibabacloud.com/status/product/R-kvstore)查看更多错误码。

