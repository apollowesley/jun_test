package org.duomn.naive.common.util;

import org.duomn.naive.common.mail.MailSender;
import org.duomn.naive.common.mail.MailSenderImpl;
import org.duomn.naive.common.ssh.SSHPool;
import org.duomn.naive.common.thread.ThreadPool;

/***
 * BeanFactory，获取特定对象的实例.<br>
 * 把SpringContextHolder中比较常见的Bean分离出来
 * @author Hu Qiang
 *
 */
public class BF {

	/** 获取邮件发送 */
	public static MailSender getMailSender() {
		return SpringContextHolder.getBean(MailSenderImpl.class);
	}
	
	/** 获取SSHPool */
	public static SSHPool getSSHPool() {
		return SpringContextHolder.getBean(SSHPool.class);
	}
	
	/** 获取ThreadPool */
	public static ThreadPool getThreadPool() {
		return SpringContextHolder.getBean(ThreadPool.class);
	}
	
}
