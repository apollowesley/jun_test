package com.zlkj.shiro.dyprem.taglib;

import java.io.Serializable;
import java.util.Collection;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.zlkj.shiro.dyprem.entity.Resource;
import com.zlkj.shiro.dyprem.entity.Role;
import com.zlkj.shiro.dyprem.service.OrganizationService;
import com.zlkj.shiro.dyprem.service.ResourceService;
import com.zlkj.shiro.dyprem.service.RoleService;
import com.zlkj.shiro.dyprem.spring.SpringUtils;


public class Organization implements Serializable{
	private String name="你好";
	private static final long serialVersionUID = -1448113204178944307L;
	private static Organization organization;
	private Organization(){}
	public static Organization getinstance(){
		if (null==organization)organization =new Organization();
			return organization;
	}
	public OrganizationService getOrganizationService (){
		return	SpringUtils.getBean(OrganizationService.class);
	}
	
	public ResourceService getResourceService(){
		return	SpringUtils.getBean(ResourceService.class);
	}
	public RoleService getRoleService (){
		return	SpringUtils.getBean(RoleService.class);
	}
	
	public static boolean in(Iterable iterable, Object element) {
        if(iterable == null) {
            return false;
        }
        return CollectionUtils.contains(iterable.iterator(), element);
    }
	
    public  String organizationName(Long organizationId) {
        com.zlkj.shiro.dyprem.entity.Organization organization//
        			= getOrganizationService().findOne(organizationId);
        if(organization == null) {
            return "";
        }
        return organization.getName();
    }

    public  String organizationNames(Collection<Long> organizationIds) {
        if(CollectionUtils.isEmpty(organizationIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for(Long organizationId : organizationIds) {
            com.zlkj.shiro.dyprem.entity.Organization organization =getOrganizationService().findOne(organizationId);
            if(organization == null) {
                return "";
            }
            s.append(organization.getName());
            s.append(",");
        }

        if(s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }
    public  String roleName(Long roleId) {
        Role role =getRoleService().findOne(roleId);
        if(role == null) {
            return "";
        }
        return role.getDescription();
    }

    public  String roleNames(Collection<Long> roleIds) {
        if(CollectionUtils.isEmpty(roleIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for(Long roleId : roleIds) {
            Role role = getRoleService().findOne(roleId);
            if(role == null) {
                return "";
            }
            s.append(role.getDescription());
            s.append(",");
        }

        if(s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }
    public  String resourceName(Long resourceId) {
        Resource resource = getResourceService().findOne(resourceId);
        if(resource == null) {
            return "";
        }
        return resource.getName();
    }
    public  String resourceNames(Collection<Long> resourceIds) {
        if(CollectionUtils.isEmpty(resourceIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for(Long resourceId : resourceIds) {
            Resource resource =getResourceService().findOne(resourceId);
            if(resource == null) {
                return "";
            }
            s.append(resource.getName());
            s.append(",");
        }

        if(s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }	
}
