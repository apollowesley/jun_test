package org.apache.center.web.controller.systemmanage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.playframework.web.controller.BaseController;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.kisso.annotation.Permission;


import org.apache.center.model.StatusCode;
import org.apache.center.service.StatusCodeService;

import com.alibaba.dubbo.config.annotation.Reference;
	
import org.apache.playframework.domain.EasyuiJsonResult;


/**
 * @author willenfoo
 */
@Controller
@RequestMapping("statusCode/")
public class StatusCodeController extends BaseController {
	
	private static String VIEWS_PATH = "systemmanage/statusCode/";
	
	@Reference(registry="centerRegistry")
	private StatusCodeService statusCodeService; //服务层

    /**
	 * 跳转到查询页面
	 * @return
	 */
	@RequestMapping(value="toFind")
	@Permission("statusCode")
	public String toFind() {
		return VIEWS_PATH+"find";
	}
	
	/**
	 *  查询数据
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "find")
	@ResponseBody
	@Permission("statusCode_find")
	public Object find(StatusCode statusCode) {
		EntityWrapper<StatusCode> wrapper = new EntityWrapper<StatusCode>(statusCode);
		Page<StatusCode> page = getEasyuiPage();
		return EasyuiJsonResult.getSuccessResult(statusCodeService.selectPage(page , wrapper));
	}

    /**
     * 跳转到新增页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toAdd")
	@Permission("statusCode_add")
	public String toAdd(ModelMap modelMap) {
	    modelMap.put("statusCode", new StatusCode());
		return VIEWS_PATH+"edit";
	}
	
	/**
     * 跳转到更新页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toUpdate")
	@Permission("statusCode_update")
	public String toUpdate(@RequestParam Long id,ModelMap modelMap) {
	    StatusCode statusCode = statusCodeService.selectById(id);
	    modelMap.put("statusCode", statusCode);
		return VIEWS_PATH+"edit";
	}
	
	/**
	 * 添加
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	@Permission("statusCode_add")
	public Object add(StatusCode statusCode) {
		return getResult(statusCodeService.insert(statusCode));
	}
	
	/**
	 * 更新
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	@Permission("statusCode_update")
	public Object update(StatusCode statusCode) {
		return getResult(statusCodeService.updateById(statusCode));
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	@Permission("statusCode_delete")
	public Object delete(@RequestParam("id") Long id) {
	    StatusCode statusCode = new StatusCode();
	    statusCode.setId(id);
	    statusCode.setIsDelete("Y");
		return getResult(statusCodeService.updateById(statusCode));
	} 
	 
}