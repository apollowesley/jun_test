# ModifyInstanceConfig {#reference_j1k_m22_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to change the configuration parameters of an instance.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Public request parameter\>|-|Yes|See [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: ModifyInstanceConfig.|
|InstanceId|String|Yes|Instance ID|
|Config|String|Yes|Instance configuration parameter \(JSON String\). For more information, see [Instance configurations table](intl.en-US/API Reference/Appendix/Instance configurations table.md#).|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Public return parameter\>|-|See also.|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
<Common request parameters>
&Action= ModifyInstanceConfig
&InstanceId= de5d88e34d004211
&Config={"hash-max-ziplist-entries":"512"}
```

## Response example {#section_hjp_tkw_wbb .section}

```

"RequestId":"59A58517-F8FF-44E5-B90F-F386DB3E4AB8"

```

