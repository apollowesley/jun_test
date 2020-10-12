# DescribeInstanceConfig {#reference_zr3_pqx_wdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to view the configuration parameters of an instance.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|Yes|See [Public parameters](reseller.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: DescribeInstanceConfig.|
|InstanceId|String|Yes|Instance ID|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameters\>|-|See [Public return parameters](reseller.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|
|Config|String|Configuration parameters for the instance. See [Instance configurations table](reseller.en-US/API Reference/Appendix/Instance configurations table.md#).|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
<Common request parameters>
&Action= DescribeInstanceConfig
&InstanceId= de5d88e34d004211
```

## Response example {#section_hjp_tkw_wbb .section}

```

"Config":"{"EvictionPolicy":"volatile-lru","hash-max-ziplist-entries":512,
"hash-max-ziplist-value":64,"list-max-ziplist-entries":512,
"list-max-ziplist-value":64,"set-max-intset-entries":512,
"zset-max-ziplist-entries":128,"zset-max-ziplist-value":64},
"RequestId":"59A58517-F8FF-44E5-B90F-F386DB3E4AB8"

```

