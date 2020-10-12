# API概览 {#concept_t53_hsn_tdb .concept}

本文汇总了云数据库RDS所有可调用的API，各API的具体信息请参见相关文档。

## 区域管理接口 {#section_c3y_1tl_sfb .section}

|API|描述|
|---|--|
|[DescribeRegions](cn.zh-CN/API 参考/区域管理/DescribeRegions.md#)|地域查询|
|[DescribeZones](cn.zh-CN/API 参考/区域管理/DescribeZones.md#)|可用区查询|
|[MigrateToOtherZone](cn.zh-CN/API 参考/区域管理/MigrateToOtherZone.md#)|迁移可用区|

## 实例生命周期管理接口 { .section}

|API|描述|
|---|--|
|[CreateInstance](cn.zh-CN/API 参考/生命周期管理/CreateInstance.md#)|创建实例|
|[DeleteInstance](cn.zh-CN/API 参考/生命周期管理/DeleteInstance.md#)|释放实例|
|[ModifyInstanceSpec](cn.zh-CN/API 参考/生命周期管理/ModifyInstanceSpec.md#)|变更实例规格|
|[TransformToPrePaid](cn.zh-CN/API 参考/生命周期管理/TransformToPrePaid.md#)|按量付费转包年包月|

## 实例管理接口 { .section}

|API|描述|
|---|--|
|[DescribeDBInstanceNetInfo](cn.zh-CN/API 参考/实例管理/DescribeDBInstanceNetInfo.md#)|查看实例的经典网络访问地址|
|[ModifyInstanceAttribute](cn.zh-CN/API 参考/实例管理/ModifyInstanceAttribute.md#)|修改实例属性|
|[FlushInstance](cn.zh-CN/API 参考/实例管理/FlushInstance.md#)|清空实例|
|[DescribeInstances](cn.zh-CN/API 参考/实例管理/DescribeInstances.md#)|查询实例基础信息|
|[DescribeInstanceAttribute](cn.zh-CN/API 参考/实例管理/DescribeInstanceAttribute.md#)|查询实例详情|
|[DescribeSecurityIps](cn.zh-CN/API 参考/实例管理/DescribeSecurityIps.md#)|查询允许访问实例的IP名单|
|[ModifyInstanceMaintainTime](cn.zh-CN/API 参考/实例管理/ModifyInstanceMaintainTime.md#)|修改实例可运维时间|
|[ModifySecurityIps](cn.zh-CN/API 参考/实例管理/ModifySecurityIps.md#)|修改实例白名单|
|[SwitchNetwork](cn.zh-CN/API 参考/实例管理/SwitchNetwork.md#)|经典网络切换为专有网络|
|[ModifyInstanceNetExpireTime](cn.zh-CN/API 参考/实例管理/ModifyInstanceNetExpireTime.md#)|修改经典网络域名的保留时间|

## 备份恢复接口 { .section}

|API|描述|
|---|--|
|[CreateBackup](cn.zh-CN/API 参考/备份恢复/CreateBackup.md#)|创建备份|
|[ModifyBackupPolicy](cn.zh-CN/API 参考/备份恢复/ModifyBackupPolicy.md#)|修改备份策略|
|[DescribeBackupPolicy](cn.zh-CN/API 参考/备份恢复/DescribeBackupPolicy.md#)|查询备份策略|
|[DescribeBackups](cn.zh-CN/API 参考/备份恢复/DescribeBackups.md#)|显示备份列表|
|[RestoreInstance](cn.zh-CN/API 参考/备份恢复/RestoreInstance.md#)|基于备份集回滚实例|

## 监控管理接口 { .section}

|API|描述|
|---|--|
|[DescribeMonitorItems](cn.zh-CN/API 参考/监控管理/DescribeMonitorItems.md#)|查看监控项列表|
|[DescribeHistoryMonitorValues](cn.zh-CN/API 参考/监控管理/DescribeHistoryMonitorValues.md#)|查看历史监控|

## 参数管理接口 { .section}

|API|描述|
|---|--|
|[DescribeParameters](cn.zh-CN/API 参考/参数管理/DescribeParameters.md#)|查看实例参数配置|
|[ModifyInstanceConfig](cn.zh-CN/API 参考/参数管理/ModifyInstanceConfig.md#)|修改参数配置|

## 相关文档 { .section}

-   [API资源导航](https://developer.aliyun.com/)
-   [API Explorer](https://api.aliyun.com/)
-   [API错误中心](https://error-center.aliyun.com/)

