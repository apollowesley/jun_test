# CreateAccount {#doc_api_R-kvstore_CreateAccount .reference}

调用CreateAccount可以在Redis实例中创建有特定权限的账号。

**说明：** 

-   该API仅支持4.0或以上版本的Redis实例；
-   使用该API需要实例状态为运行中；
-   一个实例下最多可创建18个账号。

该API对应的控制台操作请参见[账号管理](~~92665~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=CreateAccount&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|AccountName|String|是|demoaccount|账号名。需以字母开头，由小写字母、数字或下划线组成，长度不超过16个字符。

 |
|AccountPassword|String|是|uWonno233|账号的密码。长度为8－32位，需包含大写字母、小写字母、特殊字符和数字中的至少三种，允许的特殊字符包括`!@#$%^&*()_+-=`。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|需要创建账号的实例的ID。

 |
|Action|String|否|CreateAccount|系统规定参数，取值：CreateAccount。

 |
|RegionId|String|否|cn-hangzhou|地域ID。

 |
|AccountPrivilege|String|否|RoleReadOnly|账号权限：

 -   RoleReadOnly（只读）
-   RoleReadWrite（读写，默认值）
-   RoleRepl（复制）

 **说明：** 复制权限支持读写，且开放SYNC/PSYNC命令。目前仅支持在4.0标准版实例中创建有复制权限的账号。

 |
|AccountDescription|String|否|this is a test account|账号描述。

 -   需以中文、英文字母开头，不能以http: //或https: //开头；
-   可以包含中文、英文字母、“\_”、“ -”和数字；
-   长度为2~256个字符。

 |
|AccountType|String|否|Normal|账号类型，取值为Normal（普通账号）。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|AcountName|String|demoaccount|账号名称。

 |
|InstanceId|String|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|RequestId|String|ABAF95F6-35C1-4177-AF3A-70969EBDF624|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=CreateAccount
?AccountName=demoaccount
&AccountPassword=uWonno233
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<CreateAccountResponse>
      <RequestId>ABAF95F6-35C1-4177-AF3A-70969EBDF624</RequestId>
      <AcountName>demoaccount</AcountName>
</CreateAccountResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"ABAF95F6-35C1-4177-AF3A-70969EBDF624",
	"AcountName":"demoaccount"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

