# DescribeZones {#reference_f1p_1wn_4fb .reference}

调用该API可以查询指定地域中的可用区列表。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeZones。|
|RegionId|String|是|地域的ID。您可以通过调用[DescribeRegions](cn.zh-CN/API 参考/区域管理/DescribeRegions.md#)接口获取地域ID。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|Zones|List|可用区的集合|

|名称|类型| |
|--|--|--|
|ZoneName|String|该地域下的某特定可用区的名称|
|RegionId|String|该地域的ID|
|ZoneId|String|该地域下的某特定可用区的ID|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=DescribeZones
&RegionId=cn-hangzhou
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML示例**

```
<DescribeZonesResponse>
 <RequestId>1D42F072-72FE-4DC4-BB8E-64B1D298182E</RequestId>
 <Zones>
   <KVStoreZone>
     <ZoneName>华东 1 多可用区 6</ZoneName>
     <RegionId>cn-hangzhou</RegionId>
     <ZoneId>cn-hangzhou-MAZ6(b,f)</ZoneId>
   </KVStoreZone>
   <KVStoreZone>
     <ZoneName>华东 1 可用区 B</ZoneName>
     <RegionId>cn-hangzhou</RegionId>
     <ZoneId>cn-hangzhou-b</ZoneId>
   </KVStoreZone>
   <KVStoreZone>
     <ZoneName>华东 1 可用区 D</ZoneName>
     <RegionId>cn-hangzhou</RegionId>
     <ZoneId>cn-hangzhou-d</ZoneId>
   </KVStoreZone>
   <KVStoreZone>
     <ZoneName>华东 1 可用区 E</ZoneName>
     <RegionId>cn-hangzhou</RegionId>
     <ZoneId>cn-hangzhou-e</ZoneId>
   </KVStoreZone>
   <KVStoreZone>
     <ZoneName>华东 1 可用区 F</ZoneName>
     <RegionId>cn-hangzhou</RegionId>
     <ZoneId>cn-hangzhou-f</ZoneId>
   </KVStoreZone>
   <KVStoreZone>
     <ZoneName>华东 1 可用区 G</ZoneName>
     <RegionId>cn-hangzhou</RegionId>
     <ZoneId>cn-hangzhou-g</ZoneId>
   </KVStoreZone>
 </Zones>
</DescribeZonesResponse>
```

**JSON示例**

```
{
 "RequestId": "1D42F072-72FE-4DC4-BB8E-64B1D298182E",
 "Zones": {
   "KVStoreZone": [
     {
       "ZoneName": "华东 1 多可用区 6",
       "RegionId": "cn-hangzhou",
       "ZoneId": "cn-hangzhou-MAZ6(b,f)"
     },
     {
       "ZoneName": "华东 1 可用区 B",
       "RegionId": "cn-hangzhou",
       "ZoneId": "cn-hangzhou-b"
     },
     {
       "ZoneName": "华东 1 可用区 D",
       "RegionId": "cn-hangzhou",
       "ZoneId": "cn-hangzhou-d"
     },
     {
       "ZoneName": "华东 1 可用区 E",
       "RegionId": "cn-hangzhou",
       "ZoneId": "cn-hangzhou-e"
     },
     {
       "ZoneName": "华东 1 可用区 F",
       "RegionId": "cn-hangzhou",
       "ZoneId": "cn-hangzhou-f"
     },
     {
       "ZoneName": "华东 1 可用区 G",
       "RegionId": "cn-hangzhou",
       "ZoneId": "cn-hangzhou-g"
     }
   ]
 }
}
```

