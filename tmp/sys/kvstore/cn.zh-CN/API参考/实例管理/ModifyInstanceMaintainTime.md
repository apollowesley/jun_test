# ModifyInstanceMaintainTime {#doc_api_R-kvstore_ModifyInstanceMaintainTime .reference}

调用ModifyInstanceMaintainTime修改Redis实例的可维护时间段，阿里云将在您设定的可维护时间段内对Redis实例进行例行维护。

该API对应的控制台操作请参见[设置可维护时间段](~~55252~~)。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceMaintainTime)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|ModifyInstanceMaintainTime|系统规定参数，取值：ModifyInstanceMaintainTime。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|MaintainStartTime|String|是|03:00Z|可维护时间段的开始时间，格式：`HH:mmZ`。

 |
|MaintainEndTime|String|是|02:00Z|可维护时间段的结束时间，格式：`HH:mmZ`。

 **说明：** 开始时间和结束时间的间隔应为1小时，如：MaintainStartTime为`01:00Z`，MaintainEndTime为`02:00Z`。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceMaintainTime
&InstanceId=r-bp1xxxxxxxxxxxxx
&MaintainStartTime=02:00Z
&MaintainEndTime=03:00Z
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyInstanceMaintainTimeResponse>
  <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</ModifyInstanceMaintainTimeResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76"
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|InvalidEndTime.Format|Specified end time is not valid.|时间验证失败|

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

