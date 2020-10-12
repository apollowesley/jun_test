# DescribeBackups {#reference_z1y_kx2_xdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to query the details of a backup file, including the backup start time, file size, backup mode, download address, and other information.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|Yes|see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|Required parameter. Value: DescribeBackups.|
|InstanceId|String|Yes|Instance ID|
|BackupId|String|No|Backup set ID|
|PagSize|String|Yes|Number of records per page. Supported values: 30, 50, and 100.|
|PageNumber|String|Yes|Page number. It must be greater than 0 and cannot exceed the maximum value of the given Integer.|
|StrarTime|String|Yes|Query start time|
|EndTime|String|Yes|Query end time|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common return parameter\>|-|See [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|
|Backups|List|An array composed  of Backups|
|PageNumber|Integer|Page number|
|TotalCount|Integer|Total number of backups|
|PageSize|Integer|Number of records per page|

## Backup parameter structure {#section_dsm_2td_xbb .section}

|Name|Type|Description|
|----|----|-----------|
|BackupId|String|Backup set ID|
|BackupDBNames|String|Backup database name|
|BackupStatus|String|Backup set status:-   Success
-   Failed

|
|BackupStartTime|String|Backup start time|
|BackupEndTime|String|Backup end time|
|BackupType|String|Backup Type:-   Fullbackup
-   IncrementalBackup

|
|BackupMode|String|Backup mode:-   Automated
-   Manual

|
|BackupMethod|String|Backup method:-   Logical
-   Physical

|
|BackupDownloadURL|String|Download address of the backup file|
|BackupSize|String|Backup size|

## Example {#section_d3l_4kw_wbb .section}

```
Https://r-kvstore.aliyuncs.com
?<Common request parameter>
&Action= DescribeBackups
&InstanceId= de5d88e34d004211
& Pagenumber = 1
& Pagesize = 10
&InstanceId=de5d88e34d004211
```

## Response example {#section_hjp_tkw_wbb .section}

``` {#codeblock_ylw_djd_xdb}


    " Backups " : {
        " Backup " : [{
           “BackupId":"187709043",
           "BackupDBNames":"all",
          "BackupStatus":"Success",
         Backuptype: fullbackup ",
"BackupStartTime":"2017-10-18T10:34:52Z",
"BackupEndTime":"2017-10-18T10:36:06Z",
"BackupMode":"Manual",
"BackupMethod":"Physical",
"BackupDownloadURL":"https://rdsbak-st-v2.oss-cn-shenzhen.aliyuncs.com/custins4588367/hins3402581_data_20171018183408.rdb?OSSAccessKeyId=AAAAAAAAAAAAAAAA&Expires=1508409386&Signature=rYyFYVdMOhhTJ0TAPafGc6oJSuk%3D",
"BackupSize":"1024"

        
    
    "PageNumber" : 1,
    "PageSize" : 10,
    "TotalCount" : 1,
"RequestId" : "5C97648D-C85F-4D58-A71F-7B6750856BF7”

```

