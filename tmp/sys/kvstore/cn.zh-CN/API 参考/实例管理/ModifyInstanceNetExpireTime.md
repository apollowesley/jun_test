# ModifyInstanceNetExpireTime {#reference_yx1_wv2_xdb .reference}

当实例之前做过经典网络向 VPC 网络迁移，选择了保留经典网络连接地址，可调用该API延长经典网络地址的保存时间。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：ModifyInstanceNetExpireTime。|
|InstanceId|String|是|实例 ID（全局唯一）|
|ConnectionString|String|是|经典网络的访问域名|
|ClassicExpiredDays|String|是|选择保留时长。取值：14、30、60或120。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= ModifyInstanceNetExpireTime
&ConectionString= fdafas32323ed.redis.rds.aliyuncs.com
&InstanceId=fdafas32323ed
&ClassicExpiredDays=120
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : "AAAF99B1-69ED-4E80-8CD5-272C09E46ACF"
}
```

