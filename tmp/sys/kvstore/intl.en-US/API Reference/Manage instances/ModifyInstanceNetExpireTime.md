# ModifyInstanceNetExpireTime {#reference_yx1_wv2_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

If an instance has been switched from a classic network to a VPC and the classic network connection address is retained, this API can be called to prolong the retention period of the classic network connection address.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|Yes|For more information, see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: ModifyInstanceNetExpireTime.|
|InstanceId|String|Yes|Instance ID \(globally unique\)|
|ConnectionString|String|Yes|Domain name used to access the classic network|
|ClassicExpiredDays|String|Yes|Retention period. Supported values: 14, 30, 60, and 120.|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common response parameters\>|-|For more information, see [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
<Common request parameters>
&Action= ModifyInstanceNetExpireTime
&ConectionString= fdafas32323ed.redis.rds.aliyuncs.com
&InstanceId=fdafas32323ed
& ClassicExpiredDays=120
```

## Response example {#section_hjp_tkw_wbb .section}

```

"RequestId" : "AAAF99B1-69ED-4E80-8CD5-272C09E46ACF"

```

