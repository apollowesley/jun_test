# ModifyInstanceSpec {#reference_fmf_4yx_wdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to change the instance type. For more information about the instance types, see Instance specifications.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|Yes|See [EN-US\_TP\_3172.md\#section\_hph\_dhp\_wbb](intl.en-US/API reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Operation API name. Value: ModifyInstanceSpec.|
|InstanceId|String|Yes|Instance ID \(globally unique\)|
|InstanceClass|String|Yes|Type of the applied ApsaraDB for Redis instance. For more information, see Instance specifications.|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameters\>|-|See [EN-US\_TP\_3172.md\#section\_rjr\_zgp\_wbb](intl.en-US/API reference/Common Parameters.md#section_rjr_zgp_wbb).|
|Orderid|String|Order ID.|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
<Common request parameters>
&Action=ModifyInstanceSpec
&InstanceId=736538d0a6894665
&InstanceClass= redis.master.mid.default
```

## Response example {#section_hjp_tkw_wbb .section}

```

"OrderId" : "201294900260011"
"RequestId" : "283746AF-82B3-4BFF-88CC-BF34CDE2732"

```

