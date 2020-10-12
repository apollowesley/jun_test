# RenewMultiInstance {#reference_m3x_czx_wdb .reference}

## 描述 {#section_z1z_2kw_wbb .section}

对包月实例进行批量续费操作。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|操作接口名，取值：RenewMultiInstance。|
|InstanceIds|String|是|实例 ID，多个时用逗号隔开。|
|Period|Long|是|预付费续费时长，单位是月。取值范围为：1-9、12、24、36。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= RenewMultiInstance
&InstanceIds=736538d0a6894665, de5d88e34d004241
&Period=1
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : ""698D75F4-87B2-41DD-BDEB-B2D45E94254F"
}
```

