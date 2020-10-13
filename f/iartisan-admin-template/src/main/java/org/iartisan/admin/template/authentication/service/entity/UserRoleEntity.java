package org.iartisan.admin.template.authentication.service.entity;

import java.util.List;

/**
 * @author King
 * @since 2018/3/7
 */
public class UserRoleEntity {

    private UserEntity userEntity;

    private List<RoleEntity> roleEntities;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public void setRoleEntities(List<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
    }
}
