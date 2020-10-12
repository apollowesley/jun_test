package action;

import java.util.Date;
import java.util.Map;

import model.Message;
import model.Users;
import service.IMessageService;
import service.IUserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class publishMessageAction extends ActionSupport {
	
	private String content;
	/** 接受消息人的用户名 */
	private String getterUserName;
	
	private Users sender;
	private IMessageService messageService;
	private IUserService userService;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getGetterUserName() {
		return getterUserName;
	}
	public void setGetterUserName(String getterUserName) {
		this.getterUserName = getterUserName;
	}
	public Users getSender() {
		return sender;
	}
	public void setSender(Users sender) {
		this.sender = sender;
	}
	public IMessageService getMessageService() {
		return messageService;
	}
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	/** 发布消息 */
	public String publishMessage(){
		Map<String, Object> session = (Map<String, Object>) ActionContext.getContext().getSession();	
		Users loginUser = (Users) session.get("user");		
		this.sender = loginUser;
		
//		System.out.println("getterUserName=" + getterUserName);
		// 通过用户名查找用户
		Users getterUser = userService.findUserByName(getterUserName);
		if(null != getterUser){
			System.out.println("getterUser.getUserid()=" + getterUser.getUserid());
			
			Message message = new Message();
			message.setSender(sender);
			message.setGetter(getterUser);
			message.setContent(content);
			message.setMesTime(new Date());
			
//			System.out.println("sender_id="+sender.getUserid()+",getter_id="+getterUser.getUserid());
			
			// 保存消息到数据库里
			this.messageService.save(message);
			return this.SUCCESS;
		} 
		return this.ERROR;
	}
	
}
