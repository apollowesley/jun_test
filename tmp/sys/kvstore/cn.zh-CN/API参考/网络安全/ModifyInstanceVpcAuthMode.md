# ModifyInstanceVpcAuthMode {#doc_api_R-kvstore_ModifyInstanceVpcAuthMode .reference}

调用ModifyInstanceVpcAuthMode开启或关闭免密访问。开启免密访问后，同一VPC内的云服务器不使用密码就可以访问Redis，同时仍然支持通过用户名及密码的方式连接Redis。

该API对应的控制台操作请参见开启免密访问\]\(~~85168~~\)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceVpcAuthMode&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|需要修改设置的实例的ID。

 |
|VpcAuthMode|String|是|Close|VPC认证模式：

 -   Open（需要密码认证）
-   Close（关闭密码认证，即VPC免密）

 **说明：** 默认为Open。

 |
|Action|String|否|ModifyInstanceVpcAuthMode|系统规定参数，取值：ModifyInstanceVpcAuthMode。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|ABAF95F6-35C1-4177-AF3A-70969EBDF623|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceVpcAuthMode
&InstanceId=r-bp1xxxxxxxxxxxxx
&VpcAuthMode=Close
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifyInstanceVpcAuthModeResponse>
      <RequestId>ABAF95F6-35C1-4177-AF3A-70969EBDF623</RequestId>
</ModifyInstanceVpcAuthModeResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"ABAF95F6-35C1-4177-AF3A-70969EBDF623"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

