package org.iartisan.admin.template.dao.model;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.ibatis.type.Alias;
/**
* system_menu 表模型
* @author King
*/
@Alias("systemMenu")
@TableName(value = "system_menu")
public class SystemMenuDO {

        /**
        * 列名: MENU_ID
        * 备注: MENU_ID
        */
        @TableId("MENU_ID")
        private String menuId;

        /**
        * 列名: MENU_NAME
        * 备注: 菜单名称
        */
        @TableField("MENU_NAME")
        private String menuName;

        /**
        * 列名: MENU_PERMISSION
        * 备注: 菜单权限编码 menu:一级菜单:二级菜单
        */
        @TableField("MENU_PERMISSION")
        private String menuPermission;

        /**
        * 列名: MENU_URL
        * 备注: 菜单跳转链接
        */
        @TableField("MENU_URL")
        private String menuUrl;

        /**
        * 列名: MENU_ICON
        * 备注: 菜单icon
        */
        @TableField("MENU_ICON")
        private String menuIcon;

        /**
        * 列名: PARENT_MENU_ID
        * 备注: 上一级菜单id
        */
        @TableField("PARENT_MENU_ID")
        private String parentMenuId;

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

        private List<String> menuIds;


        public String getMenuId(){
        return menuId;
      }

        public void setMenuId(String menuId){
          this.menuId = menuId;
      }

        public String getMenuName(){
        return menuName;
      }

        public void setMenuName(String menuName){
          this.menuName = menuName;
      }

        public String getMenuPermission(){
        return menuPermission;
      }

        public void setMenuPermission(String menuPermission){
          this.menuPermission = menuPermission;
      }

        public String getMenuUrl(){
        return menuUrl;
      }

        public void setMenuUrl(String menuUrl){
          this.menuUrl = menuUrl;
      }

        public String getMenuIcon(){
        return menuIcon;
      }

        public void setMenuIcon(String menuIcon){
          this.menuIcon = menuIcon;
      }

        public String getParentMenuId(){
        return parentMenuId;
      }

        public void setParentMenuId(String parentMenuId){
          this.parentMenuId = parentMenuId;
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

    public List<String> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<String> menuIds) {
        this.menuIds = menuIds;
    }
}