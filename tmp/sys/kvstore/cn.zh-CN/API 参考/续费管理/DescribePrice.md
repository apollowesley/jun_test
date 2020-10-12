# DescribePrice {#reference_gpr_44z_qfb .reference}

调用该API可以查询商品价格。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribePrice。|
|InstanceId|String|是|实例ID（全局唯一）。多个实例ID用`,`（半角符号）拼接，最多允许30个实例ID。|
|RegionId|String|是|地域ID。可通过[DescribeRegions](cn.zh-CN/API 参考/区域管理/DescribeRegions.md#)查询。|
|ZoneId|String|是|可用区ID。可通过[DescribeZones](cn.zh-CN/API 参考/区域管理/DescribeZones.md#)查询。|
|OrderType|String|是|订单类型：-   BUY
-   UPGRADE
-   RENEW

|
|Capacity|Long|否|存储实例容量， 单位：MB。|
|InstanceClass|String|否|实例规格|
|ChargeType|String|否|付费类型：-   Postpaid（后付费）；
-   Prepaid（预付费）；
-   默认值为`PostPaid`。

|
|NodeType|String|否|节点类型：-   STAND\_ALONE（单节点）；
-   MASTER\_SLAVE（多节点）；
-   默认值为`MASTER_SLAVE`。

|
|Period|Long|否|预付费续费时长，单位是月。取值范围为：1-9、12、24、36。|
|Quantity|String|否|订购实例数量，取值范围：\[1,30\]。默认值为`1`。|
|Instances|String（JSON）|否|包含多个Instance的JSON格式字符串|
|BusinessInfo|String|否|扩展参数|
|CouponNo|String|否|优惠码。不填默认为：`youhuiquan_promotion_option_id_for_blank`。|
|ForceUpgrade|String|否|是否强制升级：-   false（否）；
-   true（是）；
-   默认为`true`。

|
|OrderParamOut|String|否|是否返回订单参数：-   false（不返回）；
-   true（返回）；
-   默认为`false`。

|

|名称|类型|是否必须|描述|
|--|--|----|--|
|InstanceId|String|否|实例ID|
|Capacity|Long|是|存储实例容量， 单位：MB。|
|InstanceClass|String|是|实例规格|
|OrderType|String|是|订单类型：-   BUY
-   UPGRADE
-   RENEW

|
|IzNo|String|是|可用区ID。可通过[DescribeZones](cn.zh-CN/API 参考/区域管理/DescribeZones.md#)查询。|
|ChargeType|String|否|付费类型：-   Postpaid（后付费）；
-   Prepaid（预付费）；
-   默认值为`PostPaid`。

|
|NodeType|String|否|节点类型：-   STAND\_ALONE（单节点）；
-   MASTER\_SLAVE（多节点）；
-   默认值为`MASTER_SLAVE`。

|
|Period|Long|否|预付费续费时长，单位是月。取值范围为：1-9、12、24、36。|
|Quantity|String|否|订购实例数量，取值范围：\[1,30\]。默认值：`1`。|
|VPCId|String|否|如InstanceNetworkType为`VPC`则此参数必传。|
|VSwitchId|String|否|如InstanceNetworkType为`VPC`则此参数必传。|
|DBInstanceDescription|String|否|实例的描述或备注信息|
|SecurityIPList|String|否|允许访问该实例下所有数据库的IP名单|
|AutoPay|String|否|自动付费：-   True（是）
-   False（否）

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|Rules|List<Rule\>|活动规则组成的集合|
|SubOrders|List<SubOrder\>|优惠券命中策略集合|
|Order|List<OrderResult\>|订单信息组成的集合|
|OrderParams|String|订单参数，如果请求中传递了OrderParamOut参数，且值为`true`时，将返回OrderParams参数。|

|名称|类型|描述|
|--|--|--|
|RuleDescId|Long|策略ID|
|Name|String|策略名称|
|Title|String|策略标题|

|名称|类型|描述|
|--|--|--|
|OriginalAmount|Float|订单原价|
|TradeAmount|Float|订单实际交易价|
|DiscountAmount|Float|订单优惠金额|
|InstanceId|String|实例ID|
|RuleIds|List|命中策略ID集合|

|名称|类型|描述|
|--|--|--|
|OriginalAmount|Float|订单原价|
|TradeAmount|Float|订单实际交易价|
|DiscountAmount|Float|订单优惠金额|
|RuleIds|List|命中策略ID集合|
|Currency|String|币种|
|Coupons|List<Coupon\>|优惠券组成的集合|

|名称|类型|描述|
|--|--|--|
|CouponNo|String|优惠券编码|
|Name|String|订单实际交易价|
|Description|String|备注|
|IsSelected|String|是否选中该优惠券。|

## 请求示例 {#section_d3l_4kw_wbb .section}

**示例 1**

```
https://r-kvstore.aliyuncs.com/
?Action=DescribePrice
&RegionId=cn-hangzhou
&ZoneId=cn-hangzhou-b
&OrderType=UPGRADE
&ChargeType=PostPaid
&Capacity=4096
&<公共请求参数>
```

**示例 2**

```
https://r-kvstore.aliyuncs.com/
?Action=DescribePrice
&RegionId=cn-hangzhou
&ZoneId=cn-hangzhou-b
&OrderType=BUY
&Instances=
        [
          {
            "RegionId": "cn-hangzhou",
            "ZoneId": "cn-hangzhou-b",
            "InstanceClass": "redis.master.small.default",
            "ChargeType": "PostPaid",
            "OrderType": "BUY",
            "Period": "1",
            "Quantity": "1",
            "Capacity": "4096"
          }
        ]    
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<DescribePriceResponse>  
	<SubOrders>
		<SubOrder>
			<RuleIds>
				<RuleId>1001199213</RuleId>
				<RuleId>1001199213</RuleId>
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
		<Coupons></Coupons>
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

**JSON格式**

```
{
    "SubOrders":{
        "SubOrder":[
            {
                "RuleIds":{
                    "RuleId":[
                        1001199213,
                        1001199213
                    ]
                },
                "OriginalAmount":0.21,
                "TradeAmount":0,
                "DiscountAmount":0.21
            }
        ]
    },
    "RequestId":"4D830342-E341-4AA8-BA96-0257FE613EEF",
    "Order":{
        "RuleIds":{
            "RuleId":[
                1001199213
            ]
        },
        "OriginalAmount":0.21,
        "TradeAmount":0,
        "Coupons":{
            "Coupon":[

            ]
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

