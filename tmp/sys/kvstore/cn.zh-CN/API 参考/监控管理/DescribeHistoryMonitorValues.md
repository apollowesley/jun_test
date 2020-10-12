# DescribeHistoryMonitorValues {#reference_nyv_gcf_xdb .reference}

调用该API可以查看实例的历史监控信息。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeHistoryMonitorValues。|
|InstanceId|String|是|实例ID（全局唯一）|
|StartTime|String|是|历史监控开始时间点；采用ISO8601表示法，并使用UTC时间。格式为：`YYYY-MM-DDThh:mm:ssZ`。|
|EndTime|String|是|历史监控结束时间点；采用ISO8601表示法，并使用UTC时间。格式为：`YYYY-MM-DDThh:mm:ssZ`。EndTime必须大于等于StartTime。|
|IntervalForHistory|String|是|历史监控数据间隔，单位m（分钟），取值01m。|
|MonitorKeys|String|否|监控项Key。可通过[DescribeMonitorItems](cn.zh-CN/API 参考/监控管理/DescribeMonitorItems.md#)查询。|
|NodeId|String|否|集群实例指定子节点ID，传入后返回特定节点的监控信息。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|MonitorHistory|String| 以JSON格式返回的监控信息。返回的监控项，参见[查看监控项列表](cn.zh-CN/API 参考/监控管理/DescribeMonitorItems.md#)。

 **说明：** 为了提高数据传输效率，只有非0的监控数据才会返回，其余未显示给出的监控数据均为默认值0。

 |

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=DescribeHistoryMonitorValues
&InstanceId=r-xxxxxxxxxxxxxxx
&StartTime=2018-10-18T12:00:00Z
&EndTime=2018-10-19T12:00:00Z
&IntervalForHistory=01m
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<DescribeHistoryMonitorValuesResponse>
 <monitorHistory>
   <2018-07-09T12:00:13Z>
     <quotaMemory>1073741824</quotaMemory>
     <UsedMemory>5823152</UsedMemory>
   </2018-07-09T12:00:13Z>
   <2018-07-09T12:13:15Z>
     <quotaMemory>1073741824</quotaMemory>
     <UsedMemory>5823152</UsedMemory>
   </2018-07-09T12:13:15Z>
   <2018-07-09T12:14:15Z>
     <quotaMemory>1073741824</quotaMemory>
     <UsedMemory>5823152</UsedMemory>
   </2018-07-09T12:14:15Z>
 </monitorHistory>
 <requestId>F0997EE8-F4C2-4503-9168-85177ED78C70</requestId>
</DescribeHistoryMonitorValuesResponse>
```

**JSON格式**

```
{
    "monitorHistory":{
        "2018-07-09T12:00:13Z":{
            "quotaMemory":1073741824,
            "UsedMemory":5823152
        },
        "2018-07-09T12:13:15Z":{
            "quotaMemory":1073741824,
            "UsedMemory":5823152
        },
        "2018-07-09T12:14:15Z":{
            "quotaMemory":1073741824,
            "UsedMemory":5823152
        }
    },
    "requestId":"F0997EE8-F4C2-4503-9168-85177ED78C70"
}
```

