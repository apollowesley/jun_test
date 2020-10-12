# DeleteInstance {#reference_ucs_hyx_wdb .reference}

调用该API可以释放实例。

调用释放实例接口的要求如下：

-   实例状态为运行中。
-   实例没有被人为锁定。

**说明：** 预付费实例不能被删除，只能等到期后释放。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DeleteInstance。|
|InstanceId|String|是|实例 ID（全局唯一）|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action=DeleteInstance
&InstanceId=657e361a074646d5
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : "C2225574-1D93-4F8A-B1D5-39FCBAA40660"
}
```

