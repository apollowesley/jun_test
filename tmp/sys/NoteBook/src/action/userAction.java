package action;

import java.util.Map;

import model.Users;
import service.IUserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class userAction extends ActionSupport {

	private String username;
	private String password;
	private Users user;
	private IUserService userService;
	
	
	
	// ------GET SET------
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	// ------GET SET------
	

	/** µÇÂ¼¼ì²é */
	public String checkUser() throws Exception {	
		Map<String, Object> session = (Map<String, Object>) ActionContext.getContext().getSession();		

		System.out.println("userName=" + username+ ", password= " + password);
		Users user = new Users();
		user.setUsername(username);
		user.setPassword(password);
		
		Users loginUser = userService.findUser(user);
		
		if (loginUser != null) {
			session.put("user", loginUser);
			return SUCCESS;
		} else {			
			return ERROR;
		}
	}
	
}
