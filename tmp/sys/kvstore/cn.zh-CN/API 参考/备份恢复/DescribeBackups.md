# DescribeBackups {#reference_z1y_kx2_xdb .reference}

调用该API可以查询备份文件的详细信息，包括备份开始时间、文件大小、备份方式及下载地址等详细信息。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|是|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeBackups。|
|InstanceId|String|是|实例 ID|
|BackupId|String|否|备份集 ID|
|PagSize|String|是|每页记录数，取值：30/50/100。|
|PageNumber|String|是|页码，大于0，且不超过 Integer 的最大值。|
|StartTime|String|是|查询开始时间|
|EndTime|String|是|查询结束时间|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|Backups|List|由 Backup 组成的数组|
|PageNumber|Integer|页码|
|TotalCount|Integer|备份总个数|
|PageSize|Integer|每页记录数|

## Backup 的参数构成 {#section_dsm_2td_xbb .section}

|名称|类型|描述|
|--|--|--|
|BackupId|String|备份集 ID|
|BackupDBNames|String|备份的 DB 名称|
|BackupStatus|String|备份集状态：-   Success：成功
-   Failed：失败

|
|BackupStartTime|String|备份开始时间|
|BackupEndTime|String|备份结束时间|
|BackupType|String|备份类型：-   FullBackup：全量备份
-   IncrementalBackup：增量备份

|
|BackupMode|String|备份模式：-   Automated：自动备份
-   Manual：手工触发备份

|
|BackupMethod|String|备份方法：-   Logical：逻辑备份
-   Physical：物理备份

|
|BackupDownloadURL|String|备份文件的 URL 下载地址|
|BackupSize|String|备份集大小|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
?<公共请求参数>
&Action= DescribeBackups
&InstanceId= de5d88e34d004211
& PageNumber=1
&PageSize=10
&InstanceId=de5d88e34d004211
```

## 返回示例 {#section_hjp_tkw_wbb .section}

``` {#codeblock_ylw_djd_xdb}
{
    " Backups " : {
        " Backup " : [{
           “BackupId":"187709043",
           "BackupDBNames":"all",
          "BackupStatus":"Success",
         “BackupType”:” FullBackup”,
"BackupStartTime":"2017-10-18T10:34:52Z",
"BackupEndTime":"2017-10-18T10:36:06Z",
"BackupMode":"Manual",
"BackupMethod":"Physical",
"BackupDownloadURL":"https://rdsbak-st-v2.oss-cn-shenzhen.aliyuncs.com/custins4588367/hins3402581_data_20171018183408.rdb?OSSAccessKeyId=LTAITfQ7krsrEwRn&Expires=1508409386&Signature=rYyFYVdMOhhTJ0TAPafGc6oJSuk%3D",
"BackupSize":"1024"
}
        ]
    },
    "PageNumber" : 1,
    "PageSize" : 10,
    "TotalCount" : 1,
"RequestId" : "5C97648D-C85F-4D58-A71F-7B6750856BF7”
}
```

