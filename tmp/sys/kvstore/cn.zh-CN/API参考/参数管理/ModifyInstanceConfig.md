# ModifyInstanceConfig {#doc_api_R-kvstore_ModifyInstanceConfig .reference}

调用ModifyInstanceConfig修改Redis实例的配置参数。

该API对应的控制台操作请参见[参数设置](~~43885~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceConfig&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Config|String|是|\{"EvictionPolicy":"volatile-lru","list-max-ziplist-entries":512,"zset-max-ziplist-entries":128,"hash-max-ziplist-entries":512,"hash-max-ziplist-value":64,"list-max-ziplist-value":64,"set-max-intset-entries":512,"zset-max-ziplist-value":64\}|实例的配置参数（JSON String），详情请参见[实例规格表](~~107984~~)。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|Action|String|否|ModifyInstanceConfig|系统规定参数，取值：ModifyInstanceConfig。

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
?Action=ModifyInstanceConfig
&InstanceId=r-bp1xxxxxxxxxxxxx
&Config={"EvictionPolicy":"volatile-lru","list-max-ziplist-entries":512,"zset-max-ziplist-entries":128,"hash-max-ziplist-entries":512,"hash-max-ziplist-value":64,"list-max-ziplist-value":64,"set-max-intset-entries":512,"zset-max-ziplist-value":64}
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyInstanceConfigResponse>
      <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</ModifyInstanceConfigResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"requestId":"8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76"
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|InvalidParameters.Format|Specified parameters is not valid.|参数无效|

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

