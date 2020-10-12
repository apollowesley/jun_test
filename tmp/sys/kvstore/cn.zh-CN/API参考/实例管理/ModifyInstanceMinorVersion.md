# ModifyInstanceMinorVersion {#doc_api_R-kvstore_ModifyInstanceMinorVersion .reference}

调用ModifyInstanceMinorVersion升级Redis实例的小版本。

该API对应的控制台操作请参见[升级小版本](~~56450~~)。

小版本升级方式与所需时间根据实例架构有所区别：

-   集群版、读写分离版、标准版容灾架构通过跨机迁移方式升级。升级时间与数据量成正比，会发生30秒以内的闪断及60秒以内的实例只读。
-   标准版非容灾架构本地升级，通常5分钟内生效，对Redis服务无影响。如果本地资源不足，会采用跨机迁移方式升级，影响与集群版的升级相同。

**说明：** 请在业务低峰期升级，并确保应用程序具备重连机制。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceMinorVersion&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|Action|String|否|ModifyInstanceMinorVersion|系统规定参数，取值：ModifyInstanceMinorVersion。

 |
|Minorversion|String|否|latest\_version|要升级到的版本，默认值：latest\_version，即最新版本。

 |
|EffectTime|String|否|0|升级执行时间，取值：

 -   0（立即执行）
-   1（可维护时间内执行）

 默认值：0。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|28761557-0B33-41DF-AEEB-322DFF960395|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceMinorVersion
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyInstanceMinorVersionResponse>
      <RequestId>28761557-0B33-41DF-AEEB-322DFF960395</RequestId>
</ModifyInstanceMinorVersionResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"28761557-0B33-41DF-AEEB-322DFF960395"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

