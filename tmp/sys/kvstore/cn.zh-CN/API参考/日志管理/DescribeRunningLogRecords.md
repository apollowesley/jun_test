# DescribeRunningLogRecords {#doc_api_R-kvstore_DescribeRunningLogRecords .reference}

调用DescribeRunningLogRecords查询Redis实例的运行日志列表。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=DescribeRunningLogRecords)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DescribeRunningLogRecords|系统规定参数，取值：DescribeRunningLogRecords。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|需要查询的实例的ID。

 |
|StartTime|String|是|2018-12-03T07:01Z|查询开始时间，格式：YYYY-MM-DDTHH:mmZ。

 |
|EndTime|String|是|2018-12-03T08:01Z|查询结束时间，必须大于查询开始时间，格式：YYYY-MM-DDTHH:mmZ。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|NodeId|String|否|r-bp1xxxxxxxxxxxxx-db-0|集群子节点ID，查询集群实例的特定子节点时需传入此参数。

 |
|DBName|String|否|demo|数据库名称。

 |
|RoleType|String|否|master|节点角色，默认为master。

 |
|PageSize|Integer|否|30|每页显示的最大日志条目数。

 |
|PageNumber|Integer|否|1|当前显示的页码。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|Engine|String|Redis|数据库类型。

 |
|InstanceId|String|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|Items| | |日志详情。

 |
|└Category|String|NETWORK|日志类别。

 |
|└Content|String|CONFIG REWRITE executed with success.|日志内容。

 |
|└CreateTime|String|2018-12-03T07:07:30Z|日志生成时间。

 |
|└Level|String|WARNING|日志级别。

 |
|PageNumber|Integer|1|当前显示的页码。

 |
|PageRecordCount|Integer|5|当前页显示的记录数。

 |
|PageSize|Integer|30|每页最大记录数。

 |
|RequestId|String|093B8579-9264-43A0-ABA9-AA8621FF|请求ID。

 |
|StartTime|String|2018-12-03T07:01Z|查询开始时间。

 |
|TotalRecordCount|Integer|5|总记录数。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeRunningLogRecords
&InstanceId=r-bp1xxxxxxxxxxxxx
&StartTime=2018-12-03T07:01Z
&EndTime=2018-12-03T08:01Z
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeRunningLogRecordsResponse>
  <Items>
    <LogRecords>
      <CreateTime>2019-03-22T08:43:24Z</CreateTime>
      <Level>WARNING</Level>
      <Content>CONFIG REWRITE executed with success.</Content>
    </LogRecords>
  </Items>
  <PageNumber>1</PageNumber>
  <TotalRecordCount>1</TotalRecordCount>
  <PageSize>30</PageSize>
  <InstanceId>r-bp1xxxxxxxxxxxxx</InstanceId>
  <RequestId>AAFCDED7-3FE1-49B2-9E9F-2D66E3D041A8</RequestId>
  <StartTime>2019-03-20T07:01Z</StartTime>
  <PageRecordCount>1</PageRecordCount>
</DescribeRunningLogRecordsResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"Items":{
		"LogRecords":[
			{
				"CreateTime":"2019-03-22T08:43:24Z",
				"Level":"WARNING",
				"Content":"CONFIG REWRITE executed with success."
			}
		]
	},
	"TotalRecordCount":1,
	"PageNumber":1,
	"PageSize":30,
	"RequestId":"AAFCDED7-3FE1-49B2-9E9F-2D66E3D041A8",
	"InstanceId":"r-bp1xxxxxxxxxxxxx",
	"StartTime":"2019-03-20T07:01Z",
	"PageRecordCount":1
}
```

## 错误码 { .section}

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

