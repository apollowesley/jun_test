package com.dtdream.rdic.saas.utils;

import com.dtdream.rdic.saas.app.Results;
import com.dtdream.rdic.saas.def.Result;
import com.dtdream.rdic.saas.intf.Worker;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Ozz8 on 2015/06/30.
 * <!-- available tools -->
 *     - com.sun.mail:logging-mailhandler:1.5.4
 *     - com.sun.mail:dsn:1.5.4
 *     - com.sun.mail:pop3:1.5.4
 *     - com.sun.mail:qimap:1.5.4
 *     - com.sun.mail:imap:1.5.4
 *     - com.sun.mail:smtp:1.5.4
 *     - com.sun.mail:parent-distrib:1.5.4
 *     - com.sun.mail:mailapi:1.5.4
 *     - com.sun.mail:javax-mail:1.5.4
 *     - com.sun.mail:all:1.5.4
 * <!-- add maven dependencies -->
 * <dependency>
 *   <groupId>com.sun.mail</groupId>
 *   <artifactId>mailapi</artifactId>
 *   <version>1.5.4</version>
 * </dependency>
 * <dependency>
 *   <groupId>org.springframework</groupId>
 *   <artifactId>spring-context-support</artifactId>
 *   <version>x.x.x.RELEASE</version>
 * </dependency>
 * <dependency>
 *   <groupId>javax.mail</groupId>
 *   <artifactId>javax.mail-api</artifactId>
 *   <version>x.x.x</version>
 * </dependency>
 * <!-- Spring DI -->
 * <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
 *   <property name="defaultEncoding" value="UTF-8"></property>
 *   <property name="username" value="dtdream@yeah.net"></property>
 *   <property name="password" value="zalvtnfcyujzcnbh"></property>
 *   <property name="protocol" value="smtps"></property>
 *   <property name="javaMailProperties">
 *   <props>
 *     <prop key="mail.smtps.auth">true</prop>
 *     <prop key="mail.smtps.timeout">25000</prop>
 *     <prop key="mail.smtps.host">smtp.yeah.net</prop>
 *     <prop key="mail.smtps.port">465</prop>
 *     <prop key="mail.smtps.socketFactory.port">465</prop>
 *     <prop key="mail.smtps.socketFactory.fallback">false</prop>
 *     <prop key="mail.smtps.starttls.enable">true</prop>
 *     <prop key="mail.smtps.socketFactory.class">javax.net.ssl.SSLSocketFactory</prop>
 *     <prop key="mail.smtps.debug">true</prop>
 *     <prop key="mail.debug">true</prop>
 *   </props>
 *   </property>
 * </bean>
 * <bean id="mailUtils" class="com.dtdream.rdic.saas.utils.MailUtils">
 *   <property name="from" value="${mail.from}"></property>
 *   <property name="replyto" value="${mail.replyto}"></property>
 *   <property name="sender" ref="mailSender"></property>
 * </bean>
 */
public class MailUtils {
    public enum PRIORITY {
        HIGH(1),
        NORMAL(3),
        LOW(5);
        private int level;
        private PRIORITY(int level) {
            this.level = level;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }

    public enum ETYPE {
        TEXT,
        HTML;
    }

    protected Logger logger = LoggerFactory.getLogger(MailUtils.class);

    private TemplateUtils template = null;
    private TextUtils textor = null;
    private String from;
    private String replyto;
    JavaMailSender sender;

    /**
     * 同步发送邮件接口
     * @param to        邮件收件人，必须
     * @param from      邮件发件人，为null则采用Bean缺省值
     * @param cc        邮件抄送人，为null则没有
     * @param bcc       邮件密送人，为null则没有
     * @param subject   邮件主题，必须
     * @param text      邮件文本正文，不能同时与html为null
     * @param html      邮件html正文，不能同时与text为null
     * @param priority  邮件优先级，PRIORITY枚举，必须
     * @param annexes   邮件附件完全路径，为null则没有
     * @return
     */
    public Result mailto (
        List<String> to,
        String from,
        List<String> cc,
        List<String> bcc,
        String subject,
        String text,
        String html,
        PRIORITY priority,
        String[] annexes)
    {
        if (null == this.sender)
            return Results.FAIL_NO_SENDER;
        if (null == to)
            return Results.FAIL_NO_RECIPIENTS;
        if (null == subject)
            return Results.FAIL_NO_SUBJECT;
        if (null == text && html == text)
            return Results.FAIL_NO_BODY;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            Worker<InternetAddress,String> worker = new Worker<InternetAddress,String>() {
                @Override
                public InternetAddress work(String object) {
                    if (null == object || object.length() <= 0)
                        return null;
                    try {
                        return new InternetAddress(object);
                    } catch (AddressException e) {
                        logger.debug("创建邮件接收人失败：", e);
                        return null;
                    }
                }
            };
            List<InternetAddress> list;
            list = SetUtils.foreach(to, worker, null, null, null);
            InternetAddress[] tos = new InternetAddress[list.size()];
            tos = list.toArray(tos);
            messageHelper.setTo(tos);
            if (null != from) {
                messageHelper.setFrom(from);
                messageHelper.setReplyTo(from);
            } else {
                if (KitUtils.check(this.from).success()) {
                    messageHelper.setFrom(this.from);
                    if (KitUtils.check(this.replyto).success())
                        messageHelper.setReplyTo(this.replyto);
                    else
                        messageHelper.setReplyTo(this.from);
                }
            }
            if (null != cc) {
                list = SetUtils.foreach(cc, worker, null, null, null);
                InternetAddress[] ccs = new InternetAddress[list.size()];
                ccs = list.toArray(ccs);
                messageHelper.setCc(ccs);
            }
            if (null != bcc) {
                list = SetUtils.foreach(bcc, worker, null, null, null);
                InternetAddress[] bccs = new InternetAddress[list.size()];
                bccs = list.toArray(bccs);
                messageHelper.setBcc(bccs);
            }
            messageHelper.setSubject(subject);
            messageHelper.setPriority(priority.getLevel());
            if (null != text)
                messageHelper.setText(text);
            if (null != html)
                messageHelper.setText(html, true);
            String annex;
            String annexName;
            if (null != annexes) {
                for (int i = 0; i < annexes.length; ++ i) {
                    annex = annexes[i];
                    annexName = annex.substring(annex.lastIndexOf(File.separator.charAt(0)), annex.length());
                    messageHelper.addInline(MimeUtility.encodeText(annexName), new File(annex));
                }
            }
            try {
                sender.send(message);
                return Results.SUCCESS;
            } catch (MailException e) {
                logger.error("发送邮件失败：", e);
                if (e instanceof MailSendException) {
                    Exception[] exceptions = ((MailSendException) e).getMessageExceptions();
                    if (null != exceptions) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < exceptions.length; ++i) {
                            if (! (exceptions[i] instanceof SendFailedException))
                                continue;
                            Address[] addresses = ((SendFailedException) exceptions[i]).getInvalidAddresses();
                            if (null == addresses)
                                continue;
                            for (int j = 0; j < addresses.length; ++j) {
                                sb.append(", ").append(addresses[j].toString());
                            }
                        }
                        if (sb.length() > 0) {
                            /**
                             * 删除头部的[, ]
                             */
                            sb.delete(0,2);
                            return new Result(Result.FAILURE_SEDN_EMAIL, "地址无效：".concat(sb.toString()));
                        }
                    }
                }
                return Result.FAILURE_SEDN_EMAIL;
            }
        } catch (MessagingException e) {
            logger.error("创建邮件助手失败：", e);
            return Results.FAIL_GEN_MIME_HELPER;
        } catch (UnsupportedEncodingException e) {
            logger.error("编码附件名称失败：", e);
            return Results.FAIL_GEN_ANNEX_NAME;
        }
    }

    public Result mailto (List<String> to,
        String from,
        List<String> cc,
        List<String> bcc,
        String subject,
        VelocityContext ctxt,
        String template,
        ETYPE type,
        PRIORITY priority)
    {
        if (null == this.template)
            return Result.FAILURE_NOINIT_TEMPLATE;
        if (null == template)
            return Result.FAILURE_NULL_TEMPLATE;
        if (null == ctxt)
            return Result.FAILURE_NULL_CONTEXT;
        Template templ = this.template.get(template);
        if (null == templ)
            return Result.FAILURE_NOSUCH_TEMPLATE;
        StringWriter sw = new StringWriter();
        templ.merge(ctxt, sw);
        if (type.equals(ETYPE.HTML))
            return mailto(to, from, cc, bcc, subject, null, sw.toString(), priority, null);
        else
            return mailto(to, from, cc, bcc, subject, sw.toString(), null, priority, null);
    }

    public Result mailto (
        List<String> to,
        String from,
        List<String> cc,
        List<String> bcc,
        String subject,
        String template,
        String[] vars,
        Object[] vals,
        ETYPE type,
        PRIORITY priority)
    {
        if (null == this.template)
            return Result.FAILURE_NOINIT_TEMPLATE;
        if (null == template)
            return Result.FAILURE_NULL_TEMPLATE;
        if (null == textor)
            return Result.FAILURE_NULL_TEXTOR;
        StringWriter sw = textor.template(template, vars, vals);
        if (type.equals(ETYPE.HTML))
            return mailto(to, from, cc, bcc, subject, null, sw.toString(), priority, null);
        else
            return mailto(to, from, cc, bcc, subject, sw.toString(), null, priority, null);
    }

    public TemplateUtils getTemplate() {
        return template;
    }

    public void setTemplate(TemplateUtils template) {
        this.template = template;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReplyto() {
        return replyto;
    }

    public void setReplyto(String replyto) {
        this.replyto = replyto;
    }

    public JavaMailSender getSender() {
        return sender;
    }

    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    public TextUtils getTextor() {
        return textor;
    }

    public void setTextor(TextUtils textor) {
        this.textor = textor;
    }
}
