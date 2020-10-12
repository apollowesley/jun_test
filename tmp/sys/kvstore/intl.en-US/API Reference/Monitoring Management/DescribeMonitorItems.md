# DescribeMonitorItems {#reference_xqp_1cf_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to query the list of available monitoring parameters. The results are returned in the format of <parameter:unit\>.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required or not|Description|
|----|----|---------------|-----------|
|<Common request parameters\>|-|Yes|For more information, see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: DescribeMonitorItems.|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameters\>|-|For more information, see [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|
|MonitorItems|List|List of each monitoring parameter that can be viewed.|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
<Common request parameters>
&Action=DescribeMonitorItems
```

## Response example {#section_hjp_tkw_wbb .section}

```

    "MonitorItems" : {
        "MonitorItem" : [{
                "MonitorKey" : "GetQ",
                "Unit" : "Counts/s"
            
                "MonitorKey" : "Flush",
                "Unit" : "Counts/s"
            
                "MonitorKey" : "UsedMemCache",
                "Unit" : "Bytes"
            
                "MonitorKey" : "ReplaceQ",
                "Unit" : "Counts/s"
            
        
    
    "RequestId" : "B906A893-58A3-4644-AC2D-A2C9B08706C1"

```

