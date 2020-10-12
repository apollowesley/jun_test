# RAM鉴权 {#reference_rv2_slq_kfb .reference}

通过阿里云账号创建的实例都是该账号拥有的资源。默认情况下，账号对自己的资源拥有完整的操作权限。使用阿里云的RAM（Resource Access Management）服务，您可以将云账号下的资源的访问及管理权限授予RAM中的子用户。如果您不需要使用RAM，请略过此章节。

## 可授权的资源类型 {#section_l52_3mq_kfb .section}

云数据库Redis版可以在RAM中进行授权的资源类型仅有一种：instance。

在通过RAM进行授权时，资源的描述方式如下：

|资源类型|授权策略中的资源描述方式|
|----|------------|
|Instance|acs:kvstore:$regionid:$accountid:instance/$instanceid acs:kvstore:$regionid:$accountid:instance/ acs:kvstore:::instance/|

其中，所有$regionid应为某个region的ID，或者`*`；所有$instanceid应为某个instance的ID，或者`*`；以此类推，$account-id是云帐号的数字ID，可以用`*`代替。

## 可对资源进行授权的Action {#section_kmm_14q_kfb .section}

RAM中可对资源进行授权的Action如下：

-   CreateInstance
-   DeleteInstance
-   ModifyInstanceSpec
-   RenewInstance
-   RenewMultiInstance
-   TransformToPrePaid
-   ModifyInstanceAttribute
-   FlushInstance
-   DescribeInstances
-   DescribeInstanceAttribute
-   ModifyInstanceMaintainTime
-   ModifySecurityIps
-   SwitchNetwork
-   ModifyInstanceNetExpireTime
-   CreateBackup
-   ModifyBackupPolicy
-   DescribeBackupPolicy
-   DescribeBackups
-   RestoreInstance
-   DescribeHistoryMonitorValues
-   DescribeInstanceConfig
-   ModifyInstanceConfig

## API的鉴权规则 {#section_g1j_d4q_kfb .section}

当子用户通过API进行资源访问时，后台向RAM进行权限检查，以确保调用者拥有响应权限。

每个不同的API会根据涉及到的资源以及API的语义来确定需要检查哪些资源的权限。具体地，每个API的鉴权规则见下表。

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

