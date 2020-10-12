# Authorization rules of API {#reference_acn_3c2_xdb .reference}

When a sub-user accesses resources over the APIs, the background checks the caller’s permissions on RAM to ensure that the caller has the response permissions.

Each API determines the resources for permission check according to the involved resources and the semantics of the API. The following table lists the authentication rules for each API.

|Action|Authentication rule|
|------|-------------------|
|CreateDBInstance|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|DeleteInstance|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|ModifyInstanceSpec|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|RenewInstance|acs:kvstore:$regionid:$accountid:instance/$instanceid|
|RenewMultiInstance|acs:kvstore:$regionid:$accountid:instance/$instanceid|
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

