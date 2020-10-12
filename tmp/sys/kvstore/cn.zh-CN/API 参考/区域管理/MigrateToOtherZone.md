# MigrateToOtherZone {#reference_tlk_wc4_qfb .reference}

调用该API可以将实例迁移至同地域内的其他可用区。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：MigrateToOtherZone。|
|DBInstanceId|String|是|实例 ID（全局唯一）|
|ZoneId|String|是|要迁移到的可用区（目的可用区）|
|VSwitchId|String|否|VSwitch ID**说明：** 

需满足以下条件：

-   VSwitch所在可用区须与ZoneId（目的可用区）一致；
-   如果是VPC实例，则该参数必传。

|
|EffectiveTime|String|否|迁移执行时间。可选值：-   Immediately，表示立即执行；
-   MaintainTime，表示可维护时间内执行；
-   不传递该参数则默认为Immediately，迁移将立即执行。

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=MigrateToOtherZone
&DBInstanceId=r-xxxxxxxxxxxxxxx
&ZoneId=cn-hangzhou-g
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<MigrateToOtherZoneResponse>
     <RequestId>29B0BF34-D069-4495-92C7-FA6D94528A9E</RequestId>
</MigrateToOtherZoneResponse>
```

**JSON格式**

```
{
  "RequestId":"29B0BF34-D069-4495-92C7-FA6D94528A9E"
 }
```

