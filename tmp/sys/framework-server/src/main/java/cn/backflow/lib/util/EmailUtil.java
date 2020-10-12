package cn.backflow.lib.util;

import cn.backflow.admin.common.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

public class EmailUtil {

    /**
     * 发送Html格式邮件
     *
     * @param subject 邮件标题
     * @param content 邮件内容
     * @param sendTo  收件人
     */
    public static void sendEmail(String subject, String content, String... sendTo) throws Exception {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器认证用户名和密码是否正确
        prop.put("mail.smtp.timeout", "5000"); // 超时时间

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setSession(Session.getInstance(prop));
        sender.setJavaMailProperties(prop);

        // 发送邮箱的邮件服务器
        sender.setHost(Configuration.EMAIL_HOST);
        sender.setUsername(Configuration.EMAIL_ACCOUNT);
        sender.setPassword(Configuration.EMAIL_PASSWORD);

        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = sender.createMimeMessage();

        // 为防止乱码，添加编码集设置
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage, "UTF-8");

        String msg = "发送邮件出错！";
        try {
            msg = "收件人邮箱地址错误！";
            helper.setTo(sendTo);
            msg = "发件人邮箱地址错误！";
            helper.setFrom(Configuration.EMAIL_ADDRESS);
            msg = "邮件主题错误！";
            helper.setSubject(subject);
            msg = "邮件内容错误！";
            helper.setText(content, true);
        } catch (MessagingException e) {
            throw new RuntimeException(msg);
        }
        // 发送邮件
        sender.send(mailMessage);
    }

    /**
     * 发送带有图片的邮件
     **/
    public static void sendMimeEmail() throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.timeout", "5000");

        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setJavaMailProperties(prop);
        emailSender.setHost("smtp.163.com");
        emailSender.setUsername("username"); // 根据自己的情况,设置username
        emailSender.setPassword("password"); // 根据自己的情况, 设置password

        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = emailSender.createMimeMessage();
        // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
        // multipart模式
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
        messageHelper.setTo("toMail@sina.com"); // 设置收件人，寄件人
        messageHelper.setFrom("username@163.com");
        messageHelper.setSubject("测试邮件中嵌套图片!！");
        messageHelper.setText("<html><head></head><body><h1>hello!!spring image html mail</h1><img src=\"cid:aaa\"/></body></html>", true); // true
        // 表示启动HTML格式的邮件

        FileSystemResource img = new FileSystemResource(new File("g:/123.jpg"));
        messageHelper.addInline("aaa", img);

        // 发送邮件
        emailSender.send(mailMessage);

        System.out.println("邮件发送成功..");
    }

    /**
     * 发送带有附件的邮件
     **/
    public void sendAttachedEmail() throws MessagingException {
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setHost("smtp.163.com");
        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage mailMessage = emailSender.createMimeMessage();
        // 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
        // multipart模式 为true时发送附件 可以设置html格式
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");

        // 设置收件人，寄件人
        messageHelper.setTo("toMail@sina.com");
        messageHelper.setFrom("username@163.com");
        messageHelper.setSubject("测试邮件中上传附件!！");
        // true 表示启动HTML格式的邮件
        messageHelper.setText("<html><head></head><body><h1>你好：附件中有学习资料！</h1></body></html>", true);

        FileSystemResource file = new FileSystemResource(new File("g:/test.rar"));
        // 这里的方法调用和插入图片是不同的。
        messageHelper.addAttachment("test.rar", file);

        emailSender.setUsername("username"); // 根据自己的情况,设置username
        emailSender.setPassword("password"); // 根据自己的情况, 设置password
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.timeout", "5000");
        emailSender.setJavaMailProperties(prop);
        // 发送邮件
        emailSender.send(mailMessage);

        System.out.println("邮件发送成功..");
    }
}
