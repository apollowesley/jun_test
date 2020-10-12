package com.sise.school.teach.utils;

import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.sise.idea.service.ApplicationSettingService;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.sise.school.teach.constants.ApplicationConstants.EMAIL_AUTH_KEY;
import static com.sise.school.teach.constants.ApplicationConstants.EMAIL_SENDER_KEY;


/**
 * 邮箱发送工具类
 *
 * @author idea
 * @data 2018/11/29
 */
@Slf4j
public class EmailUtil {


    public static String SENDER;
    public static String PASSWORD;

    static {
        SENDER = ApplicationSettingService.getValue(EMAIL_SENDER_KEY);
        PASSWORD = ApplicationSettingService.getValue(EMAIL_AUTH_KEY);
    }

    /**
     * 发送纯文本的简单邮件
     *
     * @param title
     * @param reciver
     * @param content
     */
    public static void sendEmail(String title, String content, String reciver) {
        try {
            Properties props = new Properties();
            // 开启debug调试
            props.setProperty("mail.debug", "true");
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.host", "smtp.qq.com");
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            Session session = Session.getInstance(props);
            //邮件内容部分
            Message msg = new MimeMessage(session);

            //邮件的名字
            msg.setSubject(title);
            //邮件的内容
            msg.setText(content);
            //邮件发送者
            msg.setFrom(new InternetAddress(SENDER));
            //设置邮件的群发送地址
            if (reciver != null && reciver.trim().length() > 0) {
                String address[] = reciver.split(",");
                int receiverAddress = address.length;
                if (receiverAddress > 0) {
                    InternetAddress[] internetAddresses = new InternetAddress[receiverAddress];
                    for (int i = 0; i < receiverAddress; i++) {
                        internetAddresses[i] = new InternetAddress(address[i]);
                    }
                    msg.addRecipients(Message.RecipientType.TO, internetAddresses);
                }
            }
            //发送邮件
            Transport transport = session.getTransport();
            /*这里面的参数是协议链接，邮箱名称，接口信息*/
            transport.connect("smtp.qq.com", SENDER, PASSWORD);
            transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
            log.error("[邮件工具]邮件发送出现异常，异常为{}", e);
        }
    }

    public static void asyncSendEmail(String content,String title,String reciverEmailAddr) throws ExecutionException, InterruptedException {
        Callable<Boolean> sendEmailCallable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                EmailUtil.sendEmail(title,content,reciverEmailAddr);
                return true;
            }
        };
        FutureTask<Boolean> futureTask = new FutureTask<>(sendEmailCallable);
        new Thread(futureTask).start();
    }

}
