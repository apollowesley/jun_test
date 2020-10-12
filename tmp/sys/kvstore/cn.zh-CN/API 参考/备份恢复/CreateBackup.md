# CreateBackup {#reference_ihp_sw2_xdb .reference}

调用该API可以创建备份。此 API 为异步操作，用户调用后 API 即刻返回，创建备份的操作在后台执行。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：CreateBackup。|
|InstanceId|String|是|实例 ID|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com、
?<公共请求参数>
&Action=CreateBackup
&InstanceId=de5d88e34d004211
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : "5C97648D-C85F-4D58-A71F-7B6750856BF7”
}
```

