# ModifyAccountDescription {#reference_e2j_tkw_rfb .reference}

调用该API可以修改账号描述，方便用户备注账号用途等信息。

**说明：** 该API基于账号管理功能，当前只在Redis 4.0标准版实例中可用。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：ModifyAccountDescription。|
|InstanceId|String|是|实例ID（全局唯一）|
|AccountName|String|否|账号名。以字母开头，由小写字母、数字、下划线组成，长度不超过16个字符。|
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
?Action=ModifyAccountDescription
&InstanceId=r-xxxxxxxxxxxxxxx
&AccountName=r-xxxxxxxxxxxxxxx
&AccountDescription=testaccount
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<ModifyAccountDescriptionResponse>
 <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</ModifyAccountDescriptionResponse>
```

**JSON格式**

```
{
  "RequestId":"8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76"
}
```

