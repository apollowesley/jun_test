# DescribeMonitorItems {#doc_api_R-kvstore_DescribeMonitorItems .reference}

调用DescribeMonitorItems查询监控项列表。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=DescribeMonitorItems)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DescribeMonitorItems|系统规定参数，取值：DescribeMonitorItems。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|MonitorItems| | |监控参数列表。

 |
|└MonitorKey|String|select|监控项。

 |
|└Unit|String|Counts/s|监控项使用的单位。

 |
|RequestId|String|8BEB2618-9517-43F3-A233-E0B345125686|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com
?Action=DescribeMonitorItems
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeMonitorItemsResponse>
  <RequestId>8BEB2618-9517-43F3-A233-E0B345125686</RequestId>
  <MonitorItems>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>discard</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>zcount</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>select</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>sunionstore</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>zunionstore</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>del</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>zinterstore</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>echo</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>hscan</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>psubscribe</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>type</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>sinterstore</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>multi</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>randomkey</MonitorKey>
    </KVStoreMonitorItem>
    <KVStoreMonitorItem>
      <Unit>Counts/s</Unit>
      <MonitorKey>setex</MonitorKey>
    </KVStoreMonitorItem>
  </MonitorItems>
</DescribeMonitorItemsResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"8BEB2618-9517-43F3-A233-E0B345125686",
	"MonitorItems":{
		"KVStoreMonitorItem":[
			{
				"MonitorKey":"discard",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"zcount",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"select",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"sunionstore",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"zunionstore",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"del",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"zinterstore",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"echo",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"hscan",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"psubscribe",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"type",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"sinterstore",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"multi",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"randomkey",
				"Unit":"Counts/s"
			},
			{
				"MonitorKey":"setex",
				"Unit":"Counts/s"
			}
		]
	}
}
```

## 错误码 { .section}

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

