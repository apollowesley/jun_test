# 将AWS ElastiCache for Redis数据库迁移到阿里云 {#concept_sqz_wmc_ggb .concept}

## 背景信息 {#section_qlq_4f1_hgb .section}

本文为您介绍如何将数据从AWS ElastiCache for Redis实例迁移到阿里云ApsaraDB for Redis实例。

## 前提条件 {#section_jj1_wlk_ggb .section}

-   已经[创建阿里云Redis实例](https://help.aliyun.com/document_detail/26351.html)。
-   已经[创建阿里云ECS实例](https://help.aliyun.com/document_detail/25424.html)。

## 注意事项 {#section_tgx_fwd_ggb .section}

-   执行该操作前建议停止将数据继续写入AWS ElastiCache for Redis。
-   执行该操作前请提前做好数据备份。
-   执行该操作前需要规划业务停机时间。
-   如果要迁移的Redis实例是在服务器上自行构建的，建议您使用阿里云[数据传输服务DTS](https://help.aliyun.com/document_detail/26592.html)迁移数据。

## 从AWS ElastiCache for Redis备份和导出数据到S3存储桶 {#section_zvq_pwd_ggb .section}

1.  创建备份到特定群集。单击左侧导航栏中的Redis，选择需要备份的**集群名称**，单击**备份**，**资源名称**里选择需要备份的集群，填写**备份名称**后单击**创建快照**。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/154717250235338_zh-CN.png)

2.  将备份文件导出到AWS S3存储桶。单击左侧导航栏中的备份，选择需要导出的备份文件，单击上方的**复制**，填写**新缓存快照标识符**名称，选择**目标S3位置**，单击右下角**复制**，导出过程将开始。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/154717250335339_zh-CN.png)

3.  您可以在S3存储桶中找到导出的RDB文件。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/154717250335448_zh-CN.png)

4.  从S3存储桶下载RDB文件。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/154717250335449_zh-CN.png)


## 将RDB文件导入ApsaraDB for Redis数据库 {#section_h51_bxd_ggb .section}

1.  使用MobaXterm Personal Edition客户端，通过SFTP协议将RDB文件上传到阿里云ECS实例。
2.  在ECS实例中下载redis-port工具。

    wget http://docs-aliyun.cn-hangzhou.oss.aliyun-inc.com/assets/attach/85829/cn\_zh/1533199526614/redis-port%282%29?spm=a2c4g.11186623.2.10.35d048d2Q12fTI

    **说明：** 使用该命令下载的redis-port工具由于文件名问题会导致安装出错，可通过以下命令修改文件名后再进行下一步操作。mv 下载的redis-port工具文件名 redis-port。

3.  使用chmod u+x redis-port命令将redis-port修改为可执行文件。
4.  使用如下命令将RDB文件导入ApsaraDB for Redis数据库。

    ./redis-port restore -i 备份文件名 -t 目的数据库域名或IP:端口 --auth=

    **说明：** 需要在ApsaraDB for Redis中先开启免密访问，待迁移完成后可关闭。

5.  如果返回restore: rdb done，代表导入成功，此时迁移已经完成。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/154717250335450_zh-CN.png)

6.  登录阿里云ApsaraDB for Redis的[DMS管理控制台](https://dms.console.aliyun.com/#/dms/login)，对迁移结果进行检查，可以搜索一些信息来验证数据。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/154717250335451_zh-CN.png)

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/83060/154717250335496_zh-CN.png)


