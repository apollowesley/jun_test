# DescribeBackupPolicy {#doc_api_R-kvstore_DescribeBackupPolicy .reference}

调用DescribeBackupPolicy查询Redis实例的备份策略，包括备份周期、备份时间等。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeBackupPolicy&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DescribeBackupPolicy|系统规定参数，取值：DescribeBackupPolicy。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|RegionId|String|否|cn-hangzhou|地域ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|BackupRetentionPeriod|String|7|备份保留天数。

 |
|PreferredBackupPeriod|String|Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday|备份周期：

 -   Monday（周一）
-   Tuesday（周二）
-   Wednesday（周三）
-   Thursday（周四）
-   Friday（周五）
-   Saturday（周六）
-   Sunday（周日）

 |
|PreferredBackupTime|String|05:00Z-06:00Z|备份时间，格式：`HH:mmZ-HH:mmZ`。

 |
|PreferredNextBackupTime|String|2019-03-14T05:28Z|下次备份时间。

 |
|RequestId|String|90B82DB7-FB28-4CC2-ADBF-1F8659F30C26|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeBackupPolicy
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeBackupPolicyResponse>
      <PreferredBackupPeriod>Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday</PreferredBackupPeriod>
      <RequestId>90B82DB7-FB28-4CC2-ADBF-1F8659F30C26</RequestId>
      <PreferredNextBackupTime>2018-10-26T19:05Z</PreferredNextBackupTime>
      <PreferredBackupTime>19:00Z-20:00Z</PreferredBackupTime>
      <BackupRetentionPeriod>7</BackupRetentionPeriod>
</DescribeBackupPolicyResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"PreferredBackupPeriod":"Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday",
	"RequestId":"90B82DB7-FB28-4CC2-ADBF-1F8659F30C26",
	"BackupRetentionPeriod":"7",
	"PreferredBackupTime":"19:00Z-20:00Z",
	"PreferredNextBackupTime":"2018-10-26T19:05Z"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

