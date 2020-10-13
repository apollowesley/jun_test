package com.zb.controller.sys;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zb.bean.sys.SysPermission;
import com.zb.common.AjaxResult;
import com.zb.common.Pager;
import com.zb.common.enums.Status;
import com.zb.common.vo.PermissionVo;
import com.zb.common.vo.ZtreeVo;
import com.zb.service.sys.PermissionService;
import com.zb.util.JsonUtil;
/**
 * 角色管理
 * 
 * 
 * 创建日期：2016年8月4日 上午9:39:35 操作用户：zhoubang
 *
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 进入权限管理页面
     * 
     * 创建日期：2016年8月4日 上午9:39:59
     * 操作用户：zhoubang
     * 
     * @param map
     * @return
     */
    @RequestMapping("/toListView")
    public String toListView(ModelMap map) {
        map.put("status", Status.values());
        return "/sys/permission/list";
    }
    
    
    /**
     * 分页获取所有权限列表
     * 
     * 创建日期：2016年8月4日 上午10:27:50
     * 操作用户：zhoubang
     * 
     * @param pager
     * @param role
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object list(Pager<PermissionVo> pager, SysPermission permission) {
        pager = permissionService.getList(pager, permission);
        return pager;
    }
    
    
    /**
     * 进入权限添加页面
     * 
     * 创建日期：2016年8月4日 下午3:05:42
     * 操作用户：zhoubang
     * 
     * @param map
     * @return
     */
    @RequestMapping("/toAddView")
    public String toAddView(ModelMap map) {
        
        //获取所有权限列表
        List<ZtreeVo> allPermission = permissionService.queryAllFormatWithZtree(false);
        
        //json字符串包含权限的复选框选中属性设置以及展开属性设置.
        map.put("permissions", JsonUtil.toJson(setPZtreeCheck(allPermission)));
        
        map.put("status", Status.values());
        return "/sys/permission/add";
    }
    
    
    /**
     * 对权限tree进行操作
     *      对权限进行复选框选中的属性设置,并且展开对应的权限tree。
     * 
     * 作者: zhoubang 
     * 日期：2015年4月29日 上午9:52:16
     * @param list
     * @param rplist
     * @return
     */
    public static List<ZtreeVo> setPZtreeCheck(List<ZtreeVo> list){
        for (ZtreeVo ztreeVo : list) {
            ztreeVo.setChecked(false);
            ztreeVo.setOpen(true);
        }
        return list;
    }
    
    
    /**
     * 添加权限
     * 
     * 创建日期：2016年8月4日 下午3:29:56
     * 操作用户：zhoubang
     * 
     * @param permission
     * @return
     */
    @RequestMapping("/addPermission")
    @ResponseBody
    public AjaxResult<String> addPermission(SysPermission permission) {
        AjaxResult<String> result = new AjaxResult<String>();
        
        if (StringUtils.isBlank(permission.getName())) {
            result.setCode(10001);
            result.setMsg("请填写权限名称");
            return result;
        }
        if (StringUtils.isBlank(permission.getDescription())) {
            result.setCode(10002);
            result.setMsg("请填写权限描述");
            return result;
        }
        
        if (StringUtils.isBlank(permission.getCode())) {
            result.setCode(10003);
            result.setMsg("请填写权限代码");
            return result;
        }
        
        if (permission.getParentId() == null) {
            result.setCode(10004);
            result.setMsg("请设置权限所属层级");
            return result;
        }
        
        try {
            permissionService.save(permission);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(10005);
            result.setMsg("添加失败");
            return result;
        }
        return result;
    }
    
    /**
     * 进入权限编辑页面
     * 
     * 创建日期：2016年8月4日 下午3:45:41
     * 操作用户：zhoubang
     * 
     * @param modelMap
     * @param permissionId
     * @return
     */
    @RequestMapping("/editPermissionView")
    public String editPermissionView(ModelMap modelMap,Long permissionId) {
        
        //权限信息
        SysPermission permission = permissionService.get(permissionId);
        modelMap.put("permission", permission);
        
        //获取所有权限列表
        List<ZtreeVo> allPermission = permissionService.queryAllFormatWithZtree(true);
        
        //角色对应的权限.json字符串包含权限的复选框选中属性设置以及展开属性设置.
        modelMap.put("permissions", JsonUtil.toJson(setPZtreeCheck(allPermission)));
        
        modelMap.put("status", Status.values());
        
        return "/sys/permission/edit";
    }
    
    
    /**
     * 权限删除
     * 
     * 创建日期：2016年8月4日 下午5:24:23
     * 操作用户：zhoubang
     * 
     * @param permissionId
     * @return
     */
    @RequestMapping("/deletePermission")
    @ResponseBody
    public AjaxResult<String> deletePermission(Long permissionId) {
        AjaxResult<String> result = new AjaxResult<String>();
        
        if(null == permissionId || "".equals(permissionId)){
            result.setCode(10001);
            result.setMsg("请选择一个权限删除");
            return result;
        }
        
        SysPermission permission = permissionService.get(permissionId);
        if(permission == null){
            result.setCode(10002);
            result.setMsg("该权限不存在");
            return result;
        }
        
        try {
            //删除权限
            permissionService.deletePermission(permission,result);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(10003);
            result.setMsg("删除权限失败");
            return result;
        }
        return result;
    }
    
    /**
     * 更新权限信息
     * 
     * 日期：2016年8月20日 下午6:13:06
     * 用户：zhoubang
     * 
     * @param permission
     * @param permissionParentId
     * @return
     */
    @RequestMapping("/updatePermission")
    @ResponseBody
    public AjaxResult<String> updatePermission(SysPermission permission) {
        AjaxResult<String> result = new AjaxResult<String>();
        
        if (permission.getId() == null) {
            result.setCode(10001);
            result.setMsg("权限ID为空");
            return result;
        }
        
        if (StringUtils.isBlank(permission.getName())) {
            result.setCode(10002);
            result.setMsg("请输入权限名称");
            return result;
        }
        
        if(permission.getStatus() == null){
            result.setCode(10003);
            result.setMsg("请选择权限状态");
            return result;
        }
        
        SysPermission p = permissionService.get(permission.getId());
        if(p == null){
            result.setCode(10005);
            result.setMsg("该权限不存在");
            return result;
        }
        
        //更新权限
        try {
            permissionService.updatePermission(permission);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(10007);
            result.setMsg("权限更新失败");
            return result;
        }
        
        return result;
    }
    
}
