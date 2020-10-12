# Redis连接问题排查与解决 {#concept_gm5_rgv_fgb .concept}

云数据库Redis版支持多种连接方式，本文介绍各类连接问题的排查与解决方法。

## Redis与ECS之间的连接问题 {#section_rhp_cmv_fgb .section}

在ECS实例上通过内网地址访问Redis实例需要确保ECS与Redis之间可以相互连接。如果二者无法互访，可能是下列原因引起的。

-   Redis实例和ECS实例不在同一账号下。解决方法：
    -   使用RDB文件将Redis实例迁移到ECS实例所在账号下的同一VPC中，操作方法请参见[使用redis-shake迁移RDB文件内的数据](../../../../cn.zh-CN/用户指南/数据迁移/云下到云上/使用redis-shake迁移RDB文件内的数据.md#)。
    -   [跨账号连接Redis与ECS实例](cn.zh-CN/常见问题/跨账号连接Redis与ECS实例.md#)。
-   ECS与Redis不在相同地域。解决方法：

    在ECS所在地域创建新的Redis实例，将源实例中的数据迁移到新实例中，操作步骤请参见[使用redis-shake迁移RDB文件内的数据](../../../../cn.zh-CN/用户指南/数据迁移/云下到云上/使用redis-shake迁移RDB文件内的数据.md#)。

-   ECS与Redis的网络类型不同，一方是经典网络而另一方是VPC网络。解决方法：
    -   将Redis实例的网络类型转换为VPC，请参见[切换Redis实例的网络类型](../../../../cn.zh-CN/用户指南/实例管理/切换为专有网络.md#)。
    -   [快速实现不同网络的ECS与Redis实例互访](cn.zh-CN/常见问题/快速实现不同网络的ECS与Redis实例互访.md#)。
-   ECS的安全组规则阻塞了对Redis地址和端口的访问。解决方法：

    [添加安全组规则](../../../../cn.zh-CN/安全/安全组/添加安全组规则.md#)，允许访问。

-   Redis的白名单中未加入ECS的内网地址。解决方法：

    [设置Redis白名单](../../../../cn.zh-CN/用户指南/实例管理/设置IP白名单.md#)，将ECS的内网IP加入其中。

    **说明：** 如果出现`Caused by: redis.clients.jedis.exceptions.JedisConnectionException: java.net.ConnectException: 拒绝连接 (Connection refused)`，请检查Redis白名单设置，若白名单设置无误且可以在ECS上[ping](../../../../cn.zh-CN/技术运维问题/网络连接类/使用ping命令检测ECS与Redis之间的连接.md#)通Redis实例，请检查应用中的连接配置。

-   ECS行为异常触发安全策略，导致服务被禁止。如果多台正常连接到Redis的ECS实例中有某个实例出现突发的连接问题，尤其是ECS能[ping](../../../../cn.zh-CN/技术运维问题/网络连接类/使用ping命令检测ECS与Redis之间的连接.md#)通Redis但[telnet](../../../../cn.zh-CN/技术运维问题/网络连接类/使用telnet命令检测Redis端口连通性.md#) 6379端口失败时，可能是该ECS存在异常行为（例如对外攻击）导致服务被禁止。解决方法：

    请检查服务器，在安全组的出方向设置精确的规则，比如限定该ECS只能访问业务需要的地址和端口，此处为Redis实例的6379端口。若问题还不能解决，请提交工单进行详细排查。

-   DNS解析问题。客户端出现`UnknownHostException`或者`failed to connect: r-***************.redis.rds.aliyuncs.com could not be resolved`之类的报错。解决方法：

    用ping或者telnet命令测试Redis连接地址的解析情况，如不成功请检查DNS配置。


**说明：** 如因条件限制无法实施以上解决方案，您可以提交工单重新创建ECS或Redis实例，使二者在同一VPC中。

## 从外网连接Redis {#section_lmy_odi_3hy .section}

如您需要从本地主机连接云数据库Redis版，请参见[外网连接](../../../../cn.zh-CN/快速入门/步骤3：连接实例/外网连接.md#)。

**说明：** 建议您使用阿里云内网通过ECS连接Redis实例，提高安全性，降低网络耗时对Redis性能的影响。

## 忘记密码 {#section_zgc_xqw_fgb .section}

请在控制台[修改密码](../../../../cn.zh-CN/用户指南/实例管理/修改密码.md#)。

## 客户端连接方法 {#section_lfg_znw_fgb .section}

使用Jedis、phpredis、redis-py、C/C++、.net、node-redis、C\#等客户端连接Redis的方法请参见[Redis客户端连接](cn.zh-CN/快速入门/步骤3：连接实例/Redis客户端连接.md#)。

使用阿里云的数据管理工具DMS连接并管理数据库请参见[使用DMS登录Redis](cn.zh-CN/快速入门/步骤3：连接实例/DMS登录云数据库.md#)。

使用redis-cli连接Redis的方法请参见[redis-cli连接](../../../../cn.zh-CN/快速入门/步骤3：连接实例/redis-cli连接.md#)。

**说明：** 如果使用各语言的客户端或者redis-cli连接Redis失败，请先排查[Redis与ECS之间的连接问题](#)。

## 客户端连接问题 {#section_rls_4pw_fgb .section}

-   [Jedis常见问题](cn.zh-CN/常见问题/实例Jedis常见异常汇总.md#)。
-   [DMS中单击**登录数据库**无响应](https://help.aliyun.com/knowledge_detail/72737.html)。
-   [Redis集群版实例常见错误返回信息](../../../../cn.zh-CN/技术运维问题/Redis集群版实例常见错误返回信息.md#)。

## 带宽超限导致连接受限 {#section_ups_trw_fgb .section}

每种规格的Redis实例都有相应的带宽限制，详情请参见[规格性能](../../../../cn.zh-CN/产品简介/规格性能.md#)。在网络带宽资源充足的情况下，云数据库Redis版的带宽限制不生效，当资源不足时，实例的带宽上限开始生效，此时如果流量过大，则业务请求会受到带宽限制。解决方法：

-   如实例的引擎版本为4.0或以上，请参见[缓存分析](../../../../cn.zh-CN/用户指南/缓存分析.md#)找出大key进行优化处理；如实例的引擎版本为4.0以下，您可以通过[分析实例的内存结构](https://help.aliyun.com/knowledge_detail/50037.html)或[使用SCAN命令](cn.zh-CN/常见问题/如何搜索过大的key.md#)的方式找出大key进行优化。
-   升级实例规格，提升带宽。
-   转换为同规格的集群版实例，提升带宽。
-   转换为同规格的读写分离实例，提升带宽，同时可以把大key或者热点key存储在只读实例中，避免其影响其它业务。

## 性能问题导致连接不畅或失败 {#section_trw_txw_fgb .section}

使用KEYS \*、HGETALL 等命令影响了Redis性能，导致线程阻塞等情况，进而出现连接问题。解决方法：

-   禁止线上使用 KEYS、FLUSHALL、FLUSHDB 等命令。禁用方式请参见[禁用高风险命令](../../../../cn.zh-CN/用户指南/参数设置/禁用高风险命令.md#)。
-   查看[监控指标](../../../../cn.zh-CN/用户指南/性能监控/监控指标说明.md#)，找出问题原因并采取针对性的办法。
-   查询慢日志，根据慢日志详情进行优化。您可以在Redis控制台[查看慢日志](../../../../cn.zh-CN/用户指南/日志管理/查询慢日志.md#)或使用SHOW LOG命令查看。
-   如实例的引擎版本为4.0或以上，可以借助LazyFree机制的UNLINK、FLUSHALL ASYNC、FLUSHDB ASYNC命令和相关参数优化业务代码。
-   如实例的引擎版本为4.0或以上，请参见[缓存分析](../../../../cn.zh-CN/用户指南/缓存分析.md#)找出大key进行优化处理；如实例的引擎版本为4.0以下，您可以通过[分析实例的内存结构](https://help.aliyun.com/knowledge_detail/50037.html)或[使用SCAN命令](cn.zh-CN/常见问题/如何搜索过大的key.md#)的方式找出大key进行优化。
-   [优化热点key](../../../../cn.zh-CN/最佳实践/热点Key问题的发现与解决.md#)。如果您使用的是集群版实例，还可以[分析特定子节点中的热点key](../../../../cn.zh-CN/最佳实践/集群实例特定子节点中热点Key的分析方法.md#)。

## 排查思路总结 {#section_pgd_xdx_fgb .section}

当您发现与Redis的连接出现异常时，可以根据本文的内容，从[环境前提](#)、[连接方法](#)、[错误信息](#)、[带宽限制](#)、[性能问题](#)等方面进行排查。

