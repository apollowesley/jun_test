# RenewInstance {#reference_y3s_xyx_wdb .reference}

## 描述 {#section_z1z_2kw_wbb .section}

对包月实例进行续费操作。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|操作接口名，取值：RenewInstance。|
|InstanceId|String|是|实例 ID（全局唯一）|
|InstanceClass|String|否|变配目标规格，支持续费时变配规格。当目标规格与当前不同时，在原到期时间点执行变配。|
|Period|Long|是|预付费续费时长，单位是月。取值范围为：1-9、12、24、36。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>| |参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|OrderId|String|订单 ID。|
|EndTime|String|续费后的实例到期时间。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= RenewInstance
&InstanceId=736538d0a6894665
&InstanceClass= redis.master.mid.default
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"OrderId" : "201294800290011"
"EndTime" : "2018-03-19T00:00:00Z"
"RequestId" : "4B75DB12-3FFF-4FF4-B985-E78CDCADB959"
}
```

