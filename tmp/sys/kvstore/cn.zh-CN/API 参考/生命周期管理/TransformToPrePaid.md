# TransformToPrePaid {#reference_ctb_ns2_xdb .reference}

调用该API可以转换实例付费类型，将按量付费实例转换为包年包月（预付费）实例。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|操作接口名，取值：TransformToPrePaid。|
|InstanceId|String|是|实例 ID|
|Period|Long|是|预付费续费时长，单位是月。取值范围为：1-9、12、24、36。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|OrderId|String|订单 ID|
|EndTime|String|转换到包月实例后的到期时间。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= TransformToPrePaid
&InstanceId=736538d0a6894665
&Period=1
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"OrderId" : "201294900350011"
"EndTime" : "2017-11-19T00:00:00Z"
"RequestId" : "9F64CDE7-6B91-4C13-9252-F221AFCFF66B"
}
```

