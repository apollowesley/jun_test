# DescribeInstances {#reference_d5w_1df_xdb .reference}

调用该API可以查询账户下的某一个或多个实例信息。

## 请求参数 {#section_fn4_gkw_wbb .section}

|名称|类型|是否必须|描述|
|--|--|----|--|
|<公共请求参数\>|-|-|参见[公共请求参数](intl.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值：DescribeInstances。|
|InstanceIds|String|否|实例 ID（全局唯一）。指定实例 ID 时需输入。若查询多个实例 ID 时，使用英文半角逗号（“,”）分隔各 ID；置空时默认为查询该用户名下所有实例。|
|InstanceStatus|String|否| 按状态过滤所需返回的实例：

 -   Normal（正常）
-   Creating（创建中）
-   Changing（修改中）
-   Inactive（被禁用）
-   Flushing（清除中）
-   Released（已释放）
-   Transforming（转换中）
-   Available（使用中）
-   Unavailable（服务停止）
-   Error（创建失败）
-   Migrating（迁移中）
-   BackupRecovering（备份恢复中）
-   MinorVersionUpgrading（小版本升级中）
-   NetworkModifying（网络变更中）
-   SSLModifying（SSL变更中）
-   MajorVersionUpgrading （大版本升级中，可正常访问）

 |
|ChargeType|String|否|按付费类型过滤所需返回的实例：-   PrePaid（预付费）
-   PostPaid（后付费）

 |
|RegionId|String|是|可以调用[DescribeRegions](intl.zh-CN/API 参考/区域管理/DescribeRegions.md#)查询 RegionId。|
|InstanceType|String|否|引擎类型过滤：-   Memcache
-   Redis

|
|PageNumber|Integer|否|实例状态列表的页码，起始值为1，默认值为1。|
|PageSize|Integer|否|分页查询时设置的每页行数，最大值50行，默认值为10。|
|EngineVersion|String|否|数据库版本过滤：-   2.8
-   4.0

|
|InstanceClass|String|否|按实例规格过滤，请参见[实例规格表](intl.zh-CN/API 参考/附表/实例规格表.md#)。|
|ArchitectureType|String|否|指定架构类型返回实例列表：-   cluster（集群版）
-   standard（标准版）
-   SplitRW（读写分离版）
-   NULL（所有类型，默认值）

|
|VpcId|String|否|按VPC的ID过滤。|
|VSwitchId|String|否|按路由器的ID过滤。|
|SearchKey|String|否|按实例名称查询。|
|Expired|String|否|按即将到期的实例查询。|
|ZoneId|String|否|按可用区过滤，例如：cn-hangzhou-b。|
|NetworkType|String|否|按网络类型过滤：-   CLASSIC（经典网络）
-   VPC（VPC 网络）

|

## 返回参数 {#section_e4w_jkw_wbb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](intl.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|
|Instances|List|由 Instance 组成的数组|
|TotalCount|Integer|实例总个数|
|PageNumber|Integer|实例列表的页码|
|PageSize|Integer|输入时设置的每页行数|

## Instances 参数说明 {#section_f2s_tpw_wbb .section}

|名称|**类型**|**描述**|
|--|------|------|
|ReplacateId|String|参数名|
|InstanceId|String|实例 ID （全局唯一）|
|SearchKey|String|实例名称|
|ConnectionDomain|String|实例连接域名（仅支持内网访问）|
|Port|Long|连接端口|
|UserName|String|连接用户名|
|RegionId|String|区域ID|
|Capacity|Long|申请到的存储实例容量， 单位：MB。|
|InstanceClass|String|实例的规格|
|InstanceStatus|String|实例的状态|
|QPS|Long|每秒请求数（Query per Second）|
|Bandwidth|Long|带宽|
|Connections|Long|连接数|
|ZoneId|String|区域的可用区ID|
|Config|String|配置|
|ChargeType|String|交易类型|
|NetworkType|String| 网络类型：

 -   CLASSIC（经典网络）
-   VPC（VPC网络）

 |
|VpcId|String|VPC的ID|
|VSwitchId|String|虚拟交换机或路由器ID|
|PrivateIp|String|私有IP地址|
|CreateTime|String|创建时间|
|EndTime|String|结束时间|
|HasRenewChangeOrder|String|是否有续费订单|
|IsRds|Boolean|是否属于RDS|
|InstanceType|String|实例引擎类型分为Memcache和Redis。|
|ArchitectureType|String| 架构类型：

 -   cluster（集群版）
-   standard（标准版）
-   SplitRW（读写分离版）
-   NULL（所有类型，默认值）

 |
|NodeType|String|节点类型|
|PackageType|String|套餐类型：-   standard（标准套餐）
-   customized（定制套餐）

|
|EngineVersion|String|数据库版本：-   2.8
-   4.0

|
|DestroyTime|String|销毁实例的时间|
|ConnectionMode|String|网络连接模式|

## 请求示例 {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com/
?Action=DescribeInstances
&RegionId=cn-hangzhou
&<公共请求参数>
```

## 返回示例 {#section_hjp_tkw_wbb .section}

**XML格式**

```
<DescribeInstancesResponse>
 <PageNumber>1</PageNumber>
 <TotalCount>38</TotalCount>
 <PageSize>10</PageSize>
 <RequestId>9099BAE9-BE8A-4B25-8E96-3C22289C70A6</RequestId>
 <Instances>
   <KVStoreInstance>
     <Config>{"EvictionPolicy":"volatile-lru","list-max-ziplist-entries":512,"zset-max-ziplist-entries":128,"hash-max-ziplist-entries":512,"hash-max-ziplist-value":64,"list-max-ziplist-value":64,"set-max-intset-entries":512,"zset-max-ziplist-value":64}</Config>
     <HasRenewChangeOrder>false</HasRenewChangeOrder>
     <InstanceId>r-xxxxxxxxxxxxxxx</InstanceId>
     <UserName>r-xxxxxxxxxxxxxxx</UserName>
     <ZoneId>cn-hangzhou-g</ZoneId>
     <ArchitectureType>standard</ArchitectureType>
     <NetworkType>Classic</NetworkType>
     <QPS>100000</QPS>
     <PackageType>standard</PackageType>
     <IsRds>true</IsRds>
     <ConnectionDomain>r-xxxxxxxxxxxxxxx.redis.rds.aliyuncs.com</ConnectionDomain>
     <EngineVersion>2.8</EngineVersion>
     <InstanceName>zhai_test</InstanceName>
     <ReplacateId>bls-xxxxxxxxxxxxxxx</ReplacateId>
     <Bandwidth>16</Bandwidth>
     <ChargeType>PostPaid</ChargeType>
     <InstanceType>Redis</InstanceType>
     <InstanceStatus>Normal</InstanceStatus>
     <Port>6379</Port>
    <InstanceClass>redis.master.mid.default</InstanceClass>
     <RegionId>cn-hangzhou</RegionId>
     <CreateTime>2018-10-10T04:19:01Z</CreateTime>
     <NodeType>double</NodeType>
     <Capacity>2048</Capacity>
     <Connections>10000</Connections>
   </KVStoreInstance>
 </Instances>
</DescribeInstancesResponse>
```

**JSON格式**

```
{
   "PageNumber":1,
   "TotalCount":38,
   "PageSize":10,
   "RequestId":"9099BAE9-BE8A-4B25-8E96-3C22289C70A6",
   "Instances":{
       "KVStoreInstance":[
           {
               "Config":"{"EvictionPolicy":"volatile-lru","list-max-ziplist-entries":512,"zset-max-ziplist-entries":128,"hash-max-ziplist-entries":512,"hash-max-ziplist-value":64,"list-max-ziplist-value":64,"set-max-intset-entries":512,"zset-max-ziplist-value":64}",
               "HasRenewChangeOrder":false,
               "InstanceId":"r-xxxxxxxxxxxxxxx",
               "UserName":"r-xxxxxxxxxxxxxxx",
               "ZoneId":"cn-hangzhou-g",
               "ArchitectureType":"standard",
               "NetworkType":"Classic",
               "QPS":100000,
               "PackageType":"standard",
               "IsRds":true,
               "ConnectionDomain":"r-xxxxxxxxxxxxxxx.redis.rds.aliyuncs.com",
               "EngineVersion":"2.8",
               "InstanceName":"zhai_test",
               "ReplacateId":"bls-xxxxxxxxxxxxxxx",
               "Bandwidth":16,
               "ChargeType":"PostPaid",
               "InstanceType":"Redis",
               "InstanceStatus":"Normal",
               "Port":6379,
               "InstanceClass":"redis.master.mid.default",
               "RegionId":"cn-hangzhou",
               "CreateTime":"2018-10-10T04:19:01Z",
               "NodeType":"double",
               "Capacity":2048,
               "Connections":10000
           }
       ]
   }
}
```

