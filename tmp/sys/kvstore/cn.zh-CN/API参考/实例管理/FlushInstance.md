# FlushInstance {#doc_api_R-kvstore_FlushInstance .reference}

调用FlushInstance清空Redis实例中的数据，不可恢复。

该API对应的控制台操作请参见[清除数据](~~43881~~)。

**说明：** 调用此API删除数据前请妥善备份数据或确认数据无需备份。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=FlushInstance)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|FlushInstance|系统规定参数，取值：FlushInstance。

 |
|InstanceId|String|是|r-j6cxxxxxxxxxxxxx|目标实例的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E7|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=FlushInstance
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<FlushInstanceResponse>
  <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</FlushInstanceResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E7"
}
```

## 错误码 { .section}

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

