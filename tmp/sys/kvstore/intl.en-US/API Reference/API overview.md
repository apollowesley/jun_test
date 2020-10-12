# API overview {#concept_t53_hsn_tdb .concept}

## Instance lifecycle management APIs { .section}

|API|Description|
|---|-----------|
|CreateInstance|Creates an instance.|
|DeleteInstance|Releases an instance.|
|ModifyInstanceSpec|Modifies an instance.|
|RenewInstance|Renews an instance.|
|RenewMultiInstance|Renews instances in batches.|
|TransformToPrePaid| |

## Instance management APIs { .section}

|API|Description|
|---|-----------|
|ModifyInstanceAttribute|Modifies attributes of an instance.|
|FlushInstance|Clears data of an instance.|
|DescribeInstances|Queries basic information about an instance.|
|DescribeInstanceAttribute|Queries details of an instance.|
|DescribeSecurityIps|Queries the IP whitelists of an instance.|
|ModifyInstanceMaintainTime|Modifies the maintenance time of an instance.|
|ModifySecurityIps|Modifies the whitelist of an instance.|
|DescribeRegions|Queries the region where an instance can be sold.|
|SwitchNetwork|Modifies the network type.|
|ModifyInstanceNetExpireTime|Modifies the retention period of a classic network connection address.|

## Backup recovery APIs { .section}

|API|Description|
|---|-----------|
|CreateBackup|Create a backup.|
|ModifyBackupPolicy|Modifies a backup policy.|
|DescribeBackupPolicy|Queries a backup policy.|
|DescribeBackups|Displays a backup list.|
|RestoreInstance|Rolls an instance back based on a backup set.|

## Monitoring management APIs { .section}

|API|Description|
|---|-----------|
|DescribeMonitorItems|Views a metric list.|
|DescribeHistoryMonitorValues|Views the historical monitoring data.|

## Parameter management APIs { .section}

|API|Description|
|---|-----------|
|DescribeInstanceConfig|Views the parameter configuration of an instance.|
|ModifyInstanceConfig|Modifies the parameter configuration of an instance.|

