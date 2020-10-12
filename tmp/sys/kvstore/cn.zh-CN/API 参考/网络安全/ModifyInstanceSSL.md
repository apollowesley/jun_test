# ModifyInstanceSSL {#reference_d3x_3n2_sfb .reference}

调用该API可以设置SSL加密模式。

**说明：** 该API只支持2.8标准版实例。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：ModifyInstanceSSL。|
|InstanceId|String|是|实例 ID（全局唯一）|
|SSLEnabled|String|是|修改SSL状态：-   Disable（关闭）
-   Enable（开启）
-   Update（更新证书）

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|InstanceId|String|实例 ID|
|TaskId|String|任务 ID|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceSSL
&InstanceId=r-xxxxxxxxxxxxxxx
&SSLEnabled=Enable
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<ModifyInstanceSSLResponse>
 <InstanceId>r-xxxxxxxxxxxxxxx</InstanceId>
 <RequestId>52D901ED-E0A5-42FB-B9DB-39C295C37738</RequestId>
 <TaskId>111</TaskId>
</ModifyInstanceSSLResponse>
```

**JSON格式**

```
{
    "InstanceId":"r-xxxxxxxxxxxxxxx",
    "RequestId":"52D901ED-E0A5-42FB-B9DB-39C295C37738",
    "TaskId":"111"
}
```

