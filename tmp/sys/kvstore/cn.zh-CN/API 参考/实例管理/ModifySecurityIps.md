# ModifySecurityIps {#reference_hby_zv2_xdb .reference}

调用该API能够设置实例的 IP 白名单，从而允许指定的ECS IP访问实例。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：ModifySecurityIps。|
|InstanceId|String|是|实例 ID （全局唯一）|
|SecurityIps|String|是|待修改的 IP 白名单列表|
|SecurityIpGroupName|String|是|白名单分组|
|ModifyMode|String|否|修改方式，取值如下：-   Cover：覆盖原白名单，默认值。
-   Append：追加白名单
-   Delete：删除该白名单

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= ModifySecurityIps
&InstanceId=657e361a074646d5
&SecurityIps=1.1.1.1，2.2.2.2
&SecurityIpGroupName=testgroup
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : "AAAF99B1-69ED-4E80-8CD5-272C09E46ACF"
}
```

