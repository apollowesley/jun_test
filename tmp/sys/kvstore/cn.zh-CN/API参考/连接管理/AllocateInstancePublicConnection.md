# AllocateInstancePublicConnection {#doc_api_R-kvstore_AllocateInstancePublicConnection .reference}

调用AllocateInstancePublicConnection为Redis实例申请外网连接地址。

该API对应的控制台操作请参见[外网连接](~~43850~~)。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=AllocateInstancePublicConnection)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|ConnectionStringPrefix|String|是|r-bp1xxxxxxxxxxxxx|外网连接地址的前缀，需由小写英文字母与数字组成，以小写字母开头，长度为8~64个字符。

 Redis外网连接地址格式为：`<前缀>.redis.rds.aliyuncs.com`。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|Port|String|是|6379|Redis服务端口，取值范围：1024~65535。

 |
|Action|String|否|AllocateInstancePublicConnection|系统规定参数，取值：AllocateInstancePublicConnection。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|20C8341E-B5AD-4B24-BD82-D73241522ABF|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=AllocateInstancePublicConnection
&ConnectionStringPrefix=r-bp1xxxxxxxxxxxxx
&InstanceId=r-bp1xxxxxxxxxxxxx
&Port=6379
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<AllocateInstancePublicConnectionResponse>
  <RequestId>20C8341E-B5AD-4B24-BD82-D73241522ABF</RequestId>
</AllocateInstancePublicConnectionResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"20C8341E-B5AD-4B24-BD82-D73241522ABF"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

