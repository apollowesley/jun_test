# ModifyBackupPolicy {#reference_epw_xw2_xdb .reference}

## 描述 {#section_z1z_2kw_wbb .section}

修改备份策略。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：ModifyBackupPolicy。|
|InstanceId|String|是|实例 ID|
|PreferredBackupTime|String|是|备份时间，格式：`HH:mmZ- HH:mm Z`。|
|PreferredBackupPeriod|String|是|备份周期： -   Monday：周一
-   Tuesday：周二
-   Wednesday：周三
-   Thursday：周四
-   Friday：周五
-   Saturday：周六
-   Sunday：周日

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com、
?<公共请求参数>
&Action= ModifyBackupPolicy
&InstanceId=de5d88e34d004211
&PreferredBackupTime=00:00Z-04:00Z
&PreferredBackupPeriod=Saturday
```

## 返回示例 {#section_hjp_tkw_wbb .section}

```
{
"RequestId" : "5C97648D-C85F-4D58-A71F-7B6750856BF7”
}
```

