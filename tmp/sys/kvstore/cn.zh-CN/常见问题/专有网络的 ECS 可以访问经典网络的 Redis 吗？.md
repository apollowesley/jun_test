# 专有网络的 ECS 可以访问经典网络的 Redis 吗？ {#concept_stg_4zz_xdb .concept}

专有网络和经典网络是两个子网，是无法通过内网互通的。您可以通过如下方法来解决：

-   如果您接受转换 Redis 的网络类型，您可以将 Redis 切换为 ECS 所在的 VPC 网络，请参考[切换为专有网络](../cn.zh-CN/用户指南/管理实例/切换为专有网络.md#)。需要注意的是 Redis 只能从经典网络转换成 VPC 网络，反之不行。

    **说明：** 

    在切换网络时，如果无法选择交换机，可以在 VPC 下创建一个和 Redis 同一可用区的交换机，然后再切换 Redis 的网络即可，创建交换机步骤请参见[交换机](https://help.aliyun.com/document_detail/65387.html)。

-   如果不接受转换 Redis 的网络类型，因为 ECS 不支持将 VPC 切换为经典网络，您需要重新购买经典网络的 ECS。


