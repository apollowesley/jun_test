package com.site.auth.dto;

import java.util.ArrayList;
import java.util.List;

import com.site.auth.model.Permission;

/**
 * @author Vincent.wang
 *
 */
public class PermissionDto extends Permission {

    private static final long serialVersionUID = -2051933842290600230L;

    /** 子菜单 **/
    private List<PermissionDto> children = new ArrayList<PermissionDto>();

    public List<PermissionDto> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionDto> children) {
        this.children = children;
    }

}
