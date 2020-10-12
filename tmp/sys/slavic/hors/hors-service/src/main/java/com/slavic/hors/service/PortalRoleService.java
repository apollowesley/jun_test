package com.slavic.hors.service;

import com.slavic.hors.orm.entity.HorsPortalResource;
import com.slavic.hors.orm.entity.HorsPortalRole;
import com.slavic.hors.orm.entity.HorsPortalRoleResource;
import com.slavic.hors.orm.entity.HorsPortalUser;

import java.util.List;

public interface PortalRoleService {

    List<HorsPortalRole> list(HorsPortalRole query);

    List<HorsPortalRole> userRoles(Long userId);

    HorsPortalRole update(HorsPortalRole role);

    HorsPortalRole findById(Long id);

    boolean delete(Long id);

    boolean reRoles(Long userId, List<Long> roleIds);

    boolean reResources(Long roleId, List<Long> resourceIds);

    boolean add(HorsPortalRole user);

    List<HorsPortalUser> roleUsers(Long roleId);

    List<HorsPortalRoleResource> roleResources(Long roleId);

    List<HorsPortalResource> userResources(Long userId);

}
