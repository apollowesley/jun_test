package org.neuedu.crm.system.mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.neuedu.crm.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author CDHong
 * @since 2019-12-13
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select(" select u.id,u.real_name from sys_user u " +
            " where u.id in (select ur.user_id from sys_user_role ur where ur.role_id = #{roleId} ) ")
    List<SysUser> findUserByRoleId(Integer roleId);

    @Results({
        @Result(id = true,property = "id",column = "id"),
        @Result(property = "roles",column = "id",javaType = List.class,
                many = @Many(select = "org.neuedu.crm.system.mapper.SysRoleMapper.findRolesByUserId"))
    })
    @Select(" select id, real_name, login_name, login_password, gender, remark, create_time from sys_user u where u.login_name = #{loginName} ")
    SysUser findUserDetailByLoginName(String loginName);

}
