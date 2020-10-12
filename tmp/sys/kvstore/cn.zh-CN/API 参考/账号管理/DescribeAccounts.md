# DescribeAccounts {#reference_sjz_5pb_rfb .reference}

调用该API可以查找指定实例的帐户列表信息或实例中某个账号的信息。

**说明：** 该API基于账号管理功能，当前只在Redis 4.0标准版实例中可用。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeAccounts。|
|InstanceId|String|是|实例ID（全局唯一）|
|AccountName|String|否|账号名。以字母开头，由小写字母、数字、下划线组成，长度不超过16个字符。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|Accounts|List|由Account组成的数组|

|名称|类型|描述|
|--|--|--|
|InstanceId|String|实例 ID|
|AccountName|String|账号名称|
|AccountStatus|String|账号状态：-   Unavailable（不可用）
-   Available（可用）

|
|AccountDescription|String|账号备注信息|
|AccountType|String|账号类型：-   Normal（普通账号）
-   Super（超级账号）

|
|PrivExceeded|String|授权是否超过限制，取值如下：-   1（是）；
-   0（否，默认为否）。

|
|DatabasePrivileges|List|授权列表。包含String类型的AccountPrivilege，显示账号权限。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=DescribeAccounts
&InstanceId=r-xxxxxxxxxxxxxxx
&AccountName=r-xxxxxxxxxxxxxxx
&AccountPrivilege=ReadOnly
&<[公共请求参数]>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<DescribeAccountsResponse>
 <Accounts>
   <Account>
     <DatabasePrivileges>
       <DatabasePrivilege>
         <AccountPrivilege>RoleReadWrite</AccountPrivilege>
       </DatabasePrivilege>
     </DatabasePrivileges>
     <AccountStatus>Available</AccountStatus>
     <InstanceId>r-xxxxxxxxxxxxxxx</InstanceId>
     <AccountName>r-xxxxxxxxxxxxxxx</AccountName>
     <PrivExceeded>0</PrivExceeded>
     <AccountType>Normal</AccountType>
   </Account>
 </Accounts>
 <RequestId>6C9E114C-217C-4118-83C0-B40702221161</RequestId>
</DescribeAccountsResponse>
```

**JSON格式**

```
{
  "Accounts":{
      "Account":[
          {
              "DatabasePrivileges":{
                  "DatabasePrivilege":[
                      {
                          "AccountPrivilege":"RoleReadWrite"
                      }
                  ]
              },
              "AccountStatus":"Available",
              "InstanceId":"r-xxxxxxxxxxxxxxx",
              "AccountName":"r-xxxxxxxxxxxxxxx",
              "PrivExceeded":"0",
              "AccountType":"Normal"
          }
      ]
  },
  "RequestId":"6C9E114C-217C-4118-83C0-B40702221161"
}
```

