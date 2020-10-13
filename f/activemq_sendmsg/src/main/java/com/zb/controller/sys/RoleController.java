package com.zb.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.bean.sys.SysRole;
import com.zb.bean.sys.SysRolePermission;
import com.zb.common.AjaxResult;
import com.zb.common.Pager;
import com.zb.common.enums.Status;
import com.zb.common.qo.RoleQo;
import com.zb.common.vo.RoleVo;
import com.zb.common.vo.ZtreeVo;
import com.zb.service.sys.PermissionService;
import com.zb.service.sys.RolePermissionService;
import com.zb.service.sys.RoleService;
import com.zb.util.JsonUtil;

/**
 * 
 * 角色管理
 * 
 * 创建日期：2016年8月13日 下午4:07:01
 * 操作用户：zhoubang
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private RolePermissionService rolePermissionService;
    
    /**
     * 进入角色列表页面
     * 
     * 日期：2016年8月13日 下午4:07:06
     * 用户：zhoubang
     * 
     * @param request
     * @param response
     */
    @RequestMapping("/toRoleListView")
    public String toRoleListView(HttpServletRequest request, HttpServletResponse response,ModelMap map) {
        map.put("status", Status.values());
        return "/sys/role/list";
    }
    
    /**
     * 分页获取角色列表
     * 
     * 日期：2016年8月14日 下午1:18:43
     * 用户：zhoubang
     * 
     * @param pager
     * @param userQo
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(Pager<RoleVo> pager, SysRole role) {
        pager = roleService.getList(pager, role);
        return pager;
    }
    
    /**
     * 进入添加角色页面
     * 
     * 日期：2016年8月14日 下午2:11:53
     * 用户：zhoubang
     * 
     * @param modelMap
     * @return
     */
    @RequestMapping("/toAddView")
    public String toAddView(ModelMap modelMap) {
        //获取所有权限列表
        List<ZtreeVo> allPermission = permissionService.queryAllFormatWithZtree(true);
        
        //json字符串包含权限的复选框选中属性设置以及展开属性设置.
        modelMap.put("permissions", JsonUtil.toJson(setPZtreeCheck(allPermission, null)));
        
        return "/sys/role/add";
    }
    
    /**
     * 对角色的权限tree进行操作
     *      对角色拥有的权限进行复选框选中的属性设置,并且展开对应的权限tree。
     * 
     * 作者: zhoubang 
     * 日期：2015年4月29日 上午9:52:16
     * @param list
     * @param rplist
     * @return
     */
    public static List<ZtreeVo> setPZtreeCheck(List<ZtreeVo> list ,List<SysRolePermission> rolePermissionList){
        for (ZtreeVo ztreeVo : list) {
            if(rolePermissionList != null){
                for (SysRolePermission rolePermission : rolePermissionList) {
                    if(StringUtils.equals(String.valueOf(rolePermission.getPermissionId()), ztreeVo.getId())){
                        ztreeVo.setChecked(true);
                    }
                }
            }else{
                ztreeVo.setChecked(false);
            }
            
            ztreeVo.setOpen(true);
        }
        return list;
    }
    
    /**
     * 保存角色
     * 
     * 创建日期：2016年8月4日 下午1:50:10
     * 操作用户：zhoubang
     * 
     * @param role
     * @param permissionIds
     * @return
     */
    @RequestMapping("/addRole")
    @ResponseBody
    public AjaxResult<String> add(SysRole role, Long[] permissionIds) {
        AjaxResult<String> result = new AjaxResult<String>();
        
        if (StringUtils.isBlank(role.getName())) {
            result.setCode(10001);
            result.setMsg("请填写角色名称");
            return result;
        }
        if (StringUtils.isBlank(role.getDescription())) {
            result.setCode(10002);
            result.setMsg("请填写角色描述");
            return result;
        }
        if (permissionIds == null || permissionIds.length == 0) {
            result.setCode(10003);
            result.setMsg("请至少选择一个权限");
            return result;
        }
        
        try {
            roleService.addRole(role,permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(10004);
            result.setMsg("添加失败");
            return result;
        }
        return result;
    }
    
    /**
     * 进入角色编辑页面
     * 
     * 日期：2016年8月20日 下午5:01:36
     * 用户：zhoubang
     * 
     * @param modelMap
     * @param qo
     * @return
     */
    @RequestMapping("/toEditView")
    public String toEditView(ModelMap map,RoleQo qo) {
        logger.debug("角色编辑：qo：" + JsonUtil.toJson(qo));
        
        //获取所有权限列表
        List<ZtreeVo> allPermission = permissionService.queryAllFormatWithZtree(true);
        
        //角色拥有的权限
        List<SysRolePermission> rolePermissions = rolePermissionService.getPermissionByRoleId(qo.getRoleId());
        
        //json字符串包含权限的复选框选中属性设置以及展开属性设置.
        map.put("permissions", JsonUtil.toJson(setPZtreeCheck(allPermission, rolePermissions)));
        
        //角色对象
        map.put("role", roleService.get(qo.getRoleId()));
        
        map.put("status", Status.values());
        return "/sys/role/edit";
    }
    
    /**
     * 更新角色信息
     * 
     * 日期：2016年8月20日 下午5:21:04
     * 用户：zhoubang
     * 
     * @param role
     * @param permissionIds
     * @return
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    public AjaxResult<String> updateRole(SysRole role, Long[] permissionIds) {
        AjaxResult<String> result = new AjaxResult<String>();
        
        if (StringUtils.isBlank(role.getName())) {
            result.setCode(10001);
            result.setMsg("请填写角色名称");
            return result;
        }
        
        if (StringUtils.isBlank(role.getDescription())) {
            result.setCode(10002);
            result.setMsg("请填写角色描述");
            return result;
        }
        
        if(permissionIds == null || permissionIds.length <= 0){
            result.setCode(10003);
            result.setMsg("请为角色指定权限");
            return result;
        }
        
        if(role.getId() == null){
            result.setCode(10004);
            result.setMsg("角色ID不存在");
            return result;
        }
        
        SysRole roleObj = roleService.get(role.getId());
        if(roleObj == null){
            result.setCode(10005);
            result.setMsg("该角色不存在");
            return result;
        }
        
        //更新角色
        try {
            roleService.updateRole(role,permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(10006);
            result.setMsg("更新失败");
            return result;
        }
        
        return result;
    }
    
    /**
     * 删除角色
     * 
     * 日期：2016年8月20日 下午5:53:16
     * 用户：zhoubang
     * 
     * @param roleId
     * @return
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxResult<String> deleteRole(Long roleId) {
        AjaxResult<String> result = new AjaxResult<String>();
        
        if (roleId == null) {
            result.setCode(10001);
            result.setMsg("角色ID为空");
            return result;
        }
        
        SysRole role = roleService.get(roleId);
        if (role == null) {
            result.setCode(10002);
            result.setMsg("角色不存在");
            return result;
        }
        
        //删除角色
        try {
            roleService.deleteRole(role,result);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(10003);
            result.setMsg("角色删除失败");
            return result;
        }
        return result;
    }
    
}
