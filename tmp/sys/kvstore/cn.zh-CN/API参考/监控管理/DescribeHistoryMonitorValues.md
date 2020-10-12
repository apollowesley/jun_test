# DescribeHistoryMonitorValues {#doc_api_R-kvstore_DescribeHistoryMonitorValues .reference}

调用DescribeHistoryMonitorValues查看Redis实例的历史监控信息。

该API对应的控制台操作请参见[性能监控](~~43887~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeHistoryMonitorValues&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|StartTime|String|是|2018-10-18T12:00:00Z|历史监控开始时间点；采用ISO8601表示法，并使用UTC时间。格式为：YYYY-MM-DDThh:mm:ssZ。

 |
|EndTime|String|是|2018-10-19T12:00:00Z|历史监控结束时间点；采用ISO8601表示法，并使用UTC时间。格式为：YYYY-MM-DDThh:mm:ssZ。结束时间必须晚于开始时间。

 |
|IntervalForHistory|String|是|01m|历史监控数据间隔，单位m（分钟），唯一值`01m`。

 |
|Action|String|否|DescribeHistoryMonitorValues|系统规定参数，取值：DescribeHistoryMonitorValues。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|MonitorKeys|String|否|hscan|监控项。可调用[DescribeMonitorItems](~~61106~~)查询。

 |
|NodeId|String|否|r-bp1xxxxxxxxxxxxx-db-0\#6xxxxx5|传入集群实例中特定子节点的ID查询该节点的监控信息。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|MonitorHistory|String|\{"2018-07-09T12:00:13Z": \{"quotaMemory": 1073741824,"UsedMemory": 5823152\},"2018-07-09T12:13:15Z": \{"quotaMemory": 1073741824,"UsedMemory": 5823152\},"2018-07-09T12:14:15Z": \{"quotaMemory": 1073741824,"UsedMemory": 5823152\}\}|以JSON格式返回的监控信息。

 **说明：** 为了提高数据传输效率，只有非0的监控数据才会返回，其余未显示的监控数据均为默认值0。

 |
|RequestId|String|F0997EE8-F4C2-4503-9168-85177ED78C70|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeHistoryMonitorValues
&InstanceId=r-j6cxxxxxxxxxx3d4
&StartTime=2018-10-18T12:00:00Z
&EndTime=2018-10-19T12:00:00Z
&IntervalForHistory=01m
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeHistoryMonitorValuesResponse>
 <monitorHistory>
   <2018-07-09T12:00:13Z>
     <quotaMemory>1073741824</quotaMemory>
     <UsedMemory>5823152</UsedMemory>
   </2018-07-09T12:00:13Z>
   <2018-07-09T12:13:15Z>
     <quotaMemory>1073741824</quotaMemory>
     <UsedMemory>5823152</UsedMemory>
   </2018-07-09T12:13:15Z>
   <2018-07-09T12:14:15Z>
     <quotaMemory>1073741824</quotaMemory>
     <UsedMemory>5823152</UsedMemory>
   </2018-07-09T12:14:15Z>
 </monitorHistory>
 <requestId>F0997EE8-F4C2-4503-9168-85177ED78C70</requestId>
</DescribeHistoryMonitorValuesResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"monitorHistory":{
		"2018-07-09T12:13:15Z":{
			"quotaMemory":1073741824,
			"UsedMemory":5823152
		},
		"2018-07-09T12:14:15Z":{
			"quotaMemory":1073741824,
			"UsedMemory":5823152
		},
		"2018-07-09T12:00:13Z":{
			"quotaMemory":1073741824,
			"UsedMemory":5823152
		}
	},
	"requestId":"F0997EE8-F4C2-4503-9168-85177ED78C70"
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|InvalidStartTime.Malformed|The Specified parameter "StartTime" is not valid.|开始时间验证失败，时间格式应该为gmt时间例如，2011-06-11T16:00Z|
|400|InvalidEndTime.Malformed|The Specified parameter "EndTime" is not valid.|结束时间验证失败，时间格式应该为gmt时间例如，2011-06-11T16:00Z|

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

