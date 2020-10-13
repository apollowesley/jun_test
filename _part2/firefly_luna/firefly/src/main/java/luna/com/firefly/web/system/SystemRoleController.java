package luna.com.firefly.web.system;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import luna.com.firefly.entity.system.SystemButton;
import luna.com.firefly.entity.system.SystemMenu;
import luna.com.firefly.entity.system.SystemRole;
import luna.com.firefly.entity.system.SystemRoleMenu;
import luna.com.firefly.utils.HttpResult;
import luna.com.firefly.utils.Servlets;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <角色信息管理>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年9月11日]
 */
@Slf4j
@Controller
@RequestMapping(value = "/roleManage")
public class SystemRoleController extends SystemBasicController
{
    
    /**
     * menu主菜单parentId -
     */
    private static final Long PARENT_ID = 0l;
    
    /**
     * <查询角色信息>
     * 
     * @param pageNumber
     * @param pageSize
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "roleList")
    public String roleList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNumber,
        @RequestParam(value = "numPerPage", defaultValue = "20") int pageSize,
        @RequestParam(value = "isAdvencedSearch", defaultValue = "0") Integer isAdvencedSearch, Model model,
        ServletRequest request)
    {
        // 获取查询条件
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        try
        {
            // 根据条件查询角色信息
            Page<SystemRole> roleList = roleService.getRoleList(searchParams, pageNumber, pageSize);
            model.addAttribute("role", roleList);
            model.addAttribute("totalCount", roleList.getTotalElements());
            model.addAttribute("pageNum", pageNumber);
            model.addAttribute("numPerPage", pageSize);
        }
        catch (Exception e)
        {
            log.error("Inner func[roleList]  catch exception while get SystemRole list ", e);
        }
        return "manager/role/roleList";
    }
    
    /**
     * <进入角色添加页面>
     * 
     * @return
     */
    @RequestMapping(value = "add")
    public String pageAddInfo()
    {
        return "manager/role/roleInfo";
    }
    
    /**
     * <删除平台信息> <根据id删除平台信息>
     * 
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public HttpResult deleteByIds(HttpServletResponse response, HttpServletRequest request)
    {
        // 获得-选中删除的 平台id
        String[] ids = request.getParameterValues("ids");
        try
        {
            for (String id : ids)
            {
                // 执行删除操作
                roleService.deleteRole(Long.valueOf(id));
            }
            return getCorrectResult();
        }
        catch (Exception e)
        {
            // 执行失败则 返回信息 为 ERROR
            log.error("Enter func[delete] catch exception..", e);
            return getErrorResult();
        }
        
    }
    
    /**
     * <进入角色编辑页面>
     * 
     * @return
     */
    @RequestMapping(value = "edit")
    public String roleEditInfo(@ModelAttribute("id") String id, ModelMap model, HttpServletResponse response,
        HttpServletRequest request)
    {
        SystemRole role = new SystemRole();
        try
        {
            role = roleService.getRole(Long.parseLong(id));
            model.addAttribute("role", role);
        }
        catch (Exception e)
        {
            log.info("Enter func[roleEditInfo]", e);
        }
        return "manager/role/roleInfo";
        
    }
    
    /**
     * <执行角色添加，修改操作>
     * 
     * @param role
     * @param request
     * @param response
     */
    @RequestMapping(value = "saveInfo")
    @ResponseBody
    public HttpResult saveInfo(@Valid @ModelAttribute("role") SystemRole role, HttpServletRequest request,
        HttpServletResponse response)
    {
        try
        {
            if (null != role.getId())
            {
                SystemRole oldRole = roleService.findOne(role.getId());
                if (null != oldRole)
                {
                    role.setCreateTime(oldRole.getCreateTime());
                }
                role.setUpdateTime(new Date());
            }
            else
            {
                role.setCreateUser(this.getCurrentUser().name);
                role.setCreateTime(new Date());
            }
            // 执行保存信息 操作
            roleService.saveRole(role);
            // 执行成功则 返回信息 为 SUCCESS
            return getCorrectResult();
        }
        catch (Exception e)
        {
            log.error("Enter func[saveInfo] catch exception", e);
            return getErrorResult();
        }
    }
    
    /**
     * <根据角色获得相应的菜单-tree>
     * 
     * @param roleId
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getMenu", method = RequestMethod.GET)
    public String getMenuTree(@ModelAttribute("id") Long roleId, ModelMap model, HttpServletRequest request,
        HttpServletResponse response)
    {
        try
        {
            List<SystemMenu> menus = sysMenuService.getMenuByParentId(PARENT_ID);
            List<SystemRoleMenu> rel = roleService.getRoleMenuRel(roleId);
            List<String> roleButtonCodes = buttonService.findRoleButtonCodesByRoleId(roleId);
            for (SystemRoleMenu ls : rel)
            {
                for (SystemMenu menu : menus)
                {
                    if (ls.getMenuId().equals(menu.getId()))
                    {
                        menu.setChecked(true);
                    }
                    for (SystemMenu me : menu.getChildMenus())
                    {
                        if (ls.getMenuId().equals(me.getId()))
                        {
                            me.setChecked(true);
                        }
                    }
                }
            }
            for (SystemMenu menu : menus)
            {
                List<SystemMenu> childMenus = menu.getChildMenus();
                for (SystemMenu childMenu : childMenus)
                {
                    List<SystemButton> buttonList = childMenu.getChildButton();
                    for (SystemButton button : buttonList)
                    {
                        if (roleButtonCodes.contains(button.getButtonCode()))
                        {
                            button.setChecked(true);
                        }
                    }
                }
            }
            model.addAttribute("menus", menus);
            // 将角色Id 返回到页面-用于下面执行更新角色查看菜单权限操作
            model.addAttribute("roleId", roleId);
        }
        catch (Exception e)
        {
            log.error("Enter func[getMenuTree]", e);
        }
        return "manager/role/roleMenuList";
    }
    
    /**
     * <执行角色更新权限信息，修改操作>
     * 
     * @param menuId
     * @param request
     * @param response
     */
    @RequestMapping(value = "roleMenu")
    @ResponseBody
    public HttpResult roleMenuInfo(@ModelAttribute("strId") String menuId, HttpServletRequest request,
        HttpServletResponse response)
    {
        try
        {
            // 执行保存信息 操作
            String roleId = request.getParameter("roleId");
            if (StringUtils.isNotBlank(roleId))
            {
                // 更新角色 可以查看菜单的权限
                roleService.updateRoleMenuRel(menuId, roleId);
            }
            // 执行成功则 返回信息 为 SUCCESS
            return getCorrectResult();
        }
        catch (Exception e)
        {
            log.error("Enter func[roleMenuInfo]", e);
            return getErrorResult();
        }
    }
    
    /**
     * <校验角色名称的唯一性>
     * 
     * @return
     */
    @RequestMapping(value = "validate/{roleId}")
    @ResponseBody
    public boolean validate(@ModelAttribute("role") SystemRole role, @PathVariable("roleId") Long roleId)
    {
        log.info("Enter func [validate] with params roleName:{},roleId:{}", role.getName(), roleId);
        String roleName = role.getName();
        boolean validatePass = true;
        
        SystemRole tempRole = new SystemRole();
        tempRole = roleService.findRoleByName(roleName);
        
        if (null != tempRole && roleId != tempRole.getId())
        {
            validatePass = false;
        }
        log.info("Exit func [validate] with params validatePass:{}", validatePass);
        return validatePass;
    }
    
}
