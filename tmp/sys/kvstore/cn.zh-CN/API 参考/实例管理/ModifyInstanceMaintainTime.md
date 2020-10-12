# ModifyInstanceMaintainTime {#reference_h3w_qv2_xdb .reference}

## 描述 {#section_z1z_2kw_wbb .section}

修改可运维时间，阿里云可以在您设定的可运维时间对实例进行例行维护。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：ModifyInstanceMaintainTime。|
|InstanceId|String|是|实例 ID（全局唯一）|
|MaintainStartTime|String|是|可运维时间开始时间，格式：`HH:mmZ`。|
|MaintainEndTime|String|是|可运维时间结束时间，格式：`HH:mmZ`。**说明：** 开始时间和结束时间的间隔应为1小时，如：MaintainStartTime为01:00Z，MaintainEndTime为02:00Z。

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action=ModifyInstanceMaintainTime
&InstanceId=657e361a074646d5
&MaintainStartTime=02:00Z
&MaintainEndTime=06:00Z
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : "A099747A-0826-499D-9422-381C07337F73"
}
```

