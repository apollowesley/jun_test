# FlushInstance {#reference_zjb_hdf_xdb .reference}

调用该API将清空该实例的存储数据，且不可恢复。

调用此 API 为异步操作，用户调用后 API 即刻返回，清空实例的操作在后台执行。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|-|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：FlushInstance。|
|InstanceId|String|是|实例 ID，全局唯一。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= FlushInstance
&InstanceId=657e361a074646d5
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : "96893674-E50C-495D-AB0E-2F12C1E0FD45"
}
```

