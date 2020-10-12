# API 的鉴权规则 {#reference_acn_3c2_xdb .reference}

当子用户通过 API 进行资源访问时，后台向 RAM 进行权限检查，以确保调用者拥有响应权限。

每个不同的 API 会根据涉及到的资源以及 API 的语义来确定需要检查哪些资源的权限。具体地，每个 API 的鉴权规则见下表。

|Action|鉴权规则|
|------|----|
|CreateDBInstance|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|DeleteInstance|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|ModifyInstanceSpec|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|RenewInstance|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|RenewMultiInstance|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|TransformToPrePaid|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|ModifyInstanceAttribute|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|FlushInstance|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|DescribeInstances|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|DescribeInstanceAttribute|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|ModifyInstanceMaintainTime|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|ModifySecurityIps|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|SwitchNetwork|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|ModifyInstanceNetExpireTime|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|CreateBackup|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|ModifyBackupPolicy|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|DescribeBackupPolicy|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|DescribeBackups|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|RestoreInstance|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|DescribeHistoryMonitorValues|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|DescribeInstanceConfig|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|ModifyInstanceConfig|acs:kvstore:$regionid:$accountid:instance/$instanceid|

