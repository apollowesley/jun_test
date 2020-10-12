# ModifyInstanceAttribute {#doc_api_R-kvstore_ModifyInstanceAttribute .reference}

调用ModifyInstanceAttribute修改Redis实例的属性，包括名称和密码。

该API对应的控制台操作请参见[修改密码](~~43874~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceAttribute&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-j6cxxxxxxxxxxxxx|目标实例的ID。

 |
|Action|String|否|ModifyInstanceAttribute|系统规定参数，取值：ModifyInstanceAttribute。

 |
|InstanceName|String|否|newinstancename|新的实例名称。名称为2-80个字符，以大小写英文字母或中文开头，不支持字符`@/:=”<>{[]}`和空格。

 |
|NewPassword|String|否|uW8+nsrp|新的实例密码。长度为8-32位，需包含大写字母、小写字母、数字、特殊字符（支持`!@#$%^&*()_+-=`）中至少三种。

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
?Action=ModifyInstanceAttribute
&InstanceId=r-j6cxxxxxxxxxxxxx
&InstanceName=demo
&NewPassword=uW8+nsrp
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyInstanceAttributeResponse>
      <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</ModifyInstanceAttributeResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"EFC9161F-15E3-4A6E-8A99-C09916D1F464"
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|400|MissingParameter|InstanceName/NewPassword at least one is mandatory for this action.|实例名称/新密码至少要填写一个。|
|400|InvalidInstanceName.Malformed|The Specified parameter "InstanceName" is not valid.|InstanceName验证失败|
|400|InvalidPassword.Malformed|The Specified parameter "NewPassword" is not valid.|密码验证无效|

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

