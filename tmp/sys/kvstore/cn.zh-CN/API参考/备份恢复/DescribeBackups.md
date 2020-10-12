# DescribeBackups {#doc_api_R-kvstore_DescribeBackups .reference}

调用DescribeBackups查询备份文件的详细信息。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=DescribeBackups)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DescribeBackups|系统规定参数，取值：DescribeBackups。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|StartTime|String|是|2019-03-11T10:00Z|查询开始时间，例如：`2018-12-21T18:10Z`。

 |
|EndTime|String|是|2019-03-14T18:00Z|查询结束时间，例如：`2018-12-28T18:10Z`。

 |
|BackupId|Integer|否|111111111|备份文件的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|PageSize|Integer|否|30|每页最大记录数。

 |
|PageNumber|Integer|否|1|页码，大于0，且不超过Integer的最大值。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|TotalCount|Integer|5|备份总个数。

 |
|PageNumber|Integer|1|页码。

 |
|Backups| | |由Backup组成的集合，显示备份文件的详细信息。

 |
|└BackupDBNames|String|all|备份的DB。

 |
|└BackupDownloadURL|String|https://rdsbak-hk45-v2.oss-cn-hongkong.aliyuncs.com/xxxxxxxxxxx|备份文件的外网下载地址。

 |
|└BackupEndTime|String|2019-03-14T05:31:13Z|备份结束时间。

 |
|└BackupId|Integer|111111111|备份文件ID。

 |
|└BackupIntranetDownloadURL|String|https://rdsbak-hk45-v2.oss-cn-hongkong.aliyuncs.com/xxxxxxxxxxx|备份文件的内网下载地址。

 **说明：** 您可以在与该Redis实例连通的ECS（二者需属于同地域的经典网络或者在同一VPC内）上使用该地址下载目标备份文件。

 |
|└BackupMethod|String|Physical|备份方法：

 -   Logical（逻辑备份）
-   Physical（物理备份）

 |
|└BackupMode|String|Automated|备份模式：

 -   Automated（自动备份）
-   Manual（手动触发备份）

 |
|└BackupSize|Long|1024|备份文件大小。

 |
|└BackupStartTime|String|2019-03-14T05:28:50Z|备份开始时间。

 |
|└BackupStatus|String|Success|备份状态：

 -   Success（成功）
-   Failed（失败）

 |
|└BackupType|String|FullBackup|备份类型：

 -   FullBackup（全量备份）
-   IncrementalBackup（增量备份）

 |
|└EngineVersion|String|4.0|引擎版本，即Redis实例的大版本。

 |
|└NodeInstanceId|String|r-bp1xxxxxxxxxxxxx|节点ID。

 |
|PageSize|Integer|30|每页最大记录数。

 |
|RequestId|String|963C20F0-7CE1-4591-AAF3-6F3CD1CEB2F5|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeBackups
&InstanceId=r-bp1xxxxxxxxxxxxx
&PagSize=30
&PageNumber=1
&StartTime=2019-03-11T10:00Z
&EndTime=2019-03-14T18:00Z
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeBackupsResponse>
  <PageNumber>1</PageNumber>
  <TotalCount>3</TotalCount>
  <PageSize>30</PageSize>
  <RequestId>6D7D871F-8A6B-40C2-85C7-3632D139EAFD</RequestId>
  <Backups>
    <Backup>
      <BackupIntranetDownloadURL>http://rdsbak-hk45-v2.oss-cn-hongkong-internal.aliyuncs.com/xxxxxxxxxxx</BackupIntranetDownloadURL>
      <BackupType>FullBackup</BackupType>
      <BackupEndTime>2019-03-14T05:31:13Z</BackupEndTime>
      <BackupMethod>Physical</BackupMethod>
      <BackupId>336077354</BackupId>
      <BackupStartTime>2019-03-14T05:28:50Z</BackupStartTime>
      <BackupDownloadURL>https://rdsbak-hk45-v2.oss-cn-hongkong.aliyuncs.com/xxxxxxxxxxx</BackupDownloadURL>
      <BackupDBNames>all</BackupDBNames>
      <NodeInstanceId>r-bp1xxxxxxxxxxxxx</NodeInstanceId>
      <BackupMode>Automated</BackupMode>
      <BackupStatus>Success</BackupStatus>
      <BackupSize>1024</BackupSize>
      <EngineVersion>4.0</EngineVersion>
    </Backup>
    <Backup>
      <BackupIntranetDownloadURL>http://rdsbak-hk45-v2.oss-cn-hongkong-internal.aliyuncs.com/xxxxxxxxxxx</BackupIntranetDownloadURL>
      <BackupType>FullBackup</BackupType>
      <BackupEndTime>2019-03-13T05:31:09Z</BackupEndTime>
      <BackupMethod>Physical</BackupMethod>
      <BackupId>335538809</BackupId>
      <BackupStartTime>2019-03-13T05:28:46Z</BackupStartTime>
      <BackupDownloadURL>http://rdsbak-hk45-v2.oss-cn-hongkong-internal.aliyuncs.com/xxxxxxxxxxx</BackupDownloadURL>
      <BackupDBNames>all</BackupDBNames>
      <NodeInstanceId>r-bp1xxxxxxxxxxxxx</NodeInstanceId>
      <BackupMode>Automated</BackupMode>
      <BackupStatus>Success</BackupStatus>
      <BackupSize>1024</BackupSize>
      <EngineVersion>4.0</EngineVersion>
    </Backup>
    <Backup>
      <BackupIntranetDownloadURL>http://rdsbak-hk45-v2.oss-cn-hongkong-internal.aliyuncs.com/xxxxxxxxxxx</BackupIntranetDownloadURL>
      <BackupType>FullBackup</BackupType>
      <BackupEndTime>2019-03-12T05:31:14Z</BackupEndTime>
      <BackupMethod>Physical</BackupMethod>
      <BackupId>335001607</BackupId>
      <BackupStartTime>2019-03-12T05:28:52Z</BackupStartTime>
      <BackupDownloadURL>https://rdsbak-hk45-v2.oss-cn-hongkong.aliyuncs.com/http://rdsbak-hk45-v2.oss-cn-hongkong-internal.aliyuncs.com/xxxxxxxxxxx</BackupDownloadURL>
      <BackupDBNames>all</BackupDBNames>
      <NodeInstanceId>r-bp1xxxxxxxxxxxxx</NodeInstanceId>
      <BackupMode>Automated</BackupMode>
      <BackupStatus>Success</BackupStatus>
      <BackupSize>1024</BackupSize>
      <EngineVersion>4.0</EngineVersion>
    </Backup>
  </Backups>
</DescribeBackupsResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"DescribeBackupsResponse":{
		"PageNumber":"1",
		"TotalCount":"3",
		"PageSize":"30",
		"RequestId":"6D7D871F-8A6B-40C2-85C7-3632D139EAFD",
		"Backups":{
			"Backup":[
				{
					"BackupIntranetDownloadURL":"http://rdsbak-hk45-v2.oss-cn-hongkong-internal.aliyuncs.com/xxxxxxxxxxx",
					"BackupType":"FullBackup",
					"BackupEndTime":"2019-03-14T05:31:13Z",
					"BackupMethod":"Physical",
					"BackupId":"336077354",
					"BackupStartTime":"2019-03-14T05:28:50Z",
					"BackupDownloadURL":"https://rdsbak-hk45-v2.oss-cn-hongkong.aliyuncs.com/xxxxxxxxxxx",
					"BackupDBNames":"all",
					"NodeInstanceId":"r-bp1xxxxxxxxxxxxx",
					"BackupMode":"Automated",
					"BackupStatus":"Success",
					"BackupSize":"1024",
					"EngineVersion":"4.0"
				},
				{
					"BackupIntranetDownloadURL":"http://rdsbak-hk45-v2.oss-cn-hongkong-internal.aliyuncs.com/xxxxxxxxxxx",
					"BackupType":"FullBackup",
					"BackupEndTime":"2019-03-13T05:31:09Z",
					"BackupMethod":"Physical",
					"BackupId":"335538809",
					"BackupStartTime":"2019-03-13T05:28:46Z",
					"BackupDownloadURL":"http://rdsbak-hk45-v2.oss-cn-hongkong-internal.aliyuncs.com/xxxxxxxxxxx",
					"BackupDBNames":"all",
					"NodeInstanceId":"r-bp1xxxxxxxxxxxxx",
					"BackupMode":"Automated",
					"BackupStatus":"Success",
					"BackupSize":"1024",
					"EngineVersion":"4.0"
				},
				{
					"BackupIntranetDownloadURL":"http://rdsbak-hk45-v2.oss-cn-hongkong-internal.aliyuncs.com/xxxxxxxxxxx",
					"BackupType":"FullBackup",
					"BackupEndTime":"2019-03-12T05:31:14Z",
					"BackupMethod":"Physical",
					"BackupId":"335001607",
					"BackupStartTime":"2019-03-12T05:28:52Z",
					"BackupDownloadURL":"https://rdsbak-hk45-v2.oss-cn-hongkong.aliyuncs.com/http://rdsbak-hk45-v2.oss-cn-hongkong-internal.aliyuncs.com/xxxxxxxxxxx",
					"BackupDBNames":"all",
					"NodeInstanceId":"r-bp1xxxxxxxxxxxxx",
					"BackupMode":"Automated",
					"BackupStatus":"Success",
					"BackupSize":"1024",
					"EngineVersion":"4.0"
				}
			]
		}
	}
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|InvalidStartTime.Malformed|The Specified parameter StartTime is not valid.|开始时间验证失败，时间格式应该为gmt时间例如2011-06-11T16:00Z|
|400|InvalidEndTime.Malformed|The Specified parameter EndTime is not valid.|结束时间验证失败，时间格式应该为gmt时间例如2011-06-11T16:00Z|

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

