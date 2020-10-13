package com.zb.mapper.sys;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zb.bean.sys.SysUser;
import com.zb.common.Pager;
import com.zb.common.qo.UserQo;
import com.zb.common.vo.UserVo;

import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<SysUser> {

	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	List<SysUser> getUserByName(@Param("userName") String username);

	/**
	 * 分页获取用户信息
	 * 
	 * 日期：2016年8月13日 下午6:47:47
	 * 用户：zhoubang
	 * 
	 * @param userQo
	 * @param pager
	 * @return
	 */
    List<UserVo> getUserList(@Param("userQo") UserQo userQo, @Param("pager") Pager<UserVo> pager);

}
