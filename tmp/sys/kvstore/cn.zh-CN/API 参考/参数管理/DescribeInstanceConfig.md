# DescribeInstanceConfig {#reference_zr3_pqx_wdb .reference}

## 描述 {#section_z1z_2kw_wbb .section}

查看实例的配置参数。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeInstanceConfig。|
|InstanceId|String|是|实例 ID|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|Config|String|实例的配置参数。参见[实例配置参数表](cn.zh-CN/API 参考/附表/实例配置参数表.md#)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= DescribeInstanceConfig
&InstanceId= de5d88e34d004211
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"Config":"{"EvictionPolicy":"volatile-lru","hash-max-ziplist-entries":512,
"hash-max-ziplist-value":64,"list-max-ziplist-entries":512,
"list-max-ziplist-value":64,"set-max-intset-entries":512,
"zset-max-ziplist-entries":128,"zset-max-ziplist-value":64},
"RequestId":"59A58517-F8FF-44E5-B90F-F386DB3E4AB8"
}
```

