# ModifyInstanceNetExpireTime {#doc_api_R-kvstore_ModifyInstanceNetExpireTime .reference}

若Redis实例之前执行过由经典网络向VPC网络切换，并保留了经典网络连接地址，则可调用ModifyInstanceNetExpireTime延长经典网络连接地址的保存时间。

该API对应的控制台操作请参见[修改原经典网络地址的使用期限](~~60062~~)。

**说明：** 将实例从经典网络切换到VPC网络的方法请参见[SwitchNetwork](~~61005~~)。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceNetExpireTime)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|ModifyInstanceNetExpireTime|系统规定参数，取值：ModifyInstanceNetExpireTime。

 |
|ClassicExpiredDays|Integer|是|14|延长经典网络连接地址的保留时间。取值：14、30、60或120，单位为天。

 |
|ConnectionString|String|是|r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com|实例的经典网络连接地址。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|InstanceId|String|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|NetInfoItems| | |经典网络连接地址延长时间的详情。

 |
|└ConnectionString|String|r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com|经典网络连接地址。

 |
|└DBInstanceNetType|String|Classic|网络类型。

 |
|└ExpiredTime|String|2019-08-01T09:29:18Z|经典网络地址的过期时间。

 |
|└IPAddress|String|xxx.xxx.xxx.222|实例在经典网络中的IP地址。

 |
|└Port|String|6379|Redis服务端口。

 |
|RequestId|String|9C4AF387-1EA3-4C84-8013-3F6B973EDDF5|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceNetExpireTime
&InstanceId=r-bp1xxxxxxxxxxxxx
&ConnectionString=r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com
&ClassicExpiredDays=30
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyInstanceNetExpireTimeResponse>
  <InstanceId>r-bp1xxxxxxxxxxxxx</InstanceId>
  <RequestId>9C4AF387-1EA3-4C84-8013-3F6B973EDDF5</RequestId>
  <NetInfoItems>
    <NetInfoItem>
      <ConnectionString>r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com</ConnectionString>
      <Port>6379</Port>
      <DBInstanceNetType>Classic</DBInstanceNetType>
      <IPAddress>xxx.xxx.xxx.222</IPAddress>
      <ExpiredTime>2019-08-01T09:29:18Z</ExpiredTime>
    </NetInfoItem>
  </NetInfoItems>
</ModifyInstanceNetExpireTimeResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"9C4AF387-1EA3-4C84-8013-3F6B973EDDF5",
	"InstanceId":"r-bp1xxxxxxxxxxxxx",
	"NetInfoItems":{
		"NetInfoItem":[
			{
				"DBInstanceNetType":"Classic",
				"Port":"6379",
				"ConnectionString":"r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com",
				"IPAddress":"xxx.xxx.xxx.222",
				"ExpiredTime":"2019-08-01T09:29:18Z"
			}
		]
	}
}
```

## 错误码 { .section}

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

