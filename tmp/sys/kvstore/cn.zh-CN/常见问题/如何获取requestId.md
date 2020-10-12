# 如何获取requestId {#task_ndt_r1h_4fb .task}

在云数据库Redis版中创建实例、变更实例配置或者调用API失败时，您可能需要提工单寻求解决方案。此时建议您提交失败请求的requestId供阿里云工程师进行问题分析。本文以使用Chrome浏览器的情况为例介绍requestId的获取方法。

1.  在Chrome浏览器中选择**![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/24380/154034854514234_zh-CN.png)** \> **更多工具** \> **开发者工具**，或者按下键盘上的F12键。 

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/24380/154034854514235_zh-CN.png)

    浏览器中会打开开发者工具窗口。

2.  再次提交之前失败的请求。 开发者工具窗口内会显示请求列表，如[下图](#image_sz3_kgh_4fb)所示。
3.  在Response窗口中找到目标请求的requestId值。 

    ![](images/14239_zh-CN.png "Response详情")


