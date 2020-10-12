# CreateInstance {#reference_l2q_byx_wdb .reference}

创建一个实例。实例规格请参见[实例规格表](intl.zh-CN/API 参考/附表/实例规格表.md#)。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|操作接口名，取值：CreateInstance。|
|InstanceName|String|否|实例名称。 名称为2-128个字符，以大小写字英文母或中文开头，不支持字符@/:=”<\>\{\[\]\}和空格。|
|Password|String|否|实例密码。 字符类型，8-30个字符，需同时包含大写字母、小写字母和数字。 **说明：** 暂时不支持 !<\>\(\)\[\]\{\},\`~.-\_@\#$%^&\* 之类的特殊符号。

|
|InstanceClass|String|是|申请的 Redis 实例规格。参见实例规格表。|
|RegionId|String|是|申请的存储实例所在区域。通过函数 DescribeRegions 查看可用的数据中心。|
|ZoneId|String|否|RegionId 下的可用区。通过函数 DescribeRegions 查看可用的可用区。|
|ChargeType|String|否|付费类型：PrePaid 或 PostPaid，默认为 PostPaid。|
|Period|Long|否|付费周期，付费类型为 PrePaid 时必须，单位为月，可选值：1-9，12，24，36 。|
|Token|String|否|用于保证请求的幂等性。由客户端生成该参数值，要保证在不同请求间唯一，大小写敏感、不超过64个 ASCII 字符。参见如何保证幂等性。|
|NetworkType|String|否| 网络类型：

 -   CLASSIC：经典网络
-   VPC：专有网络

 网络默认经典网络。

 |
|VpcId|String|否|VPC 网络 ID。|
|VSwitchId|String|否|虚拟交换机 ID。|
|PrivateIp|String|否|私有 IP 地址。|
|SrcDBInstanceId|String|否|基于某个实例的备份集创建实例有效，输入产生备份集的实例 ID。|
|BackupId|String|否|基于某个实例的备份集创建实例有效，输入产生备份集 ID。通过调用 DescribeBackups 查询 BackupId。|
|InstanceType|String|否|实例类型，取值：Redis，Memcache。 默认为 Redis。|
|EngineVersion|String|否|版本类型，取值：2.8 或 4.0。 默认值为 2.8。|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|参数类型|描述|
|--|----|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|InstanceId|String|实例 ID （全局唯一）。|
|InstanceName|String|实例名称。|

**说明：** 出于历史兼容性考虑，该函数会有些返回结构在文档中并未提及（如 Config，Region 等），后续阿里云将逐步把这些字段删除，请您在调用 API 时不要依赖这些在文档中没有涉及的返回字段。

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action=CreateInstance
&RegionId=cn-hangzhou
&Password=Qa123456
&InstanceClass= redis.master.small.default
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"InstanceId":"736538d0a6894665",
"InstanceName":"736538d0a6894665"
}
```

