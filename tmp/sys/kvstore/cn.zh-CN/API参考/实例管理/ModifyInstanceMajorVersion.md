# ModifyInstanceMajorVersion {#doc_api_R-kvstore_ModifyInstanceMajorVersion .reference}

调用ModifyInstanceMajorVersion升级Redis实例的大版本。

该API对应的控制台操作请参见[升级大版本](~~101764~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceMajorVersion&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目标实例的ID。

 |
|MajorVersion|String|是|4.0|要升级到的大版本号，当前支持`4.0`。

 |
|Action|String|否|ModifyInstanceMajorVersion|系统规定参数，取值：ModifyInstanceMajorVersion。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|EffectTime|String|否|0|升级执行时间：

 -   0（立即执行）
-   1（在可维护时间段执行）

 **说明：** 默认为0。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|AA587FB2-2593-4DFE-BE13-2494C2DF0CBF|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceMajorVersion
&InstanceId=r-bp1xxxxxxxxxxxxx
&MajorVersion=4.0
&EffectTime=0
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyInstanceMajorVersionResponse>  
      <RequestId>AA587FB2-2593-4DFE-BE13-2494C2DF0CBF</RequestId>
</ModifyInstanceMajorVersionResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"AA587FB2-2593-4DFE-BE13-2494C2DF0CBF"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

