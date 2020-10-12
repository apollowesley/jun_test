# DescribeParameters {#reference_zr3_pqx_wdb .reference}

调用该API可以查询实例参数。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeParameters。|
|DBInstanceId|String|是|实例 ID|
|NodeId|String|否|Sharding实例类型时候需要传入。传入shard节点的实例ID，查询单个组件的性能情况。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|Engine|String|数据库类型|
|EngineVersion|String|数据库版本号|
|ConfigParameters|List|参数列表组成的数组|
|RunningParameters|List|参数列表组成的数组|

|名称|类型|描述|
|--|--|--|
|ParameterName|String|参数名|
|ParameterValue|String|参数默认值|
|ModifiableStatus|String|参数是否可修改的状态。False：不可修改；True：可修改。|
|ForceRestart|String|是否需要重启生效。True：需要重启生效；False：无需重启，提交后即生效。|
|CheckingCode|String|校验代码，参数的可选范围。|
|ParameterDescription|String|参数描述|

|名称|类型|描述|
|--|--|--|
|ParameterName|String|参数名|
|ParameterValue|String|参数默认值|
|ModifiableStatus|Boolean|参数是否可修改的状态。False：不可修改；True：可修改。|
|ForceRestart|Boolean|是否需要重启生效。True：需要重启生效；False：无需重启，提交后即生效。|
|CheckingCode|String|校验代码，参数的可选范围。|
|ParameterDescription|String|参数描述|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?<公共请求参数>
&Action=DescribeParameters
&DBInstanceId=r-bp1f8f808c5e8a94
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
   "ConfigParameters":{
       "Parameter":[

       ]
   },
   "RequestId":"90A7936F-B9A3-4226-9B47-CF1D0EF81604",
   "RunningParameters":{
       "Parameter":[
           {
               "ParameterDescription":"Redis可支持的最大key，value长度",
               "ParameterValue":"4194304",
               "CheckingCode":"[0-9]+",
               "ForceRestart":"false",
               "ModifiableStatus":"true",
               "ParameterName":"#no_loose_max-memcached-allow-request-length"
           },
           {
               "ParameterValue":"512",
               "CheckingCode":"[0-999999999999999]",
               "ForceRestart":"false",
               "ModifiableStatus":"true",
               "ParameterName":"set-max-intset-entries"
           },
           {
               "ParameterValue":"64",
               "CheckingCode":"[0-999999999999999]",
               "ForceRestart":"false",
               "ModifiableStatus":"true",
               "ParameterName":"zset-max-ziplist-value"
           }
       ]
   },
   "EngineVersion":"2.8",
   "Engine":"redis"
}
```

