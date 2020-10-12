# MigrateToOtherZone {#doc_api_R-kvstore_MigrateToOtherZone .reference}

调用MigrateToOtherZone将Redis实例迁移到同地域内的其它可用区。

该API对应的控制台操作请参见[迁移可用区](~~106272~~)。

**说明：** 

-   如果实例是从经典网络切换到专有网络的，并且保留了经典网络的连接地址，则需等到经典网络连接地址到期释放后才能执行可用区迁移。
-   迁移之后实例的连接地址不会改变，但VIP会发生变化，请在业务中使用连接地址连接实例而非其VIP地址。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=MigrateToOtherZone&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|MigrateToOtherZone|系统规定参数，取值：MigrateToOtherZone。

 |
|DBInstanceId|String|是|r-bp1xxxxxxxxxxxxx|实例 ID（全局唯一）。

 |
|ZoneId|String|是|cn-hangzhou-g|目的可用区的ID，可调用[DescribeZones](~~94527~~)接口查询。

 |
|RegionId|String|否|cn-hangzhou|地域ID，可以调用[DescribeRegions](~~61012~~)接口查询。

 |
|EffectiveTime|String|否|Immediately|数据迁移后执行数据库切换的时间。可选值：

 -   Immediately（迁移完立即切换）
-   MaintainTime（可维护时间段内切换）

 **说明：** 默认值：Immediately。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|VSwitchId|String|否|vsw-sdrxxxxxxxxxxxxxxxxxx|虚拟交换机的ID 。

 **说明：** 

-   VSwitch所在可用区须与ZoneId（目的可用区）一致；
-   如果实例的网络类型为VPC，则该参数为必选。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|29B0BF34-D069-4495-92C7-FA6D94528A9E|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=MigrateToOtherZone
&DBInstanceId=r-bp1xxxxxxxxxxxxx
&ZoneId=cn-hangzhou-g
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<MigrateToOtherZoneResponse>
      <RequestId>29B0BF34-D069-4495-92C7-FA6D94528A9E</RequestId>
</MigrateToOtherZoneResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"29B0BF34-D069-4495-92C7-FA6D94528A9E"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

