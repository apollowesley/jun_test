# ModifyInstanceAutoRenewalAttribute {#reference_wfx_cd5_qfb .reference}

调用该API可以修改一个或多个实例的自动续费属性，开启或者关闭实例的到期后自动续费功能。

**说明：** 自动续费将于实例到期前7天触发。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：ModifyInstanceAutoRenewalAttribute。|
|DBInstanceId|String|是|实例 ID（全局唯一）。多个实例ID用`,`（半角逗号）拼接，最多允许30个实例 ID。|
|RegionId|String|是|地域 ID，如`cn-hangzhou`。可调用[DescribeRegions](cn.zh-CN/API 参考/区域管理/DescribeRegions.md#)查询。|
|Duration|String|否|自动续费周期，当`AutoRenew=True`时，该值有效且必传。取值范围为1-12，表示实例到期时，将以月为单位进行自动续费，月数等于该值。|
|AutoRenew|String|否|开启或关闭自动续费，取值可为：-   True（开启）；
-   False（关闭）。

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceAutoRenewalAttribute
&DBInstanceId=r-xxxxxxxxxxxxxxx
&RegionId=cn-hangzhou
&AutoRenew=True
&Duration=1
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<ModifyInstanceAutoRenewalAttributeResponse>
 <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</ModifyInstanceAutoRenewalAttributeResponse>
```

**JSON格式**

```
{
    "RequestId":"52D901ED-E0A5-42FB-B9DB-39C295C37738"
}
```

