# RenewInstance {#reference_y3s_xyx_wdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to renew a subscription instance.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|Yes|For more information, see [Public parameters](intl.en-US/API reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Operation API name. Value: RenewInstance.|
|InstanceId|String|Yes|Instance ID \(globally unique\)|
|InstanceClass|String|No|Target type for configuration change. The type configuration can be changed when the instance is renewed. If the target type is different from the current type, the configuration is changed at the original expiration time.|
|Period|Long|Yes|Prepayment renewal period. Unit: month. Supported values: 1–9, 12, 24, and 36.|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameters\>| |For more information, see [Public return parameters](intl.en-US/API reference/Common Parameters.md#section_rjr_zgp_wbb).|
|OrderId|String|Order ID|
|EndTime|String|Expiration time of the instance after renewal|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
<Common request parameters>
&Action= RenewInstance
&InstanceId=736538d0a6894665
&InstanceClass= redis.master.mid.default
```

## Response example {#section_hjp_tkw_wbb .section}

```

"OrderId" : "201294800290011"
"EndTime" : "2018-03-19T00:00:00Z"
"RequestId" : "4B75DB12-3FFF-4FF4-B985-E78CDCADB959"

```

