package com.iotechn.iot.executor.compment;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.iot.executor.dev.IMailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-11-03
 * Time: 下午9:57
 */
@Component
public class MailSend implements InitializingBean,IMailSender {
    private static final Logger logger = LoggerFactory.getLogger(MailSend.class);
    @Value("${com.iotechn.iot.executor.mail.sender.address}")
    private String FROM_ADDR;
    @Value("${com.iotechn.iot.executor.mail.sender.password}")
    private String FROM_PWD;
    @Value("${com.iotechn.iot.executor.mail.sender.host}")
    private String STAMP_HOST;
    // 默认发送间隔周期
    private static final long DEFALUT_PERIOD = 60 * 1000;

    private Session session = null;

    private Map<Long, Long> periodMap;


    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", STAMP_HOST);
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、密码
                return new PasswordAuthentication(FROM_ADDR, FROM_PWD);
            }
        });
        periodMap = new HashMap<>();
    }

    /**
     * @param address 接收地址
     * @param title   标题
     * @param content 内容
     * @param period  发送至少间隔周期 单位ms
     * @return
     */
    @Override
    public boolean sendTo(String address, String title, String content, Long period) {
        try {
            if (period == null) {
                period = DEFALUT_PERIOD;
            }
            long key = ((long)title.hashCode() + (long)content.hashCode());
            Long lastSend = periodMap.get(key);
            if (lastSend == null) {
                lastSend = 0l;
            }
            if (System.currentTimeMillis() - lastSend > period) {
                //若当前时间超过间隔周期
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM_ADDR));
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(address));
                message.setSubject(title);
                message.setText(content);
                Transport.send(message);
                periodMap.put(key, System.currentTimeMillis());
                return true;
            }
        } catch (Exception e) {
            logger.error("邮件发送异常", e);
        }
        return false;
    }


}
