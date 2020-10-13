package com.zb.mapper.sys;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.zb.bean.sys.SysRole;
import com.zb.common.Pager;
import com.zb.common.vo.RoleVo;

import tk.mybatis.mapper.common.Mapper;

public interface RoleMapper extends Mapper<SysRole> {

    /**
     * 获取用户拥有的角色列表
     * 
     * 日期：2016年8月14日 下午1:20:26
     * 用户：zhoubang
     * 
     * @param userName
     * @return
     */
    List<SysRole> getUserRoles(@Param("userName") String userName);

    /**
     * 分页获取角色列表
     * 
     * 日期：2016年8月14日 下午1:20:18
     * 用户：zhoubang
     * 
     * @param roleVo
     * @param pager
     * @return
     */
    List<RoleVo> getList(@Param("role") SysRole role, @Param("pager") Pager<RoleVo> pager);

}
