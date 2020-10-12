# RestoreInstance {#reference_dz4_d1f_xdb .reference}

调用该API可以基于一个备份集将数据恢复到主实例。

**说明：** 此操作风险较大，会用备份数据覆盖实例数据，请务必谨慎操作。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：RestoreInstance。|
|InstanceId|String|是|实例 ID|
|BackupId|String|是|备份结果集 ID|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= RestoreInstance
&InstanceId= de5d88e34d004211
&BackupId=fdafasf111
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : "AFA391BF-808F-4DA6-80A2-A382108A0945"
}
```

