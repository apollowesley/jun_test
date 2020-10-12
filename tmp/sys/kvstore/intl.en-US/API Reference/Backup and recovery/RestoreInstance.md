# RestoreInstance {#reference_dz4_d1f_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to recover data to the master instance based on a backup set.

**Note:** This operation may have a high risk because the backup data will cover the instance data.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|Yes|For more information, see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: RestoreInstance.|
|InstanceId|String|Yes|Instance ID|
|BackupId|String|Yes|Backup result set ID|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameters\>|-|For more information, see [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
<Common request parameters>
&Action= RestoreInstance
&InstanceId= de5d88e34d004211
&BackupId=fdafasf111
```

## Response example {#section_hjp_tkw_wbb .section}

```

"RequestId" : "AFA391BF-808F-4DA6-80A2-A382108A0945"

```

