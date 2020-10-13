package luna.com.firefly.web.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import luna.com.firefly.entity.system.SystemMenu;
import luna.com.firefly.service.system.ShiroDbRealm.ShiroUser;
import luna.com.firefly.utils.HttpResult;
import luna.com.firefly.utils.HttpStatus;
import luna.com.firefly.utils.TagOrSessionConstants;
import luna.com.firefly.utils.UserSessionManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

 

/**
 * <菜单管理>
 * 
 * @author liwei
 * @version [1.0, 2015年7月16日]
 * @since [播控管理系统]
 */
@Slf4j
@Controller
@RequestMapping(value = "/sysMenu")
public class SystemMenuController extends SystemBasicController
{
    
    /**
     * <查询菜单信息>
     * 
     * @param pageNumber
     * @param pageSize
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "menuList")
    public String menuList(Model model, ServletRequest request)
    {
        log.info("Inner func [menuList]");
        log.info("Exit  func [menuList]");
        return "manager/menu/menuList";
    }
    
    @RequestMapping(value = "menuListById")
    @ResponseBody
    public List<SystemMenu> menuListById(ServletRequest request, @RequestParam(value = "id", defaultValue = "0") Long id)
    {
        log.info("Inner func [menuListById],param parent_menuId:{}", id);
        List<SystemMenu> firstMenuList = new ArrayList<SystemMenu>();
        try
        {
            firstMenuList = sysMenuService.getMenuByParentId(id);
        }
        catch (Exception e)
        {
            log.error("Enter func[menuListById] catch exception", e);
        }
        log.info("Exit  func [menuListById]");
        return firstMenuList;
    }
    
    /**
     * <进入菜单添加页面>
     * 
     * @return
     */
    @RequestMapping(value = "add/{id}")
    public String addMenu(@PathVariable("id") Long menuId, ModelMap model)
    {
        log.info("Inner func [add],param menuId:{}", menuId);
        List<SystemMenu> menuList = new ArrayList<SystemMenu>();
        menuList = sysMenuService.getMenuByParentId(menuId);
        model.addAttribute("menuList", menuList);
        // flag标识:0标识add,1标识edit
        int flag = 0;
        model.addAttribute("flag", flag);
        return "manager/menu/menuInfo";
    }
    
    /**
     * <进入编辑页面>
     * 
     * @param topicId
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}")
    public String editInfo(@PathVariable Long id, ModelMap model)
    {
        SystemMenu menu = new SystemMenu();
        try
        {
            menu = sysMenuService.getMenuById(id);
            Long parentId = menu.getParentId();
            List<SystemMenu> menuList = new ArrayList<SystemMenu>();
            if (sysMenuService.getMenuById(id).getParentId() != 0)
            {
                // 获取父菜单及父菜单的兄弟菜单
                menuList =
                    sysMenuService.getMenuByParentId(sysMenuService.getMenuById(sysMenuService.getMenuById(id)
                        .getParentId()).getParentId());
            }
            // flag标识:0标识add,1标识edit
            int flag = 1;
            model.addAttribute("menu", menu);
            model.addAttribute("menuList", menuList);
            model.addAttribute("parentId", parentId);
            model.addAttribute("flag", flag);
        }
        catch (Exception e)
        {
            log.error("Enter func[editInfo] catch exception " + e);
        }
        
        return "manager/menu/menuInfo";
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "getAllMenuList")
    @ResponseBody
    public List<List> getSystemDicByType()
    {
        List<List> result = new ArrayList<List>();
        List<String> list = null;
        List<SystemMenu> menuList = new ArrayList<SystemMenu>();
        menuList = sysMenuService.getMenuByParentId(0L);
        list = new ArrayList<String>();
        list.add("0");
        list.add("主菜单");
        result.add(list);
        for (SystemMenu dic : menuList)
        {
            list = new ArrayList<String>();
            list.add(dic.getId() + "");
            list.add(dic.getName());
            result.add(list);
        }
        
        return result;
    }
    
    /**
     * <保存 菜单>
     * 
     * @param menu
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(value = "saveMenu")
    @ResponseBody
    public HttpResult saveMenu(@Valid @ModelAttribute("menu") SystemMenu menu, ModelMap model, HttpServletRequest request,
        HttpServletResponse response)
    {
        HttpResult result = new HttpResult();
        Integer sort = 0;
        Integer sourceSort = 0;
        boolean updateSort = false;
        Long parentId = null;
        SystemMenu sysMenu = new SystemMenu();
        try
        {
            if (null != menu.getId())
            {
                updateSort = true;
                sysMenu = sysMenuService.getMenuById(menu.getId());
                parentId = sysMenu.getParentId();
                sourceSort = sysMenu.getSort();
            }
            if (menu.getParentId() == null)
            {
                menu.setParentId(0L);
            }
            if (menu.getSort() == null || 0 == menu.getSort())
            {
                sort = sysMenuService.getMaxSortNumByParentId(menu.getParentId());
                if (menu.getParentId() == null || (!menu.getParentId().equals(parentId)))
                {
                    sort = (null == sort) ? 1 : ++sort;
                }
                else
                {
                    sort = sourceSort;
                }
                menu.setSort(sort);
            }
            menu.setCreateUser(this.getCurrentUser().name);
            sysMenuService.saveMenu(menu);
            
            // 更新 原parentId的 顺序
            if (updateSort && (menu.getParentId() != parentId))
            {
                sysMenuService.updateMenu(parentId, sourceSort);
            }
            result.setResult(HttpStatus.OK);
            
        }
        catch (Exception e)
        {
            result.setResult("300", "操作失败");
            log.error("Enter func[saveMenu] catch exception", e);
        }
        return result;
        
    }
    
    /**
     * <删除menu>
     * 
     * @param response
     * @param request
     */
    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public HttpResult deleteById(@PathVariable Long id, HttpServletResponse response, HttpServletRequest request)
    {
        HttpResult result = new HttpResult();
        
        try
        {
            // 获得当前菜单
            SystemMenu menu = sysMenuService.getMenuById(id);
            // 当前菜单为一级菜单
            if (menu.getParentId() == 0)
            {
                List<SystemMenu> menus = new ArrayList<SystemMenu>();
                menus = sysMenuService.getMenuByParentId(id);
                // 当前一级菜单包含子菜单时,删除当前菜单,子菜单,菜单与角色关系.子菜单按钮,子菜单按钮与角色关系
                if (null != menus)
                {
                    for (SystemMenu chileMenu : menus)
                    {
                        // 删除菜单,菜单与角色关系
                        sysMenuService.deleteMenuById(chileMenu.getId());
                    }
                }
                // 删除一级菜单,菜单与角色关系
                sysMenuService.deleteMenuById(id);
            }
            // 当前菜单非一级菜单
            else
            {
                // 删除菜单,菜单与角色关系
                sysMenuService.deleteMenuById(menu.getId());
            }
            result.setResult(HttpStatus.OK);
            
        }
        catch (Exception e)
        {
            result.setResult("300", "操作失败");
            log.info("Enter func[deleteById]" + e);
            
        }
        // 返回客户端信息，打印相关信息
        return result;
    }
    
    /**
     * <登陆时显示登陆用户名>
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "getLoginUser", method = RequestMethod.GET)
    @ResponseBody
    public String loginUser(Model model)
    {
        ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
        StringBuilder sb = new StringBuilder();
        System.out.println("------------" + user.getName() + "-------------------");
        sb.append("<li value='${user.name}' style='color: #b9ccda;'> ");
        sb.append("<a target='dialog' href='admin/user/update/");
        sb.append(user.id);
        sb.append("'>");
        sb.append(user.getName());
        sb.append("</a>");
        sb.append("，您好！</li>");
        // 退出
        sb.append("<li><a href='logout'>退出</a></li>");
        return sb.toString();
    }
    
    /**
     * 获取根节点菜单树 parentId=0
     * 
     * @return 返回拼好的html
     */
    @RequestMapping(value = "getRootMenu", method = RequestMethod.GET)
    @ResponseBody
    public String list()
    {
        ShiroUser user = getCurrentUser();
        Long userId = user.id;
        if (UserSessionManager.getInstance().checkKey(TagOrSessionConstants.SESSION_LOGINNAME, user.getLoginName()))
        {
            String sessionMenuPlay =
                String.valueOf(UserSessionManager.getInstance().get(TagOrSessionConstants.SESSION_MENUDISPLAY));
            if (StringUtils.isNotEmpty(sessionMenuPlay))
            {
                log.info("get menuList from session!!!");
                return sessionMenuPlay;
            }
        }
        
        // 根据角色列表获取菜单列表
        List<SystemMenu> menus = sysMenuService.getMenuByUserId(userId);
        String retStr = getMenuDivStr(menus);
        
        log.info("begin to set loginName and menuDispplay");
        UserSessionManager.getInstance().put(TagOrSessionConstants.SESSION_LOGINNAME, user.getLoginName());
        UserSessionManager.getInstance().put(TagOrSessionConstants.SESSION_MENUDISPLAY, retStr);
        log.info("end to set loginName and menuDispplay");
        
        return retStr;
    }
    
    private String getMenuDivStr(List<SystemMenu> menus)
    {
        StringBuilder ret = new StringBuilder();
        
        for (SystemMenu menu : menus)
        {
            ret.append("<div class='accordionHeader'><h2><span>Folder</span>");
            ret.append(menu.getName());
            ret.append("</h2></div>");
            ret.append("<div class='accordionContent'>");
            ret.append("<ul class='tree treeFolder'>");
            
            if (!CollectionUtils.isEmpty(menu.getChildMenus()))
            {
                ret.append(getChildMenuDivStr(menu.getChildMenus()));
            }
            
            ret.append("</ul>");
            ret.append("</div>");
        }
        
        return ret.toString();
    }
    
    private String getChildMenuDivStr(List<SystemMenu> childMenus)
    {
        StringBuilder ret = new StringBuilder();
        
        for (SystemMenu menu : childMenus)
        {
            ret.append("<li><a href='" + menu.getLink() + "' target='navTab' rel='" + menu.getAlias() + "'>");
            ret.append(menu.getName());
            ret.append("</a>");
            if (!CollectionUtils.isEmpty(menu.getChildMenus()))
            {
                ret.append("<ul>");
                ret.append(getChildMenuDivStr(menu.getChildMenus()));
                ret.append("</ul>");
            }
            ret.append("</li>");
        }
        return ret.toString();
    }
    
}
