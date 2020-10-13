package org.iartisan.admin.template.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;

import java.util.Date;
/**
* system_user_role 表模型
* @author King
*/
@Alias("systemUserRole")
@TableName(value = "system_user_role")
public class SystemUserRoleDO {

        /**
        * 列名: USER_ID
        * 备注: 用户ID
        */
        @TableField("USER_ID")
        private String userId;

        /**
        * 列名: ROLE_ID
        * 备注: 角色ID
        */
        @TableField("ROLE_ID")
        private String roleId;

        /**
        * 列名: CREATE_TIME
        * 备注: 创建时间
        */
        @TableField("CREATE_TIME")
        private Date createTime;

        /**
        * 列名: UPDATE_TIME
        * 备注: 更新时间
        */
        @TableField("UPDATE_TIME")
        private Date updateTime;


        public String getUserId(){
        return userId;
      }

        public void setUserId(String userId){
          this.userId = userId;
      }

        public String getRoleId(){
        return roleId;
      }

        public void setRoleId(String roleId){
          this.roleId = roleId;
      }

        public Date getCreateTime(){
        return createTime;
      }

        public void setCreateTime(Date createTime){
          this.createTime = createTime;
      }

        public Date getUpdateTime(){
        return updateTime;
      }

        public void setUpdateTime(Date updateTime){
          this.updateTime = updateTime;
      }

}