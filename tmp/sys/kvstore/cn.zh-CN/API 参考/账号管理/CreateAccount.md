# CreateAccount {#reference_fyg_4cx_rfb .reference}

调用该API可以在实例中创建账号。通过给这些账号设置相应的权限，您可以更加灵活地管理实例，最大限度地避免误操作。

**说明：** 该API基于账号管理功能，当前只在Redis 4.0标准版实例中可用。此外，调用该API需满足以下条件：

-   当前实例状态为运行中；
-   当前实例没有被锁定；
-   一个实例下最多可创建18个账号，不可超过该数量限制。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：CreateAccount。|
|RegionId|String|否|地域ID。您可以通过调用[DescribeRegions](cn.zh-CN/API 参考/区域管理/DescribeRegions.md#)接口获取地域ID。|
|InstanceId|String|是|实例ID（全局唯一）|
|AccountName|String|是|账号名。以字母开头，由小写字母、数字、下划线组成，长度不超过16个字符。|
|AccountPassword|String|是|新密码。需由字母、数字或下划线组成，长度为6~32个字符。|
|AccountType|String|否|账号类型，取值为Normal（普通账号）。|
|AccountPrivilege|String|否|账号权限：-   RoleReadOnly（只读）
-   RoleReadWrite（读写，默认值）

|
|AccountDescription|String|否|账号描述。-   需以中文、英文字母开头，不能以http://或https://开头；
-   可以包含中英文字符、“\_”、“ -”、数字；
-   长度为2~256个字符。

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=CreateAccount
&InstanceId=r-xxxxxxxxxxxxxxx
&AccountName=test2
&AccountPassword=uwonno233
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<CreateAccountResponse>
 <RequestId>ABAF95F6-35C1-4177-AF3A-70969EBDF624</RequestId>
 <AcountName>test2</AcountName>
</CreateAccountResponse>
```

**JSON格式**

```
{
 "RequestId":"ABAF95F6-35C1-4177-AF3A-70969EBDF624",
 "AcountName":"test2"
}
```

