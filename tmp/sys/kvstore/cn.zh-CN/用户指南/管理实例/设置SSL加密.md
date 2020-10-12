# 设置SSL加密 {#concept_jdh_mdv_h2b .concept}

Redis 2.8标准版、集群版实例和Redis 4.0集群版实例支持SSL加密。您可以启用SSL（Secure Socket Layer）加密，提高数据传输的安全性。

**说明：** 开启SSL会增加实例的网络响应时间，建议必要时才开启该功能。

## 操作步骤 {#section_mt4_rdv_h2b .section}

1.  登录[Redis管理控制台](https://kvstore.console.aliyun.com/)。
2.  在界面左上方的菜单栏中选择实例所在的地域 。
3.  在实例列表页，单击目标实例ID或者其右侧**操作**栏的**管理**。
4.  单击左侧导航栏的**SSL设置**。
5.  单击页面右上方的**设置SSL**，系统弹出对话框。
6.  拨动**开通**滑键，滑键由灰变绿，然后单击**确定**，完成操作。
    -   系统有时候会出现实例状态错误提示，请单击对话框中的**确定**按键。
    -   如果系统出现版本不支持错误提示，请参见[升级小版本](cn.zh-CN/用户指南/管理实例/升级小版本.md#)页面升级版本。
    -   完成操作后，需等待一段时间后，系统才能正确显示操作结果。
    -   您还可通过右上方的**更新有效期**和**下载CA证书**按键执行相关的操作。

## 相关API {#section_jjh_dlp_tgb .section}

[ModifyInstanceSSL](../../../../../cn.zh-CN/API参考/网络安全/ModifyInstanceSSL.md#)

