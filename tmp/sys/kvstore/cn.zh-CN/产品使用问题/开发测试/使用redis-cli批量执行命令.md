# 使用redis-cli批量执行命令 {#concept_vbj_bpp_mgb .concept}

您可以使用redis-cli执行多行命令，方便快捷地完成测试需求。

## 背景信息 {#section_efy_yqk_ngb .section}

在实际环境中，尤其是业务上线前，您可能需要进行大量的测试，其中一部分需要您对Redis数据做出各种修改。您可以将需要批量执行的Redis命令写入一个.txt文件中，通过redis-cli批量执行该文件中的命令，实现小规模或者临时的数据修改与测试。

## 前提条件 {#section_tp1_5sk_ngb .section}

-   有可用的阿里云环境，即至少有一个ECS实例与一个Redis实例，且二者可以互通；
-   ECS实例使用的系统为Linux。

## 操作步骤 {#section_b2h_trk_ngb .section}

1.  在ECS中使用`vi *batchcmd.txt*`命令创建一个.txt文件，在其中输入并保存需要批量执行的命令，命令之间以换行分隔。

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/113066/154812447837770_zh-CN.png)

    **说明：** 

    -   您可以用任意效果相同的命令完成该步骤，也可以使用任意的文件名，batchcmd.txt仅作示例。
    -   batchcmd.txt中的命令将会按照顺序被执行，如同在命令行中输入并执行多条命令。
2.  使用以下命令批量执行.txt中的Redis命令：

    ```
    cat ./batchcmd.txt | redis-cli -h host -a password
    ```

    ![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/113066/154812447837769_zh-CN.png)

    **说明：** 

    -   `host`为Redis实例的连接地址。
    -   `password`为Redis实例的认证密码。
    -   命令行会逐行输出各命令的执行结果。

