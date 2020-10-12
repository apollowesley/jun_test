# DeleteAccount {#reference_wtc_slx_rfb .reference}

调用该API可以删除一个账号。

**说明：** 

-   该API基于账号管理功能，当前只在Redis 4.0标准版实例中可用；
-   待删除账号所属实例的状态需为运行中。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DeleteAccount。|
|InstanceId|String|是|实例ID（全局唯一）|
|AccountName|String|是|账号名。以字母开头，由小写字母、数字、下划线组成，长度不超过16个字符。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=DeleteAccount
&InstanceId=r-xxxxxxxxxxxxxxx
&AccountName=test2
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<DeleteAccountResponse>
 <RequestId>8129F11A-D70B-43A6-9455-CE9EAA71F095</RequestId>
</DeleteAccountResponse>
```

**JSON格式**

```
{
 "RequestId":"8129F11A-D70B-43A6-9455-CE9EAA71F095"
}
```

