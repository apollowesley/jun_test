# ModifySecurityIps {#reference_hby_zv2_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

Set the instance's IP whiteable list, and you can select the allowed ECs IP Access instance.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|Yes|For more information, see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: ModifySecurityIps.|
|InstanceId|String|Yes|Instance ID \(globally unique\)|
|SecurityIps|String|Yes|IP address whitelist to be modified|
|SecurityIpGroupName|String|Yes|Whitelist group|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameters\>|-|For more information, see [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
<Common request parameters>
&Action= ModifySecurityIps
&InstanceId=657e361a074646d5
&SecurityIps=1.1.1.1，2.2.2.2
&SecurityIpGroupName=testgroup
```

## Response example {#section_hjp_tkw_wbb .section}

```

"RequestId" : "AAAF99B1-69ED-4E80-8CD5-272C09E46ACF"

```

