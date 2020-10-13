package luna.com.firefly.web.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import luna.com.firefly.entity.system.SystemRole;
import luna.com.firefly.entity.system.SystemUser;
import luna.com.firefly.entity.system.SystemUserRole;
import luna.com.firefly.service.system.AccountService;
import luna.com.firefly.service.system.ShiroDbRealm.ShiroUser;
import luna.com.firefly.service.system.SystemRoleService;
import luna.com.firefly.utils.ArrayUtil;
import luna.com.firefly.utils.HttpResult;
import luna.com.firefly.utils.HttpStatus;
import luna.com.firefly.utils.Servlets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
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
 * 管理员管理用户的Controller.
 * 
 * @author 陆小凤
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/user")
public class SystemUserController extends SystemBasicController
{
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private SystemRoleService roleManageService;
    
    /**
     * Ajax请求校验loginName是否唯一。
     */
    @RequestMapping(value = "checkRoleName/{id}")
    @ResponseBody
    public boolean checkRoleName(@RequestParam("name") String roleName, @PathVariable("id") Long roleId)
    {
        log.info("Enter function [checkRoleName] with params roleName:{},roleId:{}", roleName, roleId);
        // 添加 角色时,id默认为 -100L 修改时id为表中数据
        Long valId = -100L;
        SystemRole SystemRole = new SystemRole();
        boolean validatePass = true;
        if (valId == roleId)
        {
            SystemRole = roleManageService.findRoleByName(roleName);
        }
        else
        {
            SystemRole = roleManageService.findRoleByNameAndId(roleName, roleId);
        }
        if (null != SystemRole)
        {
            validatePass = false;
        }
        log.info("Exit function [checkRoleName] with params validatePass:{}", validatePass);
        return validatePass;
    }
    
    @RequestMapping(value = "userList")
    public String list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNumber,
        @RequestParam(value = "numPerPage", defaultValue = "20") int pageSize,
        @RequestParam(value = "isAdvencedSearch", defaultValue = "0") Integer isAdvencedSearch, Model model,
        ServletRequest request)
    {
        ShiroUser user = getCurrentUser();
        
        // 获取查询条件
        Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
        try
        {
            searchParams.put("NE_roles", "system_user");
            // 根据条件查询用户信息
            // 根据条件查询平台信息
            /*
             * boolean isAdmin = getUserRole(user.id); // 如果有超级管理员权限可以查看所有平台信息 if (!isAdmin) { //
             * 没有管理员权限,则需要根据platformId 查询 searchParams.put("EQ_platformId", user.platformId.toString()); plat =
             * platService.getPlatform(getCurrentUser().platformId); platformList.add(plat); } else { platformList =
             * platService.findAll(); }
             */
            Page<SystemUser> users = accountService.getUserList(searchParams, pageNumber, pageSize);
            model.addAttribute("user", users);
            model.addAttribute("totalCount", users.getTotalElements());
            // 判断是不是高级查询 0-高级查询
            if (isAdvencedSearch.intValue() != 0)
            {
                if (null != request.getParameter("searchName"))
                    searchParams.put(request.getParameter("searchName"), request.getParameter("searchValue"));
                // //写入操作日志
                // operationLogService.logOptAsync("",
                // SysDicConstants.OPR_TYPE_QUERY,
                // CoreConstants.OPRDATATYPE_USER,
                // getCurrentUser().id.longValue(),
                // request.getRemoteAddr(),
                // getCurrentUser().platformId);
            }
        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.error("Inner func[UserAdminController.list]." + " catch exception while get firm list ", e);
            }
        }
        finally
        {
            model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
            model.addAttribute("pageNum", pageNumber);
            model.addAttribute("numPerPage", pageSize);
            // model.addAttribute("platformList", platformList);
        }
        
        return "manager/user/userList";
    }
    
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model)
    {
        
        List<SystemUserRole> rel = new ArrayList<SystemUserRole>();
        boolean isAdmin = false;
        Long adminRole = 1L;
        rel = roleManageService.getUserRoleRel(getCurrentUser().id);
        for (SystemUserRole r : rel)
        {
            if (adminRole == r.getRoleId())
            {
                isAdmin = true;
            }
        }
        model.addAttribute("user", accountService.getUser(id));
        
        return "manager/user/userInfo";
    }
    
    @RequestMapping(value = "/update")
    @ResponseBody
    public HttpResult update(@Valid @ModelAttribute("user") SystemUser user, HttpServletRequest request,
        HttpServletResponse response)
    {
        HttpResult result = new HttpResult();
        try
        {
            accountService.updateUser(user);
            // 执行成功则 返回信息 为 SUCCESS
            result.setResult(HttpStatus.OK);
            
        }
        catch (Exception e)
        {
            // 执行失败则 返回信息 为 ERROR
            result.setResult("300", "操作失败");
            log.error("Enter func[UserAdminController.update]" + e);
        }
        // 页面返回信息
        return result;
    }
    
    /**
     * <进入编辑用户绑定角色页面>
     * 
     * @param userId
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/setUserRole")
    public String setUserRole(@ModelAttribute("id") Long userId, Model model, HttpServletRequest request,
        HttpServletResponse response)
    {
        List<SystemUserRole> SystemUserRole = new ArrayList<SystemUserRole>();
        SystemRole SystemRole = new SystemRole();
        List<Long> roleIds = new ArrayList<Long>();
        List<SystemRole> roleListChecked = new ArrayList<SystemRole>();
        List<SystemRole> roleListNoChecked = new ArrayList<SystemRole>();
        try
        {
            SystemUserRole = roleManageService.getUserRoleRel(userId);
            for (SystemUserRole rel : SystemUserRole)
            {
                SystemRole = roleManageService.getRole(rel.getRoleId());
                roleListChecked.add(SystemRole);
                roleIds.add(rel.getRoleId());
            }
            if (!roleIds.isEmpty())
            {
                roleListNoChecked = roleManageService.getRoleLists(roleIds);
            }
            else
            {
                roleListNoChecked = roleManageService.getAllRole();
            }
            model.addAttribute("userIdSelected", userId);
            model.addAttribute("notCheckRolesList", roleListNoChecked);
            model.addAttribute("checkedRolesList", roleListChecked);
        }
        catch (Exception e)
        {
            if (log.isErrorEnabled())
            {
                log.info("Enter func[UserAdminController.setUserRole]" + e);
            }
        }
        return "manager/user/userRole";
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/addUserRoles")
    @ResponseBody
    public HttpResult addUserRoles(@ModelAttribute("userId") String userId, @ModelAttribute("roleId") String roleId,
        HttpServletRequest request, HttpServletResponse response)
    {
        HttpResult result = new HttpResult();
        try
        {
            roleManageService.InsertUserRoleRel(userId, roleId);
            result.setResult(HttpStatus.OK);
        }
        catch (Exception e)
        {
            result.setResult("300", "操作失败");
            log.error("Enter func[UserAdminController.addUserRoles]" + e);
        }
        return result;
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/delUserRoles")
    @ResponseBody
    public HttpResult delUserRoles(@ModelAttribute("userId") String userId, @ModelAttribute("roleId") String roleId,
        HttpServletRequest request, HttpServletResponse response)
    {
        HttpResult result = new HttpResult();
        try
        {
            roleManageService.DelUserRoleRel(userId, roleId);
            result.setResult(HttpStatus.OK);
        }
        catch (Exception e)
        {
            result.setResult("300", "操作失败");
            log.error("Enter func[UserAdminController.addUserRoles]" + e);
        }
        return result;
    }
    
    /**
     * <删除user>
     * 
     * @param response
     * @param request
     */
    @RequestMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResult deleteByIds(HttpServletResponse response, HttpServletRequest request)
    {
        // 获得所有需要删除的 运营商id
        String[] ids = request.getParameterValues("ids");
        HttpResult result = new HttpResult();
        
        try
        {
            String IDs = "";
            for (int i = 0; i < ids.length; i++)
            {
                // 执行删除操作
                accountService.deleteUser((Long.parseLong(ids[i])));
                IDs += ids[i] + ",";
            }
            IDs = IDs.substring(0, IDs.length() - 1);
            result.setResult(HttpStatus.OK);
            
        }
        catch (Exception e)
        {
            result.setResult("300", "操作失败");
            log.error("Enter func[UserAdminController.delete]" + e);
        }
        // 返回客户端信息，打印相关信息
        return result;
        
    }
    
    @RequestMapping(value = "pageBatchInfo")
    public String pageBatchInfo(@RequestParam("ids") String ids, ModelMap model)
    {
        model.addAttribute("ids", ids);
        
        return "manager/user/batchInfo";
    }
    
    @RequestMapping(value = "batchModify", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HttpResult batchModify(@RequestParam("cpCodes") String cpCodes,
        @RequestParam("programTypeIds") String programTypeIds, @RequestParam("materialTypeIds") String materialTypeIds,
        @RequestParam("ids") String ids, HttpServletRequest request)
    {
        HttpResult result = new HttpResult();
        try
        {
            String[] userIds = StringUtils.split(ids, ",");
            List<Long> idList = ArrayUtil.convertStringArrayToLongList(userIds);
            
            result.setResult(HttpStatus.OK);
        }
        catch (Exception ex)
        {
            result.setResult(HttpResult.SYSTEM_ERROR, "操作失败");
            log.error("Inner func[UserAdminController.batchModify()] catch exception..", ex);
        }
        
        return result;
    }
    
    /**
     * <查询User信息>
     * 
     * @param type
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "getUser")
    @ResponseBody
    public List<List> getUser()
    {
        List<List> result = new ArrayList<List>();
        List<String> list = null;
        List<SystemUser> userList = accountService.getAllUser();
        for (SystemUser user : userList)
        {
            list = new ArrayList<String>();
            list.add(user.getName() + "");
            list.add(user.getName());
            result.add(list);
        }
        return result;
    }
    
    /**
     * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
     * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
     */
    @ModelAttribute
    public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model)
    {
        if (id != -1)
        {
            model.addAttribute("user", accountService.getUser(id));
        }
    }
    
}
