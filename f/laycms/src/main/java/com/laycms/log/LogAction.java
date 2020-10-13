/**
 * 
 */
package com.laycms.log;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.laycms.BaseAction;
import com.laycms.base.EntityView;
import com.laycms.base.PageContext;
import com.laycms.log.entity.BizLog;
import com.laycms.log.entity.LogOperationEnum;
import com.laycms.log.entity.LogStatusEnum;
import com.laycms.util.BeansWrapperUtil;

/**
 * @author <p>Innate Solitary 于 2012-8-25 下午5:00:19</p>
 *
 */
@Controller
public class LogAction extends BaseAction {
	@Autowired
	private LogMng logMng;
	@Autowired
	private LogDao logDao;

	
	@RequestMapping("log/list")
	public String list(ModelMap model, HttpServletRequest request, Integer pageNum, Integer numPerPage) throws Exception {
		EntityView ev = new EntityView();
		PageContext<BizLog> pc = logDao.queryUsePage(ev, pageNum, numPerPage);
		model.addAttribute("pageCtx", pc);
		model.addAttribute("operationEnum", BeansWrapperUtil.wrapEnum(LogOperationEnum.class));
		model.addAttribute("statusEnum", BeansWrapperUtil.wrapEnum(LogStatusEnum.class));
		
		return "log/list";
	}
}
