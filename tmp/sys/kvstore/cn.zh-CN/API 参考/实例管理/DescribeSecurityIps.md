# DescribeSecurityIps {#reference_w1l_hv2_xdb .reference}

## 描述 {#section_z1z_2kw_wbb .section}

查询允许访问实例的 IP 名单。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeSecurityIps。|
|InstanceId|String|是|实例名|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|SecurityIpGroups|List|实例的 IP 白名单分组列表。|

## SecurityIpGroups 的参数 {#section_vpd_htv_pcb .section}

|名称|类型|描述|
|--|--|--|
|SecurityIpGroupName|String|IP 白名单分组的名字。|
|SecurityIpList|String|IP 白名单分组下的 IP 列表，最多1000个。IP 之间以逗号隔开，格式如下：`0.0.0.0/0,10.23.12.24（IP）`，或者`10.23.12.24/24`（CIDR 模式，无类域间路由，/24表示了地址中前缀的长度，范围\[1,32\]）。

|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com.com
?<公共请求参数>
&Action= DescribeSecurityIps
&InstanceId=657e361a074646d5
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
" SecurityIpGroups " : {
" SecurityIpGroup" : [{
" SecurityIpGroupName" : "ipsecuritytest" ,
" SecurityIpList " : "10.23.12.24"
}
]
},
"RequestId" : "969D0A1D-C91A-4837-9F70-49785DF9BDCE"
}
```

