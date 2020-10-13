package com.zb.controller.login;

import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zb.bean.sys.SysMenu;
import com.zb.service.sys.MenuService;

@Controller
@RequestMapping("/index")
public class IndexController {

	@Autowired
	private MenuService menuService;
	
    /**
     * 进入首页
     * 
     * 创建日期：2016年8月2日 上午11:14:02 操作用户：zhoubang
     * 
     * @return
     */
    @RequestMapping("home")
    public String page(ModelMap map) {
        Subject currentUser = SecurityUtils.getSubject();
        // 验证是否成功登录的方法
        if (currentUser.isAuthenticated()) {
        	
        	//获取所有左侧父菜单
        	List<SysMenu> parentMenuList = menuService.getAllParentList();
        	for (SysMenu parentMenu : parentMenuList) {
        		parentMenu.setHasMenu(true);
        		
        		//获取子菜单
        		List<SysMenu> subMenu = menuService.getSubMenuByParentId(parentMenu.getId());
        		parentMenu.setSubMenu(subMenu);
			}
        	
        	map.put("menuList", parentMenuList);
            return "/index";
        }
        return "/login";
    }
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/login_default")
	public String defaultPage(){
		return "/main";
	}
	
}
