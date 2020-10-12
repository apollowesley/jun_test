# API概览 {#doc_3170 .concept}

云数据库 Redis 版提供以下相关API接口。

## 生命周期管理 {#section_wnc_4ph_gxs .section}

|API|描述|
|---|--|
|[DescribeAvailableResource](cn.zh-CN/API参考/生命周期管理/DescribeAvailableResource.md)|调用DescribeAvailableResource查询指定可用区内可创建的实例。|
|[CreateInstance](cn.zh-CN/API参考/生命周期管理/CreateInstance.md)|调用CreateInstance创建一个Redis实例。|
|[ModifyInstanceSpec](cn.zh-CN/API参考/生命周期管理/ModifyInstanceSpec.md)|调用ModifyInstanceSpec变更Redis实例的规格。|
|[TransformToPrePaid](cn.zh-CN/API参考/生命周期管理/TransformToPrePaid.md)|调用TransformToPrePaid将按量付费的Redis实例转换为包年包月（预付费）实例。|
|[DeleteInstance](cn.zh-CN/API参考/生命周期管理/DeleteInstance.md)|调用DeleteInstance释放Redis实例。|

## 区域管理 {#section_nne_l2y_9fr .section}

|API|描述|
|---|--|
|[MigrateToOtherZone](cn.zh-CN/API参考/区域管理/MigrateToOtherZone.md)|调用MigrateToOtherZone将Redis实例迁移到同地域内的其它可用区。|
|[DescribeRegions](cn.zh-CN/API参考/区域管理/DescribeRegions.md)|调用DescribeRegions查询可创建Redis实例的地域。|
|[DescribeZones](cn.zh-CN/API参考/区域管理/DescribeZones.md)|调用DescribeZones查询支持Redis的可用区。|

## 连接管理 {#section_7sr_b7z_is7 .section}

|API|描述|
|---|--|
|[AllocateInstancePublicConnection](cn.zh-CN/API参考/连接管理/AllocateInstancePublicConnection.md)|调用AllocateInstancePublicConnection为Redis实例申请外网连接地址。|
|[ReleaseInstancePublicConnection](cn.zh-CN/API参考/连接管理/ReleaseInstancePublicConnection.md)|调用ReleaseInstancePublicConnection释放Redis实例的外网连接地址。|
|[ModifyIntranetAttribute](cn.zh-CN/API参考/连接管理/ModifyIntranetAttribute.md)|调用ModifyIntranetAttribute临时调整Redis实例的内网带宽。|
|[DescribeIntranetAttribute](cn.zh-CN/API参考/连接管理/DescribeIntranetAttribute.md)|调用DescribeIntranetAttribute查询Redis实例当前的内网带宽。如果使用了临时调整带宽功能，还可查询临时带宽的过期时间。|

## 实例管理 {#section_lfn_lwx_cij .section}

|API|描述|
|---|--|
|[DescribeDBInstanceNetInfo](cn.zh-CN/API参考/实例管理/DescribeDBInstanceNetInfo.md)|调用DescribeDBInstanceNetInfo查看Redis实例的网络信息。|
|[DescribeInstanceAttribute](cn.zh-CN/API参考/实例管理/DescribeInstanceAttribute.md)|调用DescribeInstanceAttribute查询Redis实例的详细信息。|
|[DescribeInstances](cn.zh-CN/API参考/实例管理/DescribeInstances.md)|调用DescribeInstances查询一个或多个Redis实例的信息。|
|[DescribeLogicInstanceTopology](cn.zh-CN/API参考/实例管理/DescribeLogicInstanceTopology.md)|调用DescribeLogicInstanceTopology查询Redis实例的逻辑拓扑结构。|
|[FlushInstance](cn.zh-CN/API参考/实例管理/FlushInstance.md)|调用FlushInstance清空Redis实例中的数据，不可恢复。|
|[ModifyInstanceAttribute](cn.zh-CN/API参考/实例管理/ModifyInstanceAttribute.md)|调用ModifyInstanceAttribute修改Redis实例的属性，包括名称和密码。|
|[ModifyInstanceMaintainTime](cn.zh-CN/API参考/实例管理/ModifyInstanceMaintainTime.md)|调用ModifyInstanceMaintainTime修改Redis实例的可维护时间段，阿里云将在您设定的可维护时间段内对Redis实例进行例行维护。|
|[ModifyInstanceNetExpireTime](cn.zh-CN/API参考/实例管理/ModifyInstanceNetExpireTime.md)|若Redis实例之前执行过由经典网络向VPC网络切换，并保留了经典网络连接地址，则可调用ModifyInstanceNetExpireTime延长经典网络连接地址的保存时间。|
|[ModifyDBInstanceConnectionString](cn.zh-CN/API参考/实例管理/ModifyDBInstanceConnectionString.md)|调用ModifyDBInstanceConnectionString修改Redis实例的连接地址。|
|[SwitchNetwork](cn.zh-CN/API参考/实例管理/SwitchNetwork.md)|调用SwitchNetwork切换Redis实例的网络类型，支持从经典网络切换为VPC网络。|
|[ModifyInstanceMajorVersion](cn.zh-CN/API参考/实例管理/ModifyInstanceMajorVersion.md)|调用ModifyInstanceMajorVersion升级Redis实例的大版本。|
|[ModifyInstanceMinorVersion](cn.zh-CN/API参考/实例管理/ModifyInstanceMinorVersion.md)|调用ModifyInstanceMinorVersion升级Redis实例的小版本。|
|[RestartInstance](cn.zh-CN/API参考/实例管理/RestartInstance.md)|调用RestartInstance重启运行中的Redis实例。|

## 续费管理 {#section_846_9pp_u4o .section}

|API|描述|
|---|--|
|[ModifyInstanceAutoRenewalAttribute](cn.zh-CN/API参考/续费管理/ModifyInstanceAutoRenewalAttribute.md)|调用ModifyInstanceAutoRenewalAttribute开启或者关闭Redis实例的到期前自动续费功能。|
|[DescribePrice](cn.zh-CN/API参考/续费管理/DescribePrice.md)|调用DescribePrice查询创建Redis实例、升级配置或续费等操作产生的费用。|
|[DescribeInstanceAutoRenewalAttribute](cn.zh-CN/API参考/续费管理/DescribeInstanceAutoRenewalAttribute.md)|调用DescribeInstanceAutoRenewalAttribute查看Redis实例的自动续费情况。|

## 账号管理 {#section_udx_s4f_mdh .section}

|API|描述|
|---|--|
|[DescribeAccounts](cn.zh-CN/API参考/账号管理/DescribeAccounts.md)|调用DescribeAccounts查找指定Redis实例的帐户列表信息或实例中某个账号的信息。|
|[ModifyAccountDescription](cn.zh-CN/API参考/账号管理/ModifyAccountDescription.md)|调用ModifyAccountDescription修改Redis账号的描述。|
|[ResetAccountPassword](cn.zh-CN/API参考/账号管理/ResetAccountPassword.md)|调用ResetAccountPassword重置Redis账号的密码。|
|[CreateAccount](cn.zh-CN/API参考/账号管理/CreateAccount.md)|调用CreateAccount可以在Redis实例中创建有特定权限的账号。|
|[DeleteAccount](cn.zh-CN/API参考/账号管理/DeleteAccount.md)|调用DeleteAccount删除一个Redis账号。|
|[GrantAccountPrivilege](cn.zh-CN/API参考/账号管理/GrantAccountPrivilege.md)|调用GrantAccountPrivilege修改Redis账号的权限。|

## 备份恢复 {#section_mep_bs3_vjn .section}

|API|描述|
|---|--|
|[CreateBackup](cn.zh-CN/API参考/备份恢复/CreateBackup.md)|调用CreateBackup为Redis实例创建数据备份。|
|[ModifyBackupPolicy](cn.zh-CN/API参考/备份恢复/ModifyBackupPolicy.md)|调用ModifyBackupPolicy修改Redis实例的自动备份策略。|
|[DescribeBackupPolicy](cn.zh-CN/API参考/备份恢复/DescribeBackupPolicy.md)|调用DescribeBackupPolicy查询Redis实例的备份策略，包括备份周期、备份时间等。|
|[DescribeBackups](cn.zh-CN/API参考/备份恢复/DescribeBackups.md)|调用DescribeBackups查询Redis实例的备份文件信息。|
|[RestoreInstance](cn.zh-CN/API参考/备份恢复/RestoreInstance.md)|调用RestoreInstance将备份文件中的数据恢复到指定的Redis实例中。|

## 监控管理 {#section_3ho_eyn_3hx .section}

|API|描述|
|---|--|
|[DescribeMonitorItems](cn.zh-CN/API参考/监控管理/DescribeMonitorItems.md)|调用DescribeMonitorItems查询Redis监控项列表。|
|[DescribeHistoryMonitorValues](cn.zh-CN/API参考/监控管理/DescribeHistoryMonitorValues.md)|调用DescribeHistoryMonitorValues查看Redis实例的历史监控信息。|

## 日志管理 {#section_tsv_mpe_wsd .section}

|API|描述|
|---|--|
|[DescribeAuditRecords](cn.zh-CN/API参考/日志管理/DescribeAuditRecords.md)|调用DescribeAuditRecords查看Redis实例的审计日志。|
|[DescribeRunningLogRecords](cn.zh-CN/API参考/日志管理/DescribeRunningLogRecords.md)|调用DescribeRunningLogRecords查询Redis实例的运行日志列表。|
|[DescribeSlowLogRecords](cn.zh-CN/API参考/日志管理/DescribeSlowLogRecords.md)|调用DescribeSlowLogRecords查询Redis实例在指定时间内产生的慢日志。|
|[ModifyAuditLogConfig](cn.zh-CN/API参考/日志管理/ModifyAuditLogConfig.md)|调用ModifyAuditLogConfig设置审计日志的保留天数。|

## 网络安全 {#section_ueg_nnx_toe .section}

|API|描述|
|---|--|
|[DescribeSecurityIps](cn.zh-CN/API参考/网络安全/DescribeSecurityIps.md)|调用DescribeSecurityIps查询允许访问Redis实例的IP名单。|
|[ModifySecurityIps](cn.zh-CN/API参考/网络安全/ModifySecurityIps.md)|调用ModifySecurityIps设置Redis实例的IP白名单。|
|[ModifyInstanceSSL](cn.zh-CN/API参考/网络安全/ModifyInstanceSSL.md)|调用ModifyInstanceSSL设置Redis实例的SSL加密模式。|
|[ModifyInstanceVpcAuthMode](cn.zh-CN/API参考/网络安全/ModifyInstanceVpcAuthMode.md)|调用ModifyInstanceVpcAuthMode开启或关闭免密访问。开启免密访问后，同一VPC内的云服务器不使用密码就可以访问Redis，同时仍然支持通过用户名及密码的方式连接Redis。|
|[DescribeInstanceSSL](cn.zh-CN/API参考/网络安全/DescribeInstanceSSL.md)|调用DescribeInstanceSSL查看Redis实例是否开启了SSL加密认证。|

## 参数管理 {#section_cbw_fi4_dby .section}

|API|描述|
|---|--|
|[ModifyInstanceConfig](cn.zh-CN/API参考/参数管理/ModifyInstanceConfig.md)|调用ModifyInstanceConfig修改Redis实例的配置参数。|
|[DescribeParameters](cn.zh-CN/API参考/参数管理/DescribeParameters.md)|调用DescribeParameters查询Redis实例的配置参数和运行参数。|

## 标签管理 {#section_f3l_jr1_sql .section}

|API|描述|
|---|--|
|[TagResources](cn.zh-CN/API参考/标签管理/TagResources.md)|调用TagResources为一个或多个Redis实例绑定标签。|
|[ListTagResources](cn.zh-CN/API参考/标签管理/ListTagResources.md)|调用ListTagResources查询绑定了指定标签的Redis实例或者查询指定实例绑定的标签。|
|[UntagResources](cn.zh-CN/API参考/标签管理/UntagResources.md)|调用UntagResources将标签从Redis实例解绑。|

## 性能优化 {#section_rxf_zmj_eds .section}

|API|描述|
|---|--|
|[CreateCacheAnalysisTask](cn.zh-CN/API参考/性能优化/CreateCacheAnalysisTask.md)|调用CreateCacheAnalysisTask手动发起缓存分析任务。|
|[DescribeCacheAnalysisReport](cn.zh-CN/API参考/性能优化/DescribeCacheAnalysisReport.md)|调用DescribeCacheAnalysisReport查看Redis实例在指定日期中的缓存分析报告。|
|[DescribeCacheAnalysisReportList](cn.zh-CN/API参考/性能优化/DescribeCacheAnalysisReportList.md)|调用DescribeCacheAnalysisReportList查看Redis实例的缓存分析报告列表。|

