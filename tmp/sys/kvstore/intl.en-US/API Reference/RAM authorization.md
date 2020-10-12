# RAM authorization {#reference_rv2_slq_kfb .reference}

Resource Access Management \(RAM\) allows you to grant access of resources under an account to RAM users. Skip this section if you do not need to use RAM.

## Resources available for authorization {#section_l52_3mq_kfb .section}

Only one type of ApsaraDB for Redis resources can be authorized in RAM: Instance.

The following table shows how to describe resources to be authorized in RAM.

|Resource type|Resource description in authorization policy|
|-------------|--------------------------------------------|
|Instance|`acs:kvstore:$regionid:$accountid:instance/$instanceid acs:kvstore:$regionid:$accountid:instance/ acs:kvstore:::instance/`|

-   `$regionid` describes a region. Replace it with a region ID or an asterisk \( \* \).
-   `$accountid` describes an account. Replace it with an account ID or an asterisk \( \* \).
-   `$instanceid` describes an instance. Replace it with an instance ID or an asterisk \( \* \).

## Authorization rules {#section_g1j_d4q_kfb .section}

When RAM users request access to Redis resources, ApsaraDB for Redis checks for permissions from RAM to make sure the access is allowed by the resource owner. See the following authentication rules for each supported Redis API.

|Action|Authorization rule|
|------|------------------|
|CreateDBInstance|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|DeleteInstance|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|ModifyInstanceSpec|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|RenewInstance|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|RenewMultiInstance|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|ModifyInstanceAttribute|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|FlushInstance|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|DescribeInstances|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|DescribeInstanceAttribute|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|ModifyInstanceMaintainTime|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|ModifySecurityIps|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|SwitchNetwork|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|ModifyInstanceNetExpireTime|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|CreateBackup|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|ModifyBackupPolicy|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|DescribeBackupPolicy|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|DescribeBackups|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|RestoreInstance|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|DescribeHistoryMonitorValues|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|DescribeInstanceConfig|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|
|ModifyInstanceConfig|`acs:kvstore:$regionid:$accountid:instance/$instanceid`|

