# ModifyInstanceAttribute {#reference_jsv_ntx_wdb .reference}

## 描述 {#section_z1z_2kw_wbb .section}

修改一个实例的属性，包括名称或密码。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|-|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|操作接口名，取值：ModifyInstanceAttribute。|
|InstanceId|String|是|实例 ID，全局唯一。|
|InstanceName|String|否|修改后的实例名称。名称为2-128个字符，以大小写英文字母或中文开头，不支持字符@/:=”<\>\{\[\]\}和空格。|
|NewPassword|String|否|实例密码。字符类型：8～30个字符，需同时包含大写字母、小写字母和数字。**说明：** 对于特殊字符 !<\>\(\)\[\]\{\},\`~.-\_@\#$%^&\*暂不支持。

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= ModifyInstanceAttribute
&InstanceId=657e361a074646d5
&NewPassword=Qa12345678
&InstanceName=TestInstance
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : "E3B35BEA-9EB0-402C-88CF-C46CCCC1EE59" 
}
```

