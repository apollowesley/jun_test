# ModifyBackupPolicy {#reference_epw_xw2_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to query a backup policy.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|Yes|For more information, see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: ModifyBackupPolicy.|
|InstanceId|String|Yes|Instance ID|
|PreferredBackupTime|String|Yes|Backup time, in the format of `HH:mmZ- HH:mm Z`|
|PreferredBackupPeriod|String|Yes|Backup cycle: -   Monday
-   Tuesday
-   Wednesday
-   Thursday
-   Friday
-   Saturday
-   Sunday

|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameters\>|-|For more information, see [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com、
<Common request parameters>
&Action= ModifyBackupPolicy
&InstanceId= de5d88e34d004211
&PreferredBackupTime=00:00Z-04:00Z
&PreferredBackupPeriod= Saturday
```

## Response example {#section_hjp_tkw_wbb .section}

```

"RequestId" : "5C97648D-C85F-4D58-A71F-7B6750856BF7”

```

