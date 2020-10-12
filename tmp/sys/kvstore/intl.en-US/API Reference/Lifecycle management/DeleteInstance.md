# DeleteInstance {#reference_ucs_hyx_wdb .reference}

## Description {#section_z1z_2kw_wbb .section}

Requirements for interface instance call & release are as follows:

-   The current status of instance: running.
-   Not artificially locked.

**Note:** A subscribed instance cannot be deleted. It is only released at expiration.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required or not|Description|
|----|----|---------------|-----------|
|<Public request parameters\>|-|Yes|For more information, see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: DeleteInstance.|
|InstanceId|String|Yes|Instance id \(globally unique\)|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Public return parameters\>|-|For more information, see [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
<Public request parameters>
&Action=DeleteInstance
&InstanceId=657e361a074646d5
```

## Response example {#section_hjp_tkw_wbb .section}

```

"RequestId" : "C2225574-1D93-4F8A-B1D5-39FCBAA40660"

```

