# ModifyDBInstanceConnectionString {#doc_api_R-kvstore_ModifyDBInstanceConnectionString .reference}

调用ModifyDBInstanceConnectionString修改Redis实例的连接地址。

该API对应的控制台操作请参见[修改连接地址](~~85683~~)。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=ModifyDBInstanceConnectionString)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|ModifyDBInstanceConnectionString|系统规定参数，取值ModifyDBInstanceConnectionString。

 |
|CurrentConnectionString|String|是|r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com|Redis实例当前的连接地址。

 |
|DBInstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|NewConnectionString|String|是|standardredis|新的连接地址。

 **说明：** 此处输入的值为标准连接地址中的第一部分，即`.redis.rds.aliyuncs.com`之前的字符串。例如输入`standardredis`，则修改后的连接地址为`standardredis.redis.rds.aliyuncs.com`。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|1790D68A-465C-44E3-BC24-9732652961F9|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyDBInstanceConnectionString
&currentConnectionString=r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com
&DBInstanceId=r-bp1xxxxxxxxxxxxx
&newConnectionString=standardredis
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyDBInstanceConnectionStringResponse>
  <RequestId>1790D68A-465C-44E3-BC24-9732652961F9</RequestId>
</ModifyDBInstanceConnectionStringResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"1790D68A-465C-44E3-BC24-9732652961F9"
}
```

## 错误码 { .section}

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

