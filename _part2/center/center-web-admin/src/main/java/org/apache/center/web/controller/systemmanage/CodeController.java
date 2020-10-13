package org.apache.center.web.controller.systemmanage;

import org.apache.center.service.StatusCodeService;
import org.apache.center.web.util.CodeUtils;
import org.apache.playframework.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;


/**
 * @author willenfoo
 */
@Controller
@RequestMapping("code/")
public class CodeController extends BaseController {
	
	private static String VIEWS_PATH = "statusCode/";
	
	@Reference(registry="centerRegistry")
	private StatusCodeService statusCodeService; //服务层

	@RequestMapping(value = "find")
    @ResponseBody
    @Permission(action = Action.Skip)
    public Object find(@RequestParam String key,
    		@RequestParam(required=false,value="selected") String selected) {
		
		return CodeUtils.getData(key);
    	/*List<StatusCode> list = CodeUtils.getCodes(groupNo);
    	if (list != null && !list.isEmpty()) {
    		for (StatusCode code : list) {
    			if ("selectedAll".equals(selected) || code.getNodeKey().equals(selected)) {
    				code.setSelected(true);
        		}
			}
    	}
    	return list;*/
    }
	
	 
}