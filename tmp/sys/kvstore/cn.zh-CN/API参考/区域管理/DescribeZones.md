# DescribeZones {#doc_api_R-kvstore_DescribeZones .reference}

调用DescribeZones查询支持Redis的可用区。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeZones&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|否|DescribeZones|系统规定参数，取值：DescribeZones。

 |
|AcceptLanguage|String|否|en-US|返回值的语言，取值：

 -   zh-CN（中文）
-   en-US（英文）
-   ja（日文）

 **说明：** 默认值：zh-CN。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|Zones| | |可用区的集合。

 |
|Disabled|Boolean|false|当前可用区是否可创建Redis实例。

 |
|IsRds|Boolean|true|是否属RDS管控，Redis中该参数的值为true。

 |
|RegionId|String|cn-huhehaote|该地域的ID。

 |
|SwitchNetwork|Boolean|true|是否支持将Redis实例从经典网络切换为VPC网络：

 -   True（支持）
-   False（不支持）

 |
|ZoneId|String|cn-huhehaote-b|该地域下的某可用区的ID。

 |
|ZoneName|String|呼和浩特 可用区B|该地域下的某可用区的名称。

 |
|RequestId|String|1D42F072-72FE-4DC4-BB8E-64B1D298182E|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeZones
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeZonesResponse>
      <RequestId>1D42F072-72FE-4DC4-BB8E-64B1D298182E</RequestId>
          <Zones>
            <KVStoreZone>
                  <ZoneName>呼和浩特 可用区B</ZoneName>
                  <IsRds>true</IsRds>
                  <Disabled>false</Disabled>
                  <RegionId>cn-huhehaote</RegionId>
                  <ZoneId>cn-huhehaote-b</ZoneId>
                  <SwitchNetwork>true</SwitchNetwork>
                </KVStoreZone>
            <KVStoreZone>
                  <ZoneName>呼和浩特 可用区A</ZoneName>
                  <IsRds>true</IsRds>
                  <Disabled>false</Disabled>
                  <RegionId>cn-huhehaote</RegionId>
                  <ZoneId>cn-huhehaote-a</ZoneId>
                  <SwitchNetwork>true</SwitchNetwork>
            </KVStoreZone>
          </Zones>
</DescribeZonesResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"1D42F072-72FE-4DC4-BB8E-64B1D298182E",
	"Zones":{
		"KVStoreZone":[
			{
				"ZoneName":"呼和浩特 可用区B",
				"IsRds":true,
				"RegionId":"cn-huhehaote",
				"Disabled":false,
				"ZoneId":"cn-huhehaote-b",
				"SwitchNetwork":true
			},
			{
				"ZoneName":"呼和浩特 可用区A",
				"IsRds":true,
				"RegionId":"cn-huhehaote",
				"Disabled":false,
				"ZoneId":"cn-huhehaote-a",
				"SwitchNetwork":true
			}
		]
	}
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

