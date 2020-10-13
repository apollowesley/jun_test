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


import org.apache.center.model.Param;
import org.apache.center.service.ParamService;

import com.alibaba.dubbo.config.annotation.Reference;
	
import org.apache.playframework.domain.EasyuiJsonResult;


/**
 * @author willenfoo
 */
@Controller
@RequestMapping("param/")
public class ParamController extends BaseController {
	
	private static String VIEWS_PATH = "systemmanage/param/";
	
	@Reference(registry="centerRegistry")
	private ParamService paramService; //服务层

    /**
	 * 跳转到查询页面
	 * @return
	 */
	@RequestMapping(value="toFind")
	@Permission("param")
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
	@Permission("param_find")
	public Object find(Param param) {
		EntityWrapper<Param> wrapper = new EntityWrapper<Param>(param);
		Page<Param> page = getEasyuiPage();
		return EasyuiJsonResult.getSuccessResult(paramService.selectPage(page , wrapper));
	}

    /**
     * 跳转到新增页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toAdd")
	@Permission("param_add")
	public String toAdd(ModelMap modelMap) {
	    modelMap.put("param", new Param());
		return VIEWS_PATH+"edit";
	}
	
	/**
     * 跳转到更新页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toUpdate")
	@Permission("param_update")
	public String toUpdate(@RequestParam Long id,ModelMap modelMap) {
	    Param param = paramService.selectById(id);
	    modelMap.put("param", param);
		return VIEWS_PATH+"edit";
	}
	
	/**
	 * 添加
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	@Permission("param_add")
	public Object add(Param param) {
		return getResult(paramService.insert(param));
	}
	
	/**
	 * 更新
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	@Permission("param_update")
	public Object update(Param param) {
		return getResult(paramService.updateById(param));
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	@Permission("param_delete")
	public Object delete(@RequestParam("id") Long id) {
	    Param param = new Param();
	    param.setId(id);
	    param.setIsDelete("Y");
		return getResult(paramService.updateById(param));
	} 
	 
}