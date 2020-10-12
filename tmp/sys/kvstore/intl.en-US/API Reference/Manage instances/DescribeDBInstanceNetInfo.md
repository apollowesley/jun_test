# DescribeDBInstanceNetInfo {#reference_ih5_zt2_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

Query the classic network connection address of the instance.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameter\>|-|-|For more information, see [Public parameters](intl.en-US/API reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: DescribeDBInstanceNetInfo.|
|InstanceId|String|Yes|Instance ID \(globally unique\)|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameters\>|-|For more information, see [Public return parameters](intl.en-US/API reference/Common Parameters.md#section_rjr_zgp_wbb).|
|NetInfoItems|List|Composed of InstanceNetInfo parameters.|
|InstanceNetworkType|String|The network type of the instance.-   Private \(VPC private network\) 
-   Classic: Classic Network

|

|Name|Type|Description|
|----|----|-----------|
|ConnectionString|String|DNS connection string|
|IPAddress|String|IP address of the instance|
|IPType|String|IP type of the instance:-   Private \(VPC private network\)
-   Inner \(Reserved classic network IP address\)

|
|Port|String|Port information|
|VPCId|String|VPC ID|
|VSwitchId|String|VSwitch ID|
|ExpiredTime|String|Remaining time before expiration of the reserved classic network IP address. In seconds.|

## Examples {#section_d3l_4kw_wbb .section}

**Request example**

```

https://r-kvstore.aliyuncs.com.com/
? <Common request parameters>
&Action= DescribeDBInstanceNetInfo
&InstanceId=657e361a074646d5
```

**Response example**

```
 {
        "RequestId": "47651832-5882-4E35-BABF-97D01E06EB12",
        "InstanceNetworkType": "VPC",
        "NetInfoItems": {
            "InstanceNetInfo": [{
                "ConnectionString": "r-m5e6a11fb9fd3414.redis.rds.aliyuncs.com",
                "Port": "6379",
                "DBInstanceNetType": "Inner",
                "VPCId": "",
                "IPAddress": "10.146.182.164",
                "ExpiredTime": "2581948"
            }, {
                "ConnectionString": "r-m5e6a11fb9fd3414983.redis.rds.aliyuncs.com",
                "Port": "6379",
                "DBInstanceNetType": "Private",
                "VPCId": "vpc-m5edrscsrbmda2m46h4c7",
                "IPAddress": "192.168.100.29",
            }]
        }
    }
}
```

