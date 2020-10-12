# DescribeCacheAnalysisReportList {#doc_api_R-kvstore_DescribeCacheAnalysisReportList .reference}

调用DescribeCacheAnalysisReportList查看Redis实例的缓存分析报告列表。

 **前提条件** 

-   Redis实例的引擎版本为4.0或以上；
-   Redis实例的小版本为最新，小版本是否需要升级请参见[如何确认Redis实例的小版本是不是最新版](~~129203~~)。

 **功能说明** 

云数据库Redis版的缓存分析功能在每日全量备份数据时对缓存结构进行分析，找出每个数据节点中占用空间最大的80个key并保存分析结果供您查看。

除了每天的自动分析，您还可以发起实时的缓存分析任务，可供选择的发起方法如下：

-   调用[CreateCacheAnalysisTask](~~128806~~)创建缓存分析任务。
-   在Redis控制台发起实时分析，操作步骤请参见[缓存分析](~~102093~~)。

**说明：** 

-   云数据库Redis版只会保存每天最新的一条分析结果，如您先通过手动操作进行了分析，当日稍晚时间系统进行了自动分析，系统会保留系统自动分析的结果，删除之前手动发起分析任务获得的结果。
-   系统自动进行缓存分析的时间由数据全量备份的时间决定，您可以在控制台的[备份与恢复](~~43886~~)页修改该时间。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeCacheAnalysisReportList&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DescribeCacheAnalysisReportList|系统规定参数，取值：DescribeCacheAnalysisReportList。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|Days|Integer|否|7|查询最近几天内的分析结果，默认查询最近7天的分析结果。

 **说明：** 如果查询时当日的自动分析尚未开始，且没有手动发起缓存分析，则本日没有记录。

 |
|NodeId|String|否|r-bp1xxxxxxxxxxxxx-db-0|集群版Redis实例的子节点ID。

 **说明：** 如果不设置将返回所有子节点的分析结果。

 |
|PageSize|Integer|否|30|每页返回的记录数，可选值：

 -   30
-   50
-   100

 默认值：30。

 |
|PageNumbers|Integer|否|1|需要返回的页码。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|InstanceId|String|1041xxxx|实例ID。

 |
|RequestId|String|743D0A03-52DE-4E6F-8D09-EC1414CFB1AD|请求ID。

 |
|DailyTasks| | |缓存分析报告列表。

 |
|Date|String|2019-08-01Z|缓存分析发起的日期。

 |
|Tasks| | |缓存分析任务详情。

 |
|NodeId|String|r-bp1xxxxxxxxxxxxx-db-0|集群版Redis实例的子节点ID。

 |
|StartTime|String|2019-08-01T19:08:49Z|缓存分析任务的开始时间。

 |
|Status|String|success|缓存分析任务的状态：

 -   success（成功）
-   running（进行中）

 |
|TaskId|String|1564657730|任务ID。

 |
|PageRecordCount|Integer|6|当前页显示的记录数。

 **说明：** 当前不返回该参数。

 |
|PageNumbers|Integer|1|当前页的页码。

 **说明：** 当前不返回该参数。

 |
|TotalRecordCount|Integer|6|所有页的记录总数。

 **说明：** 当前不返回该参数。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com
?Action=DescribeCacheAnalysisReportList
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeCacheAnalysisReportListResponse>
	  <InstanceId>1041xxxx</InstanceId>
	  <RequestId>BFB9478A-119E-4589-B02C-2D2EBE5E726F</RequestId>
	  <DailyTasks>
		    <DailyTask>
			      <Date>2019-08-03Z</Date>
			      <Tasks>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-0</NodeId>
					          <StartTime>2019-08-03T19:08:48Z</StartTime>
					          <TaskId>1564830528</TaskId>
				        </Task>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-1</NodeId>
					          <StartTime>2019-08-03T19:08:49Z</StartTime>
					          <TaskId>1564830529</TaskId>
				        </Task>
			      </Tasks>
		    </DailyTask>
		    <DailyTask>
			      <Date>2019-08-02Z</Date>
			      <Tasks>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-0</NodeId>
					          <StartTime>2019-08-02T19:08:55Z</StartTime>
					          <TaskId>1564744135</TaskId>
				        </Task>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-1</NodeId>
					          <StartTime>2019-08-02T19:08:55Z</StartTime>
					          <TaskId>1564744135</TaskId>
				        </Task>
			      </Tasks>
		    </DailyTask>
		    <DailyTask>
			      <Date>2019-08-05Z</Date>
			      <Tasks>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-0</NodeId>
					          <StartTime>2019-08-05T19:08:51Z</StartTime>
					          <TaskId>1565003331</TaskId>
				        </Task>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-1</NodeId>
					          <StartTime>2019-08-05T19:08:50Z</StartTime>
					          <TaskId>1565003330</TaskId>
				        </Task>
			      </Tasks>
		    </DailyTask>
		    <DailyTask>
			      <Date>2019-08-01Z</Date>
			      <Tasks>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-0</NodeId>
					          <StartTime>2019-08-01T19:08:49Z</StartTime>
					          <TaskId>1564657729</TaskId>
				        </Task>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-1</NodeId>
					          <StartTime>2019-08-01T19:08:50Z</StartTime>
					          <TaskId>1564657730</TaskId>
				        </Task>
			      </Tasks>
		    </DailyTask>
		    <DailyTask>
			      <Date>2019-08-04Z</Date>
			      <Tasks>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-0</NodeId>
					          <StartTime>2019-08-04T19:08:50Z</StartTime>
					          <TaskId>1564916930</TaskId>
				        </Task>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-1</NodeId>
					          <StartTime>2019-08-04T19:08:51Z</StartTime>
					          <TaskId>1564916931</TaskId>
				        </Task>
			      </Tasks>
		    </DailyTask>
		    <DailyTask>
			      <Date>2019-08-06Z</Date>
			      <Tasks>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-0</NodeId>
					          <StartTime>2019-08-06T19:08:48Z</StartTime>
					          <TaskId>1565089728</TaskId>
				        </Task>
				        <Task>
					          <Status>success</Status>
					          <NodeId>r-bp1xxxxxxxxxxxxx-db-1</NodeId>
					          <StartTime>2019-08-06T19:08:49Z</StartTime>
					          <TaskId>1565089729</TaskId>
				        </Task>
			      </Tasks>
		    </DailyTask>
	  </DailyTasks>
</DescribeCacheAnalysisReportListResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"BFB9478A-119E-4589-B02C-2D2EBE5E726F",
	"InstanceId":"1041xxxx",
	"DailyTasks":{
		"DailyTask":[
			{
				"Date":"2019-08-03Z",
				"Tasks":{
					"Task":[
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-0",
							"TaskId":"1564830528",
							"StartTime":"2019-08-03T19:08:48Z"
						},
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-1",
							"TaskId":"1564830529",
							"StartTime":"2019-08-03T19:08:49Z"
						}
					]
				}
			},
			{
				"Date":"2019-08-02Z",
				"Tasks":{
					"Task":[
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-0",
							"TaskId":"1564744135",
							"StartTime":"2019-08-02T19:08:55Z"
						},
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-1",
							"TaskId":"1564744135",
							"StartTime":"2019-08-02T19:08:55Z"
						}
					]
				}
			},
			{
				"Date":"2019-08-05Z",
				"Tasks":{
					"Task":[
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-0",
							"TaskId":"1565003331",
							"StartTime":"2019-08-05T19:08:51Z"
						},
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-1",
							"TaskId":"1565003330",
							"StartTime":"2019-08-05T19:08:50Z"
						}
					]
				}
			},
			{
				"Date":"2019-08-01Z",
				"Tasks":{
					"Task":[
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-0",
							"TaskId":"1564657729",
							"StartTime":"2019-08-01T19:08:49Z"
						},
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-1",
							"TaskId":"1564657730",
							"StartTime":"2019-08-01T19:08:50Z"
						}
					]
				}
			},
			{
				"Date":"2019-08-04Z",
				"Tasks":{
					"Task":[
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-0",
							"TaskId":"1564916930",
							"StartTime":"2019-08-04T19:08:50Z"
						},
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-1",
							"TaskId":"1564916931",
							"StartTime":"2019-08-04T19:08:51Z"
						}
					]
				}
			},
			{
				"Date":"2019-08-06Z",
				"Tasks":{
					"Task":[
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-0",
							"TaskId":"1565089728",
							"StartTime":"2019-08-06T19:08:48Z"
						},
						{
							"Status":"success",
							"NodeId":"r-bp1xxxxxxxxxxxxx-db-1",
							"TaskId":"1565089729",
							"StartTime":"2019-08-06T19:08:49Z"
						}
					]
				}
			}
		]
	}
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

