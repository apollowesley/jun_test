# ModifyBackupPolicy {#doc_api_R-kvstore_ModifyBackupPolicy .reference}

调用ModifyBackupPolicy修改Redis实例的自动备份策略。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyBackupPolicy&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|ModifyBackupPolicy|系统规定参数，取值：ModifyBackupPolicy。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|PreferredBackupPeriod|String|是|Tuesday|备份周期：

 -   Monday（周一）
-   Tuesday（周二）
-   Wednesday（周三）
-   Thursday（周四）
-   Friday（周五）
-   Saturday（周六）
-   Sunday（周日）

 |
|PreferredBackupTime|String|是|00:00Z-04:00Z|备份时间，格式：`HH:mmZ-HH:mmZ`。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|RegionId|String|否|cn-hangzhou|地域ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyBackupPolicy
&InstanceId=r-bp1xxxxxxxxxxxxx
&PreferredBackupTime=00:00Z-04:00Z
&PreferredBackupPeriod=Tuesday
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyBackupPolicyResponse>
      <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</ModifyBackupPolicyResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

