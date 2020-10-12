# DescribeBackupPolicy {#reference_utv_cx2_xdb .reference}

调用该API能够查询用户设置的备份策略，可以查询出实例的备份周期、备份时间等详细信息。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeBackupPolicy。|
|InstanceId|String|是|实例 ID|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|BackupRetentionPeriod|String|备份保留天数|
|PreferredBackupTime|String|备份时间，格式：HH:mmZ- HH:mm Z。|
|PreferredBackupPeriod|String|备份周期：-   Monday：周一
-   Tuesday：周二
-   Wednesday：周三
-   Thursday：周四
-   Friday：周五
-   Saturday：周六
-   Sunday：周日

|
|PreferredNextBackupTime|String|下次备份时间|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= DescribeBackupPolicy
&InstanceId= de5d88e34d004211
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"BackupRetentionPeriod":"7"
"PreferredBackupTime":"06:00Z-07:00Z"
"PreferredNextBackupTime":"2017-10-19T06:16Z"
"PreferredBackupPeriod":"Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday"
"RequestId" : "5C97648D-C85F-4D58-A71F-7B6750856BF7”
}
```

