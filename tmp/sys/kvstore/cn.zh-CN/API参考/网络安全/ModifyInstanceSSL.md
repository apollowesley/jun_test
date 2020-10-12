# ModifyInstanceSSL {#doc_api_R-kvstore_ModifyInstanceSSL .reference}

调用ModifyInstanceSSL设置Redis实例的SSL加密模式。

该API对应的控制台操作请参见[设置SSL加密](~~84898~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceSSL&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|需要修改设置的实例的ID。

 |
|SSLEnabled|String|是|Enable|修改SSL状态：

 -   Disable（关闭）
-   Enable（开启）
-   Update（更新证书）

 |
|Action|String|否|ModifyInstanceSSL|系统规定参数，取值：ModifyInstanceSSL。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|InstanceId|String|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|TaskId|String|1111111111|任务ID。

 |
|RequestId|String|52D901ED-E0A5-42FB-B9DB-39C295C37738|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceSSL
&InstanceId=r-bp1xxxxxxxxxxxxx
&SSLEnabled=Enable
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyInstanceSSLResponse>
      <InstanceId>r-xxxxxxxxxxxxxxx</InstanceId>
      <RequestId>52D901ED-E0A5-42FB-B9DB-39C295C37738</RequestId>
      <TaskId>1111111111</TaskId>
</ModifyInstanceSSLResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"52D901ED-E0A5-42FB-B9DB-39C295C37738",
	"InstanceId":"r-xxxxxxxxxxxxxxx",
	"TaskId":"1111111111"
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|403|IncorrectDBInstanceState|Current DB instance state does not support this operation.|由于实例的状态不允许该操作，实例状态是运行中才能操作|
|403|IncorrectDBInstanceLockMode|Current DB instance lock mode does not support this operation.|当前的实例锁定模式不支持此操作。|
|400|InvalidParameters.Format|Specified parameters is not valid.|参数无效|

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

