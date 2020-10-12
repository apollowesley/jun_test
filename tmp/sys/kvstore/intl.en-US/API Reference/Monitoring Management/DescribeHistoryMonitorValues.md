# DescribeHistoryMonitorValues {#reference_nyv_gcf_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to view the historical monitoring data of an instance.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|Yes|For more information, see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: DescribeHistoryMonitorValues.|
|InstanceId|String|Yes|Instance ID|
|StartTime|String|Yes|Start time of the historical monitoring data. The time format follows the ISO8601 notation, , and the UTC time is used in the format of `YYYY-MM-DDThh:mm:ssZ`.|
|EndTime|String|Yes|End time of the historical monitoring data. The time format follows the ISO8601 notation, and the UTC time is used in the format of `YYYY-MM-DDThh:mm:ssZ`. The value of EndTime must be greater than or equal to that of StartTime.|
|IntervalForHistor|String|Yes|The interval between historical monitoring data. Unit: m \(minutes\). Value: 01m, 05m, 15m, or 60m.|
|MonitorKeys|String|No|Metric key. The value of this parameter is queried using the DescribeMonitorItems function.|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameters\>|-|For more information, see [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|
|MonitorHistory|String| Monitoring information returned in JSON format. For more information about returned metrics, see [View a metric list](intl.en-US/API Reference/Monitoring Management/DescribeMonitorItems.md#).

 **Note:** To improve the data transmission efficiency, only metrics whose values are not 0 are returned. Metrics not displayed are all set to the default value 0.

 |

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com、
<Public request parameters>
&Action= DescribeHistoryMonitorValues
&InstanceId= de5d88e34d004211
&EndTime=2014-11-27T12%3A02%3A00Z
&StartTime=2014-11-27T12%3A00%3A00Z
&IntervalForHistory=01m
```

## Response example {#section_hjp_tkw_wbb .section}

```

"MonitorHistory" :
"{"2014-11-27T12:00:00Z":{"IsConnectControl":false,"IsFlowControl":false,"ItemCount":1,"QuotaConnection":500,"QuotaFlow":15360,"QuotaMemCache":1073741824,"QuotaQps":9000,"UsedMemCache":14},
"2014-11-27T12:01:00Z":{"IsConnectControl":false,"IsFlowControl":false,"ItemCount":1,"QuotaConnection":500,"QuotaFlow":15360,"QuotaMemCache":1073741824,"QuotaQps":9000,"UsedMemCache":14},
"2014-11-27T12:02:00Z":{"IsConnectControl":false,"IsFlowControl":false,"ItemCount":1,"QuotaConnection":500,"QuotaFlow":15360,"QuotaMemCache":1073741824,"QuotaQps":9000,"UsedMemCache":14}

"RequestId" : "5C97648D-C85F-4D58-A71F-7B6750856BF7"

```

