# DescribeInstanceAttribute {#reference_nyk_w52_xdb .reference}

调用该API可以查询实例的详细信息。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeInstanceAttribute|
|InstanceId|String|是|实例 ID （全局唯一）|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|Instances|List|由 DBInstanceAttribute 组成的数组|

## DBInstanceAttribute 的参数构成 {#section_c14_bpw_wbb .section}

|名称|类型|描述|
|--|--|--|
|InstanceId|String|实例 ID （全局唯一）|
|InstanceName|String|实例名称|
|Capacity|Long|申请到的存储实例容量； 单位：MB。|
|InstanceClass|String|实例规格|
|Bandwidth|Long|实例带宽限制；单位：Mbps。|
|Connections|Long|实例连接数限制；单位：个。|
|ConnectionDomain|String|Redis 实例的连接域名（仅支持内网访问）。|
|Port|Int|Redis 连接端口|
|RegionId|String|申请的存储实例所在区域。参见 [DescribeRegions](intl.zh-CN/API 参考/实例管理/DescribeRegions.md#)。|
|ZoneId|String|RegionId 下的可用区编码。参见 [DescribeRegions](intl.zh-CN/API 参考/实例管理/DescribeRegions.md#)。|
|InstanceStatus|String| 实例状态：

 -   Normal：正常
-   Creating：创建中
-   Changing：修改中
-   Inactive：被禁用

 |
|ChargeType|String|付费类型：PrePaid 或 PostPaid|
|CreateTime|String|实例创建时间。采用ISO8601 表示法，并使用 UTC 时间。格式为：`YYYY-MM-DDThh:mm:ssZ`。|
|EndTime|String|预付费实例到期时间。采用ISO8601 表示法，并使用 UTC 时间。格式为： `YYYY-MM-DDThh:mm:ssZ`。|
|NetworkType|String|网络类型：CLASSIC 或 VPC|
|VpcId|String|VPC 的 ID|
|VSwitchId|String|虚拟交换机 ID|
|PrivateIpAddress|String|私有 IP 地址|
|MaintainStartTime|String|可运维开始时间，返回格式`HH:mmZ`，如02:00Z。|
|MaintainEndTime|String|可运维结束时间，返回格式：`HH:mmZ` ，如02:00Z。|
|SecurityIPList|String|IP 白名单|
|AvailabilityValue|String|当月的可用性指标|

**说明：** 出于历史兼容性考虑，该函数会有些返回结构在文档中并未提及（如 Config、Region 等），后续阿里云将逐步把这些字段删除，请您在调用 API 时不要依赖这些在文档中没有涉及的返回字段。

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com、
?<公共请求参数>
&Action=DescribeInstanceAttribute
&InstanceId=736538d0a6894665
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
{
    "Instances" : {
        " DBInstanceAttribute " : [{
                "Bandwidth" : 128,
                "Capacity" : 512,
                "ConnectionDomain" : "de5d88e34d004211.m.cnhzalicm10pub001.r-kvstore.aliyuncs.com.com",
                "Connections" : 300,
                "ZoneId" : "cn-qingdao-b",
                "InstanceId" : "de5d88e34d004211",
                "InstanceName" : "wl123456",
                "InstanceStatus" : "Available",
                 "InstanceClass" : "redis.master.mid.default",
                "Port" : 11211,
                "QPS" : 4500,
                "RegionId" : "cn-qingdao",
                 "ChargType"："PostPaid",
                  "NetworkType":"ClASSSIC"
                "MaintainStartTime"："02：00Z"，
                 "MaintainEndTime"："06：00Z"，
                 "SecurityIPList": "192.168.0.1"，
                  "AvailabilityValue":"100%"
        }]
  }
"RequestId" : "283746AF-82B3-4BFF-88CC-BF34CDE2732”
}
```

