# DescribeDBInstanceNetInfo {#reference_ih5_zt2_xdb .reference}

## 描述 {#section_z1z_2kw_wbb .section}

查看实例的经典网络访问地址。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|-|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeDBInstanceNetInfo。|
|InstanceId|String|是|实例 ID，全局唯一。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|NetInfoItems|List|由 InstanceNetInfo 组成的实例连接信息。|
|InstanceNetworkType|String|实例的网络类型。-   VPC：专有网络
-   Classic：经典网络

|

|名称|类型|描述|
|--|--|--|
|ConnectionString|String|DNS 连接串|
|IPAddress|String|IP 地址|
|IPType|String|IP 网络类型：-   Private（私网，VPC内网）
-   Inner（保留的经典网络 IP）

|
|Port|String|端口信息|
|VPCId|String|VPC ID|
|VSwitchId|String|交换机 ID|
|ExpiredTime|String|过期时间，即剩余有效时间，以秒为单位。|

## 示例 {#section_d3l_4kw_wbb .section}

**请求示例**

```

https://r-kvstore.aliyuncs.com.com/
?<公共请求参数>
&Action= DescribeDBInstanceNetInfo
&InstanceId=657e361a074646d5
```

**返回示例**

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

