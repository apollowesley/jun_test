package org.apache.center.web.controller.systemmanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.center.model.Resources;
import org.apache.center.service.ResourcesService;
import org.apache.center.service.RoleResourcesService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.playframework.domain.EasyuiJsonResult;
import org.apache.playframework.mybatisplus.mapper.EntityWrapper;
import org.apache.playframework.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * @author willenfoo
 */
@Controller
@RequestMapping("resources/")
public class ResourcesController extends BaseController {
	
	private static String VIEWS_PATH = "systemmanage/resources/";
	
	@Reference(registry="centerRegistry")
	private ResourcesService resourcesService; //服务层

	@Reference(registry="centerRegistry")
	private RoleResourcesService roleResourcesService; //服务层
	
    /**
	 * 跳转到查询页面
	 * @return
	 */
	@RequestMapping(value="toFind")
	@Permission("resources")
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
	@Permission("resources_find")
	public Object find(Resources resources) {
		EntityWrapper<Resources> wrapper = new EntityWrapper<Resources>();
		wrapper.orderBy("sequence", true);
		if (resources.getId() == null || resources.getId().equals(0L)) {
			wrapper.eq("parent_id", 0L);
			Page<Resources> page = getEasyuiPage();
			return EasyuiJsonResult.getSuccessResult(resourcesService.selectPage(page , wrapper));
		} else {
			wrapper.eq("parent_id", resources.getId());
			return setData(resourcesService.selectList(wrapper));
		}
	}

	private List<Resources> setData(List<Resources> list) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (Resources resources : list) {
				EntityWrapper<Resources> wrapper = new EntityWrapper<Resources>();
				wrapper.eq("parent_id", resources.getId());
				int childCount = resourcesService.selectCount(wrapper);
				if (childCount > 0) {
					resources.setState("closed");
				} else {
					resources.setState("open");
				}
			}
		}
		return list;
	}
	
    /**
     * 跳转到新增页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toAdd")
	@Permission("resources_add")
	public String toAdd(ModelMap modelMap) {
	    modelMap.put("resources", new Resources());
		return VIEWS_PATH+"edit";
	}
	
	/**
     * 跳转到更新页面
     * @param modelMap
     * @return
     */
	@RequestMapping(value="toUpdate")
	@Permission("resources_update")
	public String toUpdate(@RequestParam Long id,ModelMap modelMap) {
	    Resources resources = resourcesService.selectById(id);
	    modelMap.put("resources", resources);
		return VIEWS_PATH+"edit";
	}
	
	/**
	 * 添加
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	@Permission("resources_add")
	public Object add(Resources resources) {
		if (resources.getParentId() == null) {
			resources.setParentId(0L);
		}
		return getResult(resourcesService.insert(resources));
	}
	
	/**
	 * 更新
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	@Permission("resources_update")
	public Object update(Resources resources) {
		return getResult(resourcesService.updateById(resources));
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	@Permission("resources_delete")
	public Object delete(@RequestParam("id") Long id) {
	    Resources resources = new Resources();
	    resources.setId(id);
	    resources.setIsDelete("Y");
		return getResult(resourcesService.updateById(resources));
	} 
	
	
	/**
     * 跳转到 分配访问资源   页面
     * @param modelMap
     * @return
     */
	@Permission("role_assign_resources")
	@RequestMapping(value="toAssignResources")
	public String toAssignResources() {
		return VIEWS_PATH+"assignResources";
	}
	
	/**
	 * 根据角色id，查询该角色可以访问的资源
	 * @param roleId
	 * @return
	 */
	@Permission("role_assign_resources")
	@RequestMapping(value = "findByRoleId")
	@ResponseBody
	public Object findByRoleId(@RequestParam("roleId") Long roleId) {
		return resourcesService.selectByRoleId(roleId);
	}
	  
	/**
	 * 保存 角色与资源 关系
	 * 
	 * @param roleIds
	 * @param resourcesIds
	 * @return
	 */
	@Permission("role_assign_resources")
	@RequestMapping(value = "saveRoleResources")
	@ResponseBody
	public Object saveRoleResources(
			@RequestParam("roleIds") Long[] roleIds,
			@RequestParam("resourcesIds") Long[] resourcesIds) {
		Map<String, Object> map = EasyuiJsonResult.getSuccessResult();
		if (roleIds.length == resourcesIds.length) {
			try {
				roleResourcesService.saveRoleResources(roleIds[0], resourcesIds);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	 
	/**
	 * 查询资源，现成树形结构
	 * @param resources
	 * @return
	 */
	@Permission("role_assign_resources")
	@RequestMapping(value = "findAll")
	@ResponseBody
	public Object findAll() {
		Map<String, Object> map = EasyuiJsonResult.getSuccessResult();
		EntityWrapper<Resources> wrapper = new EntityWrapper<Resources>();
		wrapper.orderBy("sequence", true);
		try {
			List<Resources> list = resourcesService.selectList(wrapper);
			map.put(ROWS, setChildren(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 把list形成树形结构，设置子节点
	 * 
	 * @param list
	 * @return
	 */
	private List<Resources> setChildren(List<Resources> list) {
		List<Resources> oneLevel = new ArrayList<Resources>();
		if (list != null && !list.isEmpty()) {
			for (Resources resources : list) {
				if ("0".equals(resources.getParentId() + "")) {
					List<Resources> twoLevel = getChildren(resources.getId(), list);
					for (Resources resources2 : twoLevel) {
						List<Resources> threeLevel = getChildren(resources2.getId(), list);
						resources2.setChildren(threeLevel);
						if (threeLevel != null && !threeLevel.isEmpty()) {
							for (Resources resources3 : threeLevel) {
								List<Resources> fourLevel = getChildren(resources3.getId(), list);
								resources3.setChildren(fourLevel);
								if (fourLevel != null && !fourLevel.isEmpty()) {
									for (Resources resources4 : fourLevel) {
										List<Resources> fiveLevel = getChildren(resources4.getId(), list);
										resources4.setChildren(fiveLevel);
									}
								}
							}
						}
					}
					resources.setChildren(twoLevel);
					oneLevel.add(resources);
				}
			}
		}
		return oneLevel;
	}
	
	/**
	 * 得到id中的子节点
	 * @param id
	 * @param list
	 * @return
	 */
	private List<Resources> getChildren(Long id,
			List<Resources> list) {
		List<Resources> children = new ArrayList<Resources>();
		for (Resources resources : list) {
			if (id.equals(resources.getParentId())) {
				children.add(resources);
			}
		}
		return children;
	}
}