# DescribeRegions {#reference_yrv_cv2_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

Use to query the data center \(region\) where you can create an instance \).

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|Yes|For more information, see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: DescribeRegions.|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameters\>|-|For more information, see [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|
|RegionIds|List|The RegionIds is a list, in which every element consists of the RegionId and ZoneId. The ZoneId is a string, and multiple ZoneIds are separated by comma \(,\).|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com、
<Common request parameters>
&Action=DescribeRegions
```

## Response example {#section_hjp_tkw_wbb .section}

```

    "RequestId":"535A88D3-5408-4B29-AFD4-07319D97EBC4",
    "RegionIds":
    
            
                "ZoneIds":"cn-qingdao-b",
                "RegionId":"cn-qingdao",
            
            
                "ZoneIds":"cn-shenzhen-a",
                "RegionId":"cn-shenzhen",
            
            
                "ZoneIds":"cn-hangzhou-d,cn-hangzhou-b",
                "RegionId":"cn-hangzhou"
            
            
                "ZoneIds":"cn-beijing-a",
                "RegionId":"cn-beijing"
            }
    

```

