package com.antdsp.web.rest;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.antdsp.web.dto.LoginUserInfo;
import com.antdsp.common.AntdspResponse;
import com.antdsp.common.ResponseCode;
import com.antdsp.utils.CaptchaUtils;
import com.antdsp.utils.Constants;
import com.antdsp.utils.ShiroUtils;

@RestController
public class Login {
	
	@GetMapping("/captcha.jpg")
	public void captcha(HttpServletResponse response)throws IOException{
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store, no-cache");
		response.setContentType("image/jpeg");
		
		String text = CaptchaUtils.createText();
		ShiroUtils.setSessionAttribute(Constants.CAPTCHA_SESSION_KEY	, text);
		ImageIO.write(CaptchaUtils.createImage(text), "jpg", response.getOutputStream());
	}
	
	@GetMapping("/login")
	public AntdspResponse login() {
		return AntdspResponse.error(ResponseCode.UNAUTHORIZED , "请先登录");
	}
	
	@PostMapping("/login")
	public AntdspResponse login(@RequestBody LoginUserInfo userInfo) {
		
		String code = userInfo.getCode();
		if(!ShiroUtils.getCaptcha().equalsIgnoreCase(code)) {
			return AntdspResponse.error(ResponseCode.ERROR , "验证码错误");
		}

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getLoginname() , userInfo.getPassword());
		try {
			subject.login(token);
			Session session = subject.getSession();
			return AntdspResponse.success(session.getId().toString());
		}catch(AuthenticationException e) {
			return AntdspResponse.error(e.getMessage());
		}
		
	}
	
	@GetMapping("/login_success")
	public AntdspResponse loginsuccess() {
		Session session = ShiroUtils.getSession();
		return AntdspResponse.success(session.getId().toString());
	}
	
	@GetMapping("/unauth")
	public AntdspResponse unauth() {
		return new AntdspResponse(ResponseCode.FORBIDDEN , false);
	}
	
	@GetMapping("/logout")
	public AntdspResponse logout() {
		
		Subject subject = SecurityUtils.getSubject();
        subject.logout();
		return AntdspResponse.success("退出登录成功");
	}
	
}
