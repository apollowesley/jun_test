# DeleteInstance {#doc_api_R-kvstore_DeleteInstance .reference}

调用DeleteInstance释放Redis实例。

该API对应的控制台操作请参见[释放实例](~~43882~~)。

调用DeleteInstance接口的前提条件如下：

-   实例状态为运行中；
-   实例的付费方式为后付费（按量付费）。

**说明：** 预付费（包年包月）实例无法调用此接口主动删除，到期后将自动释放。 如需提前释放，请提交工单。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DeleteInstance&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-j6cxxxxxxxxxxxxx|要删除的实例的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|Action|String|否|DeleteInstance|系统规定参数，取值：DeleteInstance。

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
?Action=DeleteInstance
&InstanceId=r-j6cxxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DeleteInstanceResponse>
      <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</DeleteInstanceResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

