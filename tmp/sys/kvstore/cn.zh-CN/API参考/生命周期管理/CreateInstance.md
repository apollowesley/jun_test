# CreateInstance {#doc_api_R-kvstore_CreateInstance .reference}

调用CreateInstance创建一个Redis实例。

该API对应的控制台操作请参见[创建实例](~~26351~~)。

创建Redis实例所需的实例规格请参见[实例规格表](~~107984~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=CreateInstance&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|CreateInstance|系统规定参数，取值：CreateInstance。

 |
|RegionId|String|是|cn-hangzhou|地域ID，可调用[DescribeRegions](~~61012~~)查询，使用此参数指定要创建实例的地域。

 |
|InstanceClass|String|否|redis.master.small.default|实例的规格，详细信息请参见[实例规格表](~~107984~~)。

 **说明：** 调用此接口需至少传递Capacity或InstanceClass中的一个参数。

 |
|Capacity|Long|否|16384|实例的存储容量，单位为MB。

 **说明：** 调用此接口需至少传递Capacity或InstanceClass中的一个参数。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|Token|String|否|AAAAAAAAAAAAAAAAAAAAAAAAAA|用于保证请求的幂等性。由客户端生成该参数值，要保证在不同请求间唯一，大小写敏感、不超过64个ASCII字符。

 |
|InstanceName|String|否|apitest|实例名称。 名称为2-128个字符，以大小写字母或中文开头，不支持字符`@/:=”<>{[]}`和空格。

 |
|Password|String|否|Acfr5xxx,.xxx|实例密码。 长度为8－32位，需包含大写字母、小写字母、特殊字符和数字中的至少三种，允许的特殊字符包括`!@#$%^&*()_+-=`。

 |
|ZoneId|String|否|cn-hangzhou-e|可用区ID，可调用[DescribeRegions](~~61012~~)查询，使用此参数指定要创建实例的可用区。

 |
|Config|String|否|\{\\"EvictionPolicy\\":\\"volatile-lru\\",\\"hash-max-ziplist-entries\\":512,\\"zset-max-ziplist-entries\\":128,\\"zset-max-ziplist-value\\":64,\\"set-max-intset-entries\\":512,\\"hash-max-ziplist-value\\":64\}|实例的详细配置，为JSON格式的字符串，参见[参数配置](~~43885~~)。

 |
|ChargeType|String|否|PostPaid|付费类型：

 -   PrePaid（预付费）
-   PostPaid（按量付费）

 **说明：** 默认为PostPaid。

 |
|NodeType|String|否|MASTER\_SLAVE|节点类型：

 -   STAND\_ALONE（单节点）；
-   MASTER\_SLAVE（多节点）；

 **说明：** 默认值为MASTER\_SLAVE。

 |
|NetworkType|String|否|VPC|网络类型：

 -   CLASSIC（经典网络）
-   VPC（专有网络）

 **说明：** 默认为经典网络。

 |
|VpcId|String|否|vpc-bp1oxxxxxxxxxxgzv26cf|VPC网络的ID。

 |
|VSwitchId|String|否|vsw-oqscxxxxxxxxxxxxx5e8c|虚拟交换机的ID。

 |
|Period|String|否|12|付费周期，ChargeType（付费类型）为PrePaid时为必选参数，单位为月，可选值：1-9，12，24，36 。

 **说明：** 付费类型为PostPaid时不支持传入此参数。

 |
|BusinessInfo|String|否|000000000|活动ID、业务信息。

 |
|CouponNo|String|否|youhuiquan\_promotion\_option\_id\_for\_blank|优惠码，默认值为：`youhuiquan_promotion_option_id_for_blank`。

 |
|SrcDBInstanceId|String|否|r-bp1xxxxxxxxxxxxxx|如需基于某个实例的备份集创建新实例，则在此参数中传递源实例的ID。

 |
|BackupId|String|否|111111111|如需基于某个实例的备份集创建新实例，则在此参数中传递源实例的备份集ID。通过调用[DescribeBackups](~~61081~~)可查询BackupId。

 |
|InstanceType|String|否|Redis|实例类型，取值：

 -   Redis
-   Memcache

 **说明：** 默认为Redis。

 |
|EngineVersion|String|否|4.0|版本类型，取值：

 -   2.8
-   4.0
-   5.0

 **说明：** 默认值为2.8。

 |
|PrivateIpAddress|String|否|172.16.0.10|指定新实例的内网IP地址。

 **说明：** 内网IP需在实例所属的交换机网段内。

 |
|AutoRenew|String|否|true|是否开启自动续费，可选值：

 -   true（开启）
-   false（不开启）

 **说明：** 默认值：false。

 |
|AutoRenewPeriod|String|否|3|自动续费周期，单位为月，可选值：

 -   1
-   2
-   3
-   6
-   12

 **说明：** 当**AutoRenew**为`true`时该参数必选。

 |
|AutoUseCoupon|String|否|false|是否使用代金券，可选值：

 -   true（使用）
-   false（不使用）

 **说明：** 默认值：false。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|Bandwidth|Long|32|实例带宽限制，单位：MB/s。

 |
|Capacity|Long|16384|实例的存储容量，单位：MB。

 |
|ChargeType|String|PostPaid|付费类型：PrePaid 或 PostPaid。默认为 PostPaid。

 |
|Config|String|\{\\"EvictionPolicy\\":\\"volatile-lru\\",\\"hash-max-ziplist-entries\\":512,\\"zset-max-ziplist-entries\\":128,\\"zset-max-ziplist-value\\":64,\\"set-max-intset-entries\\":512,\\"hash-max-ziplist-value\\":64\}|实例的详细配置。

 |
|ConnectionDomain|String|r-j6cxxxxxxxxxxxxx.redis.rds.aliyuncs.com|Redis实例的内网连接地址。

 |
|Connections|Long|10000|实例连接数限制，单位：个。

 |
|EndTime|String|2019-01-18T16:00:00Z|预付费实例到期时间，采用ISO8601表示法，并使用UTC时间，格式为： YYYY-MM-DDThh:mm:ssZ。

 |
|InstanceId|String|r-j6cxxxxxxxxxxxxx|实例ID（全局唯一）。

 |
|InstanceName|String|apitest|实例名称。

 |
|InstanceStatus|String|Creating|实例的当前状态。

 |
|NetworkType|String|VPC|网络类型：

 -   CLASSIC（经典网络）
-   VPC（专有网络）

 **说明：** 默认为经典网络。

 |
|NodeType|String|MASTER\_SLAVE|节点类型：

 -   STAND\_ALONE（单节点）
-   MASTER\_SLAVE（多节点）

 **说明：** 默认值为MASTER\_SLAVE。

 |
|Port|Integer|6379|Redis服务端口。

 |
|PrivateIpAddr|String|172.16.0.10|实例的内网IP地址。

 |
|QPS|Long|100000|每秒访问次数，此处为当前规格实例的理论值。

 |
|RegionId|String|cn-hongkong|实例所在地域。

 |
|RequestId|String|5DEA3CC9-F81D-4387-8E97-CEA40F09244D|请求ID。

 |
|UserName|String|r-j6cxxxxxxxxxxxxx|连接Redis的账号。

 |
|VSwitchId|String|vsw-oqscxxxxxxxxxxxxxxxxx|虚拟交换机ID。

 |
|VpcId|String|vpc-bp1xxxxxxxxxxxxxxxxxx|专有网络（VPC）的ID。

 |
|ZoneId|String|cn-hongkong-b|实例所属的可用区的ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=CreateInstance
&RegionId=cn-hongkong
&InstanceClass=redis.master.2xlarge.default
&InstanceName=apitest
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<CreateInstanceResponse>
	  <ChargeType>PostPaid</ChargeType>
	  <Config>{"EvictionPolicy":"volatile-lru","hash-max-ziplist-entries":512,"zset-max-ziplist-entries":128,"zset-max-ziplist-value":64,"set-max-intset-entries":512,"hash-max-ziplist-value":64}</Config>
	  <InstanceId>r-j6cxxxxxxxxxxxxx</InstanceId>
	  <UserName>r-j6cxxxxxxxxxxxxx</UserName>
	  <ZoneId>cn-hongkong-b</ZoneId>
	  <InstanceStatus>Creating</InstanceStatus>
	  <Port>6379</Port>
	  <QPS>100000</QPS>
	  <RequestId>96132219-F1E6-40AB-8853-C32055B84BE1</RequestId>
	  <RegionId>cn-hongkong</RegionId>
	  <Capacity>16384</Capacity>
	  <ConnectionDomain>r-j6cxxxxxxxxxxxxx.redis.rds.aliyuncs.com</ConnectionDomain>
	  <InstanceName>apitest</InstanceName>
	  <Bandwidth>32</Bandwidth>
	  <Connections>10000</Connections>
</CreateInstanceResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"ChargeType":"PostPaid",
	"Config":"{\"EvictionPolicy\":\"volatile-lru\",\"hash-max-ziplist-entries\":512,\"zset-max-ziplist-entries\":128,\"zset-max-ziplist-value\":64,\"set-max-intset-entries\":512,\"hash-max-ziplist-value\":64}",
	"InstanceId":"r-j6cxxxxxxxxxxxxx",
	"UserName":"r-j6cxxxxxxxxxxxxx",
	"ZoneId":"cn-hongkong-b",
	"InstanceStatus":"Creating",
	"Port":6379,
	"QPS":100000,
	"RequestId":"96132219-F1E6-40AB-8853-C32055B84BE1",
	"RegionId":"cn-hongkong",
	"Capacity":16384,
	"ConnectionDomain":"r-j6cxxxxxxxxxxxxx.redis.rds.aliyuncs.com",
	"InstanceName":"apitest",
	"Connections":10000,
	"Bandwidth":32
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|MissingParameter|Period is mandatory for this action.|实例Id是必传参数|
|400|InvalidToken.Malformed|The Specified parameter "Token" is not valid.|Token验证失败|
|400|InvalidInstanceName.Malformed|The Specified parameter "InstanceName" is not valid.|InstanceName验证失败|
|400|InvalidPassword.Malformed|The Specified parameter "Password" is not valid.|密码验证无效|
|400|InsufficientBalance|Your account does not have enough balance.|账户余额不足，请先充值再操作。|
|403|RealNameAuthenticationError|Your account has not passed the real-name authentication yet.|您的帐户还没有通过实名认证。|
|400|QuotaExceed.AfterpayInstance|Living afterpay instances quota exceeded.|超过了支付实例配额。|
|400|InvalidCapacity.NotFound|The Capacity provided does not exist in our records.|Capacity 容量非法。|
|400|ResourceNotAvailable|Resource you requested is not available for finance user.|您所请求的资源对财务用户来说是不可用的。|
|400|PaymentMethodNotFound|No payment method has been registered on the account.|帐户上没有登记付款方法。|
|400|IdempotentParameterMismatch|Request uses a client token in a previous request but is not identical to that request.|幂等性校验不过|
|400|QuotaNotEnough|Quota not enough in this zone.|这个区域的配额是不够的。|
|400|QuotaExceed|Living afterpay instances quota exceed.|超过了支付实例配额。|
|400|VpcServiceError|Invoke vpc service failed.|调用vpc服务失败。|
|400|IzNotSupportVpcError|Specify iz not support vpc.|指定 iz不支持Vpc。|

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

