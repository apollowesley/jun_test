# RAM 中可授权的资源类型 {#reference_ivb_n42_xdb .reference}

云数据库 Redis 版可以在 RAM 中进行授权的资源类型仅一种：instance。

在通过 RAM 进行授权时，资源的描述方式如下：

|资源类型|授权策略中的资源描述方式|
|----|------------|
|Instance|acs:kvstore:$regionid:$accountid:instance/$instanceid acs:kvstore:$regionid:$accountid:instance/ acs:kvstore:::instance/|

其中，所有 $regionid 应为某个 region 的 ID，或者`*`；所有 $instanceid 应为某个 instance 的 ID，或者`*`；以此类推，$account-id 是云帐号的数字 ID，可以用`*`代替。

