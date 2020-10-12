# ModifyInstanceSpec {#reference_fmf_4yx_wdb .reference}

调用该API可以变更实例规格。

实例规格详情请参见[实例规格表](intl.zh-CN/API 参考/附表/实例规格表.md#)。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：ModifyInstanceSpec。|
|InstanceId|String|是|实例 ID（全局唯一）|
|InstanceClass|String|是|申请的 Redis 实例规格，详情参见[实例规格表](intl.zh-CN/API 参考/附表/实例规格表.md#)。|
|EffectiveTime|String|否| 变更生效时间：

 -   Immediately（立即生效）
-   MaintainTime（运维时间生效）

 |

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共请求参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|OrderId|String|订单 ID|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=ModifyInstanceSpec
&InstanceId=r-xxxxxxxxxxxxxxx
&InstanceClass=redis.master.2xlarge.default
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML示例**

```
<ModifyInstanceSpecResponse>
 <OrderId>202636204910941</OrderId>
 <RequestId>426F1356-B6EF-4DAD-A1C3-DE53B9DAF586</RequestId>
</ModifyInstanceSpecResponse>
```

**JSON示例**

```
{
 "OrderId":"202636204910941",
 "RequestId":"426F1356-B6EF-4DAD-A1C3-DE53B9DAF586"
}
```

