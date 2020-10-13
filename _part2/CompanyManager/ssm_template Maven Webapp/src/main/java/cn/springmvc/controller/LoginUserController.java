/**
 * @author:稀饭
 * @time:下午4:47:51
 * @filename:LoginUserController.java
 */
package cn.springmvc.controller;

import cn.springmvc.model.LoginUser;
import cn.springmvc.service.LoginUserService;
import cn.springmvc.util.IPAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/loginUser")
@Controller
public class LoginUserController {
	@Autowired
	private LoginUserService loginUserService;

	/**
	 * @Title: saveLoginUser
	 * @Description: TODO
	 * @param @param request
	 * @return void
	 */
	@RequestMapping("/saveLoginUser")
	public void saveLoginUser(HttpServletRequest request) {
		
		String ip = IPAddressUtil.getIPAddr(request);
		String address = IPAddressUtil.getPosition(ip, "utf-8");
		Date date = new Date();
		DateFormat df7 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.MEDIUM); // 显示日期，时间（精确到分）
		LoginUser loginUser = new LoginUser();
		loginUser.setLoginuser_ip(ip);
		loginUser.setLoginuser_logintime(df7.format(date));
		loginUser.setLoginuser_address(address);
		loginUserService.saveLoginUser(loginUser);
	}

	/**
	 * @Title: loginuser 
	* @Description: TODO
	* @param @param model
	* @param @return  
	* @return String
	 */
	@RequestMapping("/loginuser")
	public String loginuser(Model model)
	{
		List<LoginUser> list = loginUserService.getLoginUsers();
		model.addAttribute("usersList", list);
		model.addAttribute("sum",list.size());
		return "/logininfo/userReport";
	}
}
