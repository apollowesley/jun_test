# SwitchNetwork {#reference_qqx_cw2_xdb .reference}

调用该API可以切换实例的网络类型，支持从经典网络切换为VPC网络。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：SwitchNetwork。|
|InstanceId|String|是|实例 ID （全局唯一）|
|TargetNetworkType|String|是|切换到的网络类型。 -   VPC：专有网络类型的实例
-   Classic：经典网络类型的实例

当前只支持经典网络切换到 VPC 类型， 该类型需要填写 VPC。

|
|VpcId|String|否|切换到的 VPC 的虚拟网络 ID|
|VSwitchId|String|否|切换到的 VPC 的虚拟交换机 ID。如果指定了 VPC ID，则该参数也必须指定。|
|RetainClassic|String|否|是否保留经典网络 IP。默认是False。-   True：保留
-   False：不保留

|
|ClassicExpiredDays|String|否|保留经典网络 IP 的时间。单位：天。 可选值：14、30、60、120。若选择保留经典网络地址，则该参数必选。

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= SwitchNetwork
&InstanceId=fdafas32323ed
&TargetNetworkType=VPC
&VpcId=fadsfa
&VSwitchId=131ed
&RetainClassic=True
&ClassicExpiredDays=30
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : "AAAF99B1-69ED-4E80-8CD5-272C09E46ACF"
}
```

