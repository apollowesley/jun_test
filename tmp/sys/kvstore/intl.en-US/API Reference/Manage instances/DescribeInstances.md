# DescribeInstances {#reference_d5w_1df_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to query one or multiple instances under an account.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|-|For more information, see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|The API name. Value: DescribeInstances.|
|InstanceIds|String|No|Instance ID \(globally unique\). This parameter is required when an instance ID is specified. To query multiple instance IDs, separate the IDs by comma \(,\). If the parameter value is left blank, all instances under the account are queried by default.|
|InstanceStatus|String|No| Filter instances to be returned by the instance status:

 -   Normal
-   Creating
-   Changing
-   Inactive

 |
|ChargeType|String|No|Filter instances to be returned by the payment options:-   PrePaid
-   PostPaid

 |
|RegionId|String|Yes|Call DescribeRegions to query the RegionId.|
|InstanceType|String|No|Filter instances by the engine type:-   Memcache
-   Redis

|
|PageNumber|Integer|No|Page number of the instance status list. Initial value: 1. Default value: 1.|
|PageSize|Integer|No|Number of lines per page in paging query. Maximum value: 50. Default value: 10.|
|NetworkType|String|No|Filter instances by network type:-   CLASSIC
-   VPC

|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common response parameters\>|-|For more information, see [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|
|Instances|List|Array composed of Instances|
|TotalCount|Integer|Total number of instances|
|PageNumber|Integer|Page number of the instance list|
|PageSize|Integer|Number of lines per page set during input|

## Instance parameter structure {#section_f2s_tpw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|InstanceId|String|Instance ID \(globally unique\)|
|InstanceName|String|Instance name|
|Capacity|Long|Capacity of the applied ApsaraDB for Redis instance. Unit: MB.|
|InstanceClass|String|Instance type|
|Bandwidth|Long|Instance bandwidth limit. Unit: Mbit/s.|
|Connections|Long|Instance connection quantity limit. Unit: count.|
|ConnectionDomain|String|Instance connection domain \(only Intranet access supported\).|
|Port|Int|Connection port|
|Username|String|Connection user name|
|RegionId|String|Region of the applied ApsaraDB for Redis instance|
|ZoneId|String|Lower-level zone ID of the RegionId|
|InstanceStatus|String|Instance status:-   Normal 
-   Creating
-   Changing
-   Inactive 
-   Transforming
-   BackupRecovering
-   MinorVersionUpgrading

|
|ChargeType|String|Billing method. Supported values: PrePaid and PostPaid.|
|CreateTime|String|Instance creation time. The time format follows the ISO8601 notation, and the UTC time is used  in the format of `YYYY-MM-DDThh:mm:ssZ`.|
|EndTime|String|Expiration time for a pre-paid instance. The time format follows the ISO8601 notation, and the UTC time is used  in the format of  `YYYY-MM-DDThh:mm:ssZ`。|
|NetworkType|String|Network type. Supported values: CLASSIC and VPC.|
|VpcId|String| VPC ID|
|VSwitchId|String|VSwitch ID|
|Privateipaddr|String|Private IP address|

**Note:** In consideration of the historical compatibility, some returned fields \(such as Config\) of the function are not mentioned in this document. Alibaba Cloud will delete these fields gradually in the future. In this case, do not rely on the returned fields not involved in this document when calling the APls.

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com.com/
<Common request parameters>
&Action=DescribeInstances
&PageNumber=1
&PageSize=10
&InstanceId=de5d88e34d004211
```

## Response example {#section_hjp_tkw_wbb .section}

```

    "Instances" : {
        "Instance" : [{
                "Bandwidth" : 128,
                "Capacity" : 512,
                "ConnectionDomain" : "de5d88e34d004211.m.cnhzalicm10pub001.r-kvstore.aliyuncs.com.com",
                "Connections" : 300,
                "ZoneId" : "cn-qingdao-b",
                "InstanceId" : "de5d88e34d004211",
                "Instancename": "wl123456 ",
                "InstanceStatus" : "Available",
                 "InstanceClass" : "redis.master.mid.default",
                "Port" : 11211,
                "RegionId" : "cn-qingdao",
                "UserName" : "de5d88e34d004211"
                “NetworkType”:”CLASSSIC”
“ChargeType”:” PostPaid”
            
        
    
    "PageNumber" : 1,
    "PageSize" : 10,
    "TotalCount" : 1,
    "RequestId" : "969D0A1D-C91A-4837-9F70-49785DF9BDCE"

```

