# java邮件发送组件

这是一个java发送邮件的通用组件，程序里实现发送邮件这一功能，往往时间浪费在了客户端配置上面。在程序不做邮件服务器限制的情况下，交个客户或者咱们自己配置服务器地址、端口、SSL、账号、密码等确实有遇到过很多麻烦。

#### 作用

组件的目的就是要抛开这些问题，用最简单的方式发送邮件

1、**发送邮件只需要三步** 

1.  Account account=new Account("发送者邮箱地址","密码");
2.  MailSender sender=new MailSender(account);
3.  sender.send("接收者邮箱地址","邮件主题","邮件内容");

2、**不需要对SMTP服务器做任何配置** 

会自动根据 ‘发送者邮箱地址’自动适配，选用哪一家的SMTP服务器

3、**支持消息队列** 

*  Account account=new Account("发送者邮箱地址","密码");
*  MailSenderQueue senderQueue=new MailSenderQueue(account);
*  SimpleMessage message=new SimpleMessage();
*  message.setToAddress("接收者邮箱地址");
*  message.setSubject("主题");
*  message.setText("内容");
*  senderQueue.addMessage(message);

#### 已支持的邮箱类型有

1.  11111111111@qq.com

2.  xxxxxxxxxx@foxmail.com

3.  xxxxxxxxxx@sina.cn

4.  xxxxxxxxxx@sina.com

5.  xxxxxxxxxx@vip.sina.com

6.  xxxxxxxxxx@163.com

#### 扩展
1.  继承com.jason.utils.email.Gateway,实现isAdaptation(String)方法
2.  com/jason/utils/email/gateway/gateway_group.properties 中添加 新增的Gateway类型
3.  完成
