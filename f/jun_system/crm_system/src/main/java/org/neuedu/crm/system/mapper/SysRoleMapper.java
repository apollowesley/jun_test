package org.neuedu.crm.system.mapper;

import org.apache.ibatis.annotations.Select;
import org.neuedu.crm.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author CDHong
 * @since 2019-12-13
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select(" select r.id,r.role_name,r.remark,r.create_time from sys_role r " +
            " join sys_user_role ur on ur.role_id = r.id where ur.user_id = #{userId} ")
    List<SysRole> findRolesByUserId(Integer userId);
}
