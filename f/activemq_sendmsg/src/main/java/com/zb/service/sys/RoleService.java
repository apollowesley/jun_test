package com.zb.service.sys;

import java.util.List;
import com.zb.bean.sys.SysRole;
import com.zb.common.AjaxResult;
import com.zb.common.Pager;
import com.zb.common.vo.RoleVo;
import com.zb.common.vo.ZtreeVo;
import com.zb.service.BaseService;


public interface RoleService extends BaseService<SysRole> {

    /**
     * 获取用户拥有的角色列表
     * 
     * 日期：2016年8月14日 下午1:19:26
     * 用户：zhoubang
     * 
     * @param username
     * @return
     */
    List<SysRole> getUserRoles(String username);

    /**
     * 分页获取角色列表
     * 
     * 日期：2016年8月14日 下午1:19:19
     * 用户：zhoubang
     * 
     * @param pager
     * @param role
     * @return
     */
    Pager<RoleVo> getList(Pager<RoleVo> pager, SysRole role);

    /**
     * 添加角色
     * 
     * 日期：2016年8月14日 下午2:09:04
     * 用户：zhoubang
     * 
     * @param role
     * @param permissionIds  角色所拥有的权限
     */
    void addRole(SysRole role, Long[] permissionIds) throws Exception;

    /**
     * 获取所有角色列表
     * 
     * 日期：2016年8月20日 下午2:03:43
     * 用户：zhoubang
     * 
     * @param haveAdmin 是否包含管理员角色
     * @return
     */
    List<SysRole> getAllRole(boolean haveAdmin);

    /**
     * 用户角色tree。
     * 
     * 日期：2016年8月20日 下午2:54:38
     * 用户：zhoubang
     * 
     * @param isShowTopParent tree顶层是否显示
     * @param haveAdmin 是否显示管理员角色
     * @return
     */
    List<ZtreeVo> queryAllFormatWithZtree(boolean isShowTopParent,boolean haveAdmin);

    /**
     * 更新角色、权限
     * 
     * 日期：2016年8月20日 下午5:24:42
     * 用户：zhoubang
     * 
     * @param role
     * @param permissionIds
     * @throws Exception
     */
    void updateRole(SysRole role, Long[] permissionIds) throws Exception;

    /**
     * 删除角色
     * 
     * 日期：2016年8月20日 下午5:54:52
     * 用户：zhoubang
     * 
     * @param role
     * @throws Exception
     */
    void deleteRole(SysRole role,AjaxResult<String> result) throws Exception;

}
