package com.evil.struts2.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.system.Log;
import com.evil.service.LogService;
import com.evil.util.Page;
import com.evil.util.ValidateUtil;

@Controller("LogAction")
@Scope("prototype")
public class LogAction extends BaseAction<Log> {
	private static final long serialVersionUID = -1180266348131433939L;
	@Resource
	private LogService logService;

	/**
	 * 到角色的管理界面
	 * @return
	 */
	public String toLogManagePage(){
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		String sortfield ="";
		if(!ValidateUtil.isNull(orderField)&&!ValidateUtil.isNull(orderDirection))
			sortfield = orderField + " " + orderDirection;
		pageResult = logService.findLogPage(page,sortfield);
		return "logManagePage";
	}

}
