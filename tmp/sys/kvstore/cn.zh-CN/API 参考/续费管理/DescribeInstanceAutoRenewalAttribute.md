# DescribeInstanceAutoRenewalAttribute {#reference_esj_pkb_rfb .reference}

调用该API可以查看当前账号下某地域内实例的自动续费情况。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeInstanceAutoRenewalAttribute。|
|DBInstanceId|String|否|实例ID（全局唯一）|
|RegionId|String|否|地域ID，如`cn-hangzhou`。可使用[DescribeRegions](cn.zh-CN/API 参考/区域管理/DescribeRegions.md#)查询。|
|PageSize|Integer|否|每页记录数，取值：30/50/100。默认值：30。|
|PageNumber|Integer|否|页码，需大于0且不能超过Integer的最大值，默认值：1。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|TotalRecordCount|Integer|总记录数|
|PageNumber|Integer|页码|
|PageRecordCount|Integer|本页备份集个数|
|Items|List|由Item组成的数组|

|参数|**类型**|**说明**|
|--|------|------|
|DBInstanceId|String|实例ID|
|RegionId|String|地域ID|
|Duration|Integer|续费周期。如`Duration=1`表示自动续费1个月。|
|AutoRenew|String|自动续费状态：-   True（已开启）
-   False（未开启）

|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=DescribeInstanceAutoRenewalAttribute
&RegionId=cn-shanghai
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<DescribeInstanceAutoRenewalAttributeResponse>
 <Items>
   <Item>
     <Duration>1</Duration>
     <RegionId>cn-shanghai</RegionId>
     <DBInstanceId>r-xxxxxxxxxxxxxxx</DBInstanceId>
     <AutoRenew>true</AutoRenew>
   </Item>
 </Items>
 <TotalRecordCount>1</TotalRecordCount>
 <PageNumber>1</PageNumber>
 <RequestId>2B17D708-1D6D-49F3-B6D7-478371DDDBE8</RequestId>
 <PageRecordCount>1</PageRecordCount>
</DescribeInstanceAutoRenewalAttributeResponse>
```

**JSON格式**

```
{
 "Items": {
   "Item": [
     {
       "Duration": 1,
       "RegionId": "cn-shanghai",
       "DBInstanceId": "r-xxxxxxxxxxxxxxx",
       "AutoRenew": "true"
     }
   ]
 },
 "TotalRecordCount": 1,
 "PageNumber": 1,
 "RequestId": "2B17D708-1D6D-49F3-B6D7-478371DDDBE8",
 "PageRecordCount": 1
}
```

