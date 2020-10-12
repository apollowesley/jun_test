# DescribeSecurityIps {#doc_api_R-kvstore_DescribeSecurityIps .reference}

调用DescribeSecurityIps查询允许访问Redis实例的IP名单。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeSecurityIps&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|Action|String|否|DescribeSecurityIps|系统规定参数，取值：DescribeSecurityIps。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|SecurityIpGroups| | |实例的IP白名单信息。

 |
|SecurityIpGroupAttribute|String|""|默认为空。用于区分不同的属性值，控制台将不显示该值为hidden的白名单分组。

 |
|SecurityIpGroupName|String|default|IP白名单分组的名称。

 |
|SecurityIpList|String|100.xxx.xxx.xxx/24,10.xxx.xxx.xxx|IP白名单分组下的IP列表，最多1000个。

 |
|RequestId|String|EFC9161F-15E3-4A6E-8A99-C09916D1F464|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeSecurityIps
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeSecurityIpsResponse>
      <SecurityIpGroups>
        <SecurityIpGroup>
              <SecurityIpList>127.0.0.1</SecurityIpList>
              <SecurityIpGroupAttribute></SecurityIpGroupAttribute>
              <SecurityIpGroupName>default</SecurityIpGroupName>
        </SecurityIpGroup>
        <SecurityIpGroup>
              <SecurityIpList>100.xxx.xxx.xxx/24,10.xxx.xxx.xxx</SecurityIpList>
              <SecurityIpGroupAttribute>hidden</SecurityIpGroupAttribute>
              <SecurityIpGroupName>rds_replica_group</SecurityIpGroupName>
        </SecurityIpGroup>
      </SecurityIpGroups>
      <RequestId>EFC9161F-15E3-4A6E-8A99-C09916D1F464</RequestId>
</DescribeSecurityIpsResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"SecurityIpGroups":{
		"SecurityIpGroup":[
			{
				"SecurityIpList":"127.0.0.1",
				"SecurityIpGroupAttribute":"",
				"SecurityIpGroupName":"default"
			},
			{
				"SecurityIpList":"100.xxx.xxx.xxx/24,10.xxx.xxx.xxx",
				"SecurityIpGroupAttribute":"hidden",
				"SecurityIpGroupName":"rds_replica_group"
			}
		]
	},
	"RequestId":"EFC9161F-15E3-4A6E-8A99-C09916D1F464"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

