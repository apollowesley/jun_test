# DescribeDBInstanceNetInfo {#doc_api_R-kvstore_DescribeDBInstanceNetInfo .reference}

调用DescribeDBInstanceNetInfo查看Redis实例的网络信息。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeDBInstanceNetInfo&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|Action|String|否|DescribeDBInstanceNetInfo|系统规定参数，取值：DescribeDBInstanceNetInfo。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|InstanceNetworkType|String|CLASSIC|网络类型：

 -   CLASSIC（经典网络）
-   VPC（专有网络）

 |
|NetInfoItems| | |网络信息的集合。

 |
|ConnectionString|String|r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com|实例的连接地址。

 |
|DBInstanceNetType|String|1|该网络信息所属的网络类型：

 -   0（公网）
-   1（经典网络）
-   2（VPC网络）

 |
|ExpiredTime|String|5183779|Redis实例经典网络地址的有效时间，单位：秒。

 |
|IPAddress|String|xxx.xxx.xxx.201|IP地址。

 |
|IPType|String|Inner|IP地址的网络类型：

 -   Public（公网）
-   Inner（经典网络）
-   Private（VPC网络）

 |
|Port|String|6379|Redis服务端口。

 |
|Upgradeable|String|0|经典网络连接地址的过期时间，即剩余有效时间，以秒为单位。0表示不会过期。

 |
|VPCId|String|vpc-bp1cxxxxxxxxxxxxxxxxx|实例所属的专有网络（VPC）的ID。

 |
|VPCInstanceId|String|r-bp1xxxxxxxxxxxxx|实例ID，仅在DBInstanceNetType为2（VPC网络）的网络信息中返回。

 |
|VSwitchId|String|vsw-4gxxxxxxxxxxxxxxxxxxx|虚拟交换机的ID。

 |
|RequestId|String|314C4FB3-4256-424F-9AD9-1A6B3444160A|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeDBInstanceNetInfo
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeDBInstanceNetInfoResponse>
      <RequestId>E4866AEC-CE71-4156-8BAA-A078D5B8EF3F</RequestId>
	  <InstanceNetworkType>VPC</InstanceNetworkType>
	  <NetInfoItems>
		    <InstanceNetInfo>
			      <DBInstanceNetType>1</DBInstanceNetType>
			      <Port>6379</Port>
			      <ConnectionString>r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com</ConnectionString>
			      <VPCInstanceId></VPCInstanceId>
			      <VPCId></VPCId>
			      <IPAddress>xxx.xxx.xxx.201</IPAddress>
			      <IPType>Inner</IPType>
			      <Upgradeable>0</Upgradeable>
			      <ExpiredTime>5183779</ExpiredTime>
		    </InstanceNetInfo>
		    <InstanceNetInfo>
			      <DBInstanceNetType>0</DBInstanceNetType>
			      <Port>6379</Port>
			      <ConnectionString>r-bp1xxxxxxxxxxxxxxx.redis.rds.aliyuncs.com</ConnectionString>
			      <VPCInstanceId></VPCInstanceId>
			      <VPCId></VPCId>
			      <IPAddress>xxx.xxx.xxx.146</IPAddress>
			      <IPType>Public</IPType>
			      <Upgradeable>0</Upgradeable>
		    </InstanceNetInfo>
		    <InstanceNetInfo>
			      <DBInstanceNetType>2</DBInstanceNetType>
			      <Port>6379</Port>
			      <ConnectionString>r-bp1xxxxxxxxxxxxxxx.redis.rds.aliyuncs.com</ConnectionString>
			      <VPCInstanceId>r-bp1xxxxxxxxxxxxx</VPCInstanceId>
			      <VPCId>vpc-bp1cxxxxxxxxxxxxxxxxx</VPCId>
			      <IPAddress>xxx.xxx.xxx.245</IPAddress>
			      <IPType>Private</IPType>
			      <Upgradeable>0</Upgradeable>
		    </InstanceNetInfo>
	  </NetInfoItems>
</DescribeDBInstanceNetInfoResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"E4866AEC-CE71-4156-8BAA-A078D5B8EF3F",
	"InstanceNetworkType":"VPC",
	"NetInfoItems":{
		"InstanceNetInfo":[
			{
				"ConnectionString":"r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com",
				"Port":"6379",
				"DBInstanceNetType":"1",
				"VPCId":"",
				"VPCInstanceId":"",
				"IPAddress":"xxx.xxx.xxx.201",
				"IPType":"Inner",
				"Upgradeable":"0",
				"ExpiredTime":"5183779"
			},
			{
				"ConnectionString":"r-bp1xxxxxxxxxxxxxxx.redis.rds.aliyuncs.com",
				"Port":"6379",
				"DBInstanceNetType":"0",
				"VPCId":"",
				"VPCInstanceId":"",
				"IPAddress":"xxx.xxx.xxx.146",
				"IPType":"Public",
				"Upgradeable":"0"
			},
			{
				"ConnectionString":"r-bp1xxxxxxxxxxxxxxx.redis.rds.aliyuncs.com",
				"Port":"6379",
				"DBInstanceNetType":"2",
				"VPCId":"vpc-bp1cxxxxxxxxxxxxxxxxx",
				"VPCInstanceId":"r-bp1xxxxxxxxxxxxx",
				"IPAddress":"xxx.xxx.xxx.245",
				"IPType":"Private",
				"Upgradeable":"0"
			}
		]
	}
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|InvalidStartTime.Malformed|The Specified parameter StartTime is not valid.|开始时间验证失败，时间格式应该为gmt时间例如2011-06-11T16:00Z|
|400|InvalidEndTime.Malformed|The Specified parameter EndTime is not valid.|结束时间验证失败，时间格式应该为gmt时间例如2011-06-11T16:00Z|

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

