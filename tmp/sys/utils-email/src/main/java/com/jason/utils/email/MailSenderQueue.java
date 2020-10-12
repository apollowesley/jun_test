package com.jason.utils.email;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;
import com.jason.utils.email.message.SimpleMessage;

/**
 * Email消息队列发送器
 * @author jason
 */
public class MailSenderQueue {
	
	private MailSender mailSender;
	/**
	 * 待发送消息组
	 */
	private Queue<MailMessage> messageQueue;
	
	private Logger logger=Logger.getLogger(getClass());

	public MailSenderQueue(Account account) {
		mailSender=new MailSender(account);
		messageQueue=new LinkedBlockingQueue<MailMessage>();
		start();
	}
	
	public void start(){
		new SendMessage().start();
	}
	
	public void autoCreateMessage(String toAddress,String subject){
		new MessageCreate(toAddress,subject).start();
	}
	
	public void addMessage(MailMessage message) {
		if(message==null){
			return ;
		}
		logger.debug("Send Mail --> "+message.getToAddress());
		messageQueue.add(message);
	}
	
	public class SendMessage extends Thread{
		@Override
		public void run() {
			send();
		}
		
		private void send(){
			while(true){
				synchronized (messageQueue) {
					while(messageQueue.size()==0){
						try {
                            logger.debug("消息队列中没有待发送的消息，正在等待...");
                            messageQueue.wait();
                        } catch (InterruptedException e) {
                            messageQueue.notify();
                        	logger.error(e);
                        }
					}
					mailSender.send(messageQueue.poll());
					messageQueue.notify();
					logger.debug("成功发出一条消息，队列中有 "+messageQueue.size()+" 条消息等待发送");
				}
			}
		}
	}
	
	
	public class MessageCreate extends Thread{
		private SimpleMessage message=new SimpleMessage();
		private int counts=0;
		@SuppressWarnings("unused")
		private String fromAddress=null;
		private String toAddress;
		private String subject;
		
		public MessageCreate(String toAddress,String subject) {
			this.toAddress=toAddress;
			this.subject=subject;
			if(mailSender.getAccount()!=null){
				fromAddress=mailSender.getAccount().getEmail();
			}
		}
		
		@Override
		public void run() {
			while(true){
				synchronized (messageQueue) {
					counts++;
		    		message.setSubject(subject+"-"+counts);
		    		message.setToAddress(toAddress);
		    		messageQueue.add(message);
		    		messageQueue.notify();
		    		logger.debug("自动生产一条消息(messageId: "+counts+")加入消息队列");
				}
			}
		}
	}
}