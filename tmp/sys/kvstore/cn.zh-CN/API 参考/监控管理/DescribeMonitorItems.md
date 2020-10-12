# DescribeMonitorItems {#reference_xqp_1cf_xdb .reference}

调用该API可以查询监控项列表，以<参数:单位\>的形式返回。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeMonitorItems。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|MonitorItems|List|每个可查看的监控参数 List。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action=DescribeMonitorItems
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
    "MonitorItems" : {
        "MonitorItem" : [{
                "MonitorKey" : "GetQ",
                "Unit" : "Counts/s"
            }, {
                "MonitorKey" : "Flush",
                "Unit" : "Counts/s"
            }, {
                "MonitorKey" : "UsedMemCache",
                "Unit" : "Bytes"
            }, {
                "MonitorKey" : "ReplaceQ",
                "Unit" : "Counts/s"
            }
        ]
    },
    "RequestId" : "B906A893-58A3-4644-AC2D-A2C9B08706C1"
}
```

