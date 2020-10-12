# 使用 DMS 操作数据 {#concept_aw1_mtm_vdb .concept}

DMS 支持两种模式对云数据库 Redis 版进行数据操作，分别为视图模式和命令窗口模式。

## 视图模式 { .section}

视图模式下，您可通过可视化按钮操作进行数据库的增删改查。具体操作步骤如下。

**增加数据**

在 ① 处新增一个 key-value，在弹出的对话框即 ② 处设置键名及数据类型。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3148/2599_zh-CN.png)

在 ③ 处输入 Value 的具体值并在 ④ 处提交更改，在 ⑤ 处确定后，即可完成增加数据的操作。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3148/2622_zh-CN.png)

**删除数据**

选中需要删除的数据，单击**删除** \> **确认**即可完成删除。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3148/2623_zh-CN.png)

**修改数据**

**修改 Key 的命名**

在需修改的 key 上右键选择重命名，在弹出的对话框中输入新的键名，单击**确定**即可完成修改。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3148/2624_zh-CN.png)

**修改 Value 的值**

选中需要修改的数据的 key，在右侧的 value 输入对话框中修改 Value 的值，提交更改并单击**确定**即可完成修改。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3148/2625_zh-CN.png)

**查询数据**

在右侧查询输入栏中输入键名，单击**查询**按钮，即可显示出所查 key 的value。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3148/2626_zh-CN.png)

## 命令窗口模式 { .section}

命令窗口模视支持输入命令的模式来操作数据库，具体操作如下。

1.  选择 ① 处命令窗口，进入命令窗口模式。
2.  在 ② 处命令输入框中输入 Redis 的命令，单击**执行**即可完成一个命令的操作。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/3148/2627_zh-CN.png)

**说明：** 

云数据库 Redis 版支持的命令请参考[支持的 Redis 命令](../../../../cn.zh-CN/快速入门/支持的 Redis 命令.md#)。

您可以观看以下视频快速了解通过 DMS 对 Redis 进行数据操作，视频时长约3分钟。



