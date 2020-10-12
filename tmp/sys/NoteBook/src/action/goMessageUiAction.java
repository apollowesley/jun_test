package action;

import java.util.List;
import java.util.Map;

import model.Message;
import model.Users;
import service.IMessageService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class goMessageUiAction extends ActionSupport {
	
	private IMessageService messageService;
	
	// ------GET SET------	

	public IMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	// ------GET SET------

	
	public String showUserMessageList() throws Exception {
		Map<String, Object> session = (Map<String, Object>) ActionContext.getContext().getSession();	
		Users loginUser = (Users) session.get("user");		
		
		List<Message> messageList = messageService.showMessage(loginUser);
		Map request = (Map) ActionContext.getContext().get("request");
		request.put("messageList", messageList);
		
		for(int i=0; i< messageList.size(); ++i){
			Message m = messageList.get(i);
			System.out.println("Sender="+m.getSender()+", Time="+m.getMesTime()+", Content="+m.getContent());
		}
		
		return SUCCESS;		
	}

}
