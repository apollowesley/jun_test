/**
 * 
 */
package com.laycms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import com.laycms.base.web.DateTypeEditor;
import com.laycms.log.LogMng;
import com.laycms.log.entity.LogOperationEnum;
import com.laycms.log.entity.LogStatusEnum;
import com.laycms.member.MemberService;
import com.laycms.member.entity.Member;
import com.laycms.util.RequestUtils;


/**
 * @author <p>Innate Solitary 于 2012-6-5 下午4:28:31</p>
 *
 */
public class BaseAction extends WebApplicationObjectSupport {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	protected LogMng logMng;
	@InitBinder
	protected void initBind(HttpServletRequest request, ServletRequestDataBinder binder) {

		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
		binder.registerCustomEditor(Date.class, new DateTypeEditor());
	}


	protected Member getCurrentUser() {
		Object obj = SecurityUtils.getSubject().getPrincipal();
		if(obj == null) {
			return null;
		} else if(obj instanceof String) {
			return memberService.findByLoginName(obj.toString());
		} else if(obj instanceof Member) {
			return (Member) obj;
		} else {
			return null;
		}
	}
	
	@ModelAttribute("subject")
	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}
	//保存操作日志
	public void saveLog(HttpServletRequest request,Integer entityId,LogOperationEnum operation,String description){
		logMng.save(getCurrentUser(), RequestUtils.getIpAddr(request),
				entityId, operation, LogStatusEnum.SUCCESS, description);
	}
}
