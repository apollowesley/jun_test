# DescribePrice {#doc_api_R-kvstore_DescribePrice .reference}

调用DescribePrice查询创建Redis实例、升级配置或续费等操作产生的费用。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=DescribePrice)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DescribePrice|系统规定参数，取值：DescribePrice。

 |
|OrderType|String|是|BUY|订单类型：

 -   BUY（购买）
-   UPGRADE（变配）
-   RENEW（续费）

 |
|InstanceId|String|否|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|Capacity|Long|否|1024|实例的存储容量， 单位：MB。

 |
|InstanceClass|String|否|redis.master.small.default|实例规格，请参见[实例规格表](~~107984~~)。

 |
|ZoneId|String|否|cn-hangzhou-e|可用区ID。可调用[DescribeZones](~~94527~~)查询。

 |
|ChargeType|String|否|PostPaid|付费类型：

 -   PostPaid（按量付费）
-   PrePaid（包年包月）

 **说明：** 默认值为PostPaid。

 |
|NodeType|String|否|MASTER\_SLAVE|节点类型：

 -   STAND\_ALONE（单节点）
-   MASTER\_SLAVE（主从节点）

 **说明：** 默认值为MASTER\_SLAVE。

 |
|Period|Long|否|3|包年包月时长，单位为月。取值范围为：1-9、12、24、36。

 |
|Quantity|Long|否|1|订购实例数量，默认值为1。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|Order| | |订单信息组成的集合。

 |
|└Coupons| | |优惠券组成的集合。

 |
|└CouponNo|String|youhuiquan\_promotion\_option\_id\_for\_blank|优惠券编码。

 |
|└Description|String|coupondemo|备注。

 |
|└IsSelected|String|true|是否选中该优惠券。

 |
|└Name|String|内部结算用户0元付|订单实际交易价。

 |
|└Currency|String|CNY|币种。

 |
|└DiscountAmount|Float|0.21|订单优惠金额。

 |
|└OriginalAmount|Float|0.21|订单原价。

 |
|└RuleIds| |RuleId: 1111111111|命中策略ID集合。

 |
|└TradeAmount|Float|10|订单实际交易价。

 |
|SubOrders| | |优惠券对应的策略集合。

 |
|└DiscountAmount|Float|0.21|订单优惠金额。

 |
|└InstanceId|String|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|└OriginalAmount|Float|0.21|订单原价。

 |
|└RuleIds| |RuleId: 1111111111|命中策略ID集合。

 |
|└TradeAmount|Float|10|订单实际交易价。

 |
|Rules| | |活动规则组成的集合。

 |
|└Name|String|内部结算用户0元付|订单实际交易价。

 |
|└RuleDescId|Long|1111111111|策略ID。

 |
|└Title|String|demo|策略标题。

 |
|RequestId|String|4D830342-E341-4AA8-BA96-0257FE613EEF|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

http(s)://[Endpoint]/?Action=DescribePrice
&OrderType=BUY
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribePriceResponse>
  <SubOrders>
    <SubOrder>
      <RuleIds>
        <RuleId>1111111111</RuleId>
        <RuleId>1111111111</RuleId>
      </RuleIds>
      <OriginalAmount>0.21</OriginalAmount>
      <TradeAmount>0</TradeAmount>
      <DiscountAmount>0.21</DiscountAmount>
    </SubOrder>
  </SubOrders>
  <RequestId>4D830342-E341-4AA8-BA96-0257FE613EEF</RequestId>
  <Order>
    <RuleIds>
      <RuleId>1001199213</RuleId>
    </RuleIds>
    <OriginalAmount>0.21</OriginalAmount>
    <TradeAmount>0</TradeAmount>
    <Coupons/>
    <DiscountAmount>0.21</DiscountAmount>
    <Currency>CNY</Currency>
  </Order>
  <Rules>
    <Rule>
      <Name>内部结算用户0元付</Name>
      <RuleDescId>1001199213</RuleDescId>
    </Rule>
  </Rules>
</DescribePriceResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"SubOrders":{
		"SubOrder":[
			{
				"OriginalAmount":0.21,
				"RuleIds":{
					"RuleId":[
						1111111111,
						1111111111
					]
				},
				"TradeAmount":0,
				"DiscountAmount":0.21
			}
		]
	},
	"RequestId":"4D830342-E341-4AA8-BA96-0257FE613EEF",
	"Order":{
		"OriginalAmount":0.21,
		"RuleIds":{
			"RuleId":[
				1001199213
			]
		},
		"TradeAmount":0,
		"Coupons":{
			"Coupon":[]
		},
		"DiscountAmount":0.21,
		"Currency":"CNY"
	},
	"Rules":{
		"Rule":[
			{
				"Name":"内部结算用户0元付",
				"RuleDescId":1001199213
			}
		]
	}
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|MissingParameter|Period is mandatory for this action.|实例Id是必传参数|
|400|MissingParameter|InstanceId is mandatory for this action.|实例名称/密码至少一项是必填的。|

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

