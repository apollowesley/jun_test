/**
 * 
 */
package com.laycms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.laycms.config.SystemConfig;
import com.laycms.log.LogMng;
import com.laycms.log.entity.LogOperationEnum;
import com.laycms.log.entity.LogStatusEnum;
import com.laycms.member.MemberService;
import com.laycms.member.entity.Member;
import com.laycms.sys.entity.Menu;
import com.laycms.util.RequestUtils;

@Controller
public class HomeAction extends BaseAction {

	private static final Logger logger = LoggerFactory.getLogger(HomeAction.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private LogMng logMgr;
	@Autowired
	private SystemConfig systemConfig;
	
	@RequestMapping("/")
	public String home(ModelMap model) throws IOException {
		Member user = this.getCurrentUser();
		if(user == null) {
			return "login";
		}
		model.addAttribute("user", user);
		List<Menu> menuList = new ArrayList<>();
		model.addAttribute("menulist",menuList);
		model.addAttribute("systemConfig",systemConfig);
		return "redirect:/index.html";  
	}
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request, ModelMap model,RedirectAttributesModelMap modelMap,
			Member user) {
		try {
			Member member = memberService.findByLoginName(user.getUsername());
			if (member == null) {
				modelMap.addFlashAttribute("errMsg", "用户名输入有误！");
				return "redirect:/login";  
			}
			String encodePassword = DigestUtils.md5Hex(DigestUtils.md5Hex(user.getPassword())  + member.getSalt());
			
			SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUsername(), encodePassword));  
			logMgr.save(user, RequestUtils.getIpAddr(request), user.getId(),LogOperationEnum.LOGIN, LogStatusEnum.SUCCESS, "登录成功");
			return "redirect:/index.html";  
			
		} 
		catch (UnknownAccountException ex) {
			ex.printStackTrace();
	        modelMap.addFlashAttribute("errMsg", "用户不存在或者密码错误！");
	    } 
		catch (IncorrectCredentialsException ex) { 
			ex.printStackTrace();
	        modelMap.addFlashAttribute("errMsg", "用户不存在或者密码错误！");
	    } 
		catch (Exception ex) {  
	        ex.printStackTrace();  
	        modelMap.addFlashAttribute("errMsg", "登录失败！");
	    }  
		return "redirect:/login";  
		
	}
	

	@RequestMapping("/login")
	public String welcome() {
		return "login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		
		Member user = getCurrentUser();
		logMgr.save(user, RequestUtils.getIpAddr(request), user.getId(),LogOperationEnum.LOGOUT, LogStatusEnum.SUCCESS, "退出成功");
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/login";
	}
	@RequestMapping("/unauthorized")
	public String unauthorized() {
		return "unauthorized";
	}
	
}
