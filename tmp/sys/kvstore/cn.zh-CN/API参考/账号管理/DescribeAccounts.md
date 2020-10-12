# DescribeAccounts {#doc_api_R-kvstore_DescribeAccounts .reference}

调用DescribeAccounts查找指定Redis实例的帐户列表信息或实例中某个账号的信息。

**说明：** 该API仅支持4.0或以上版本的Redis实例。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeAccounts&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DescribeAccounts|系统规定参数，取值：DescribeAccounts。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|账号所属实例的ID。

 |
|RegionId|String|否|cn-hangzhou|地域ID。

 |
|AccountName|String|否|demoaccount|账号名。以小写字母开头，由小写字母、数字或下划线组成，长度不超过16个字符。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|Accounts| | |由账号信息组成的集合。

 |
|AccountDescription|String|this is a test account|账号备注信息。

 |
|AccountName|String|demoaccount|账号名称。

 |
|AccountStatus|String|Available|账号状态：

 -   Unavailable（不可用）
-   Available（可用）

 |
|AccountType|String|Normal|账号类型：

 -   Normal（普通账号）
-   Super（超级账号）

 |
|DatabasePrivileges| | |账号权限列表。

 |
|AccountPrivilege|String|RoleReadWrite|账号权限：

 -   RoleReadOnly（只读）
-   RoleReadWrite（读写，默认值）
-   RoleRepl（复制）

 **说明：** 复制权限支持读写，且支持使用SYNC/PSYNC命令。

 |
|InstanceId|String|r-bp1xxxxxxxxxxxxx|实例ID。

 |
|RequestId|String|6C9E114C-217C-4118-83C0-B40702221161|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeAccounts
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeAccountsResponse>
      <Accounts>
        <Account>
              <DatabasePrivileges>
                <DatabasePrivilege>
                      <AccountPrivilege>RoleReadWrite</AccountPrivilege>
                </DatabasePrivilege>
              </DatabasePrivileges>
              <AccountStatus>Available</AccountStatus>
              <InstanceId>r-bp1xxxxxxxxxxxxx</InstanceId>
              <AccountName>r-bp1xxxxxxxxxxxxx</AccountName>
              <PrivExceeded>0</PrivExceeded>
              <AccountType>Normal</AccountType>
        </Account>
      </Accounts>
      <RequestId>6C9E114C-217C-4118-83C0-B40702221161</RequestId>
</DescribeAccountsResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"Accounts":{
		"Account":[
			{
				"AccountStatus":"Available",
				"DatabasePrivileges":{
					"DatabasePrivilege":[
						{
							"AccountPrivilege":"RoleReadWrite"
						}
					]
				},
				"InstanceId":"r-bp1xxxxxxxxxxxxx",
				"AccountName":"r-bp1xxxxxxxxxxxxx",
				"PrivExceeded":"0",
				"AccountType":"Normal"
			}
		]
	},
	"RequestId":"6C9E114C-217C-4118-83C0-B40702221161"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

