package com.slavic.hors.admin.api.controller.portal;

import com.slavic.hors.admin.api.dto.portal.resource.request.ListRequest;
import com.slavic.hors.orm.entity.HorsPortalResource;
import com.slavic.hors.orm.entity.HorsPortalRoleResource;
import com.slavic.hors.service.PortalResourceService;
import com.slavic.hors.service.PortalRoleService;
import com.slavic.veles.base.response.ResponseEntity;
import com.slavic.veles.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "portal/resource",method = RequestMethod.POST, produces = "application/json; charset=utf-8")
public class ResourceController {

    @Autowired
    private PortalResourceService portalResourceService;

    @Autowired
    private PortalRoleService portalRoleService;

    @RequestMapping(value = "list")
    public ResponseEntity<Map<String,Object>> list(ListRequest request) {

        List<HorsPortalResource> list = portalResourceService.list(BeanUtil.copyObject(request, new HorsPortalResource()));
        List<HorsPortalResource> roots = list.stream().filter(i -> null == i.getParentId()).collect(Collectors.toList());

        for (HorsPortalResource root : roots) {
            list(root,list);
        }
        roots = roots.subList((request.getCurrentPage() - 1) * request.getPageSize(),
                Math.min(request.getCurrentPage() * request.getPageSize(), roots.size()));
        Map<String,Object> result = new HashMap<>();
        result.put("list",roots);
        result.put("total",roots.size());
        Optional.ofNullable(request.getRoleId()).ifPresent(roleId -> {
            List<HorsPortalRoleResource> roleResources = portalRoleService.roleResources(roleId);
            Optional.ofNullable(roleResources)
                    .ifPresent(i->
                            result.put("checked",i
                                    .stream()
                                    .map(HorsPortalRoleResource::getResourceId)
                                    .collect(Collectors.toList())
                    )
            );
        });

        return ResponseEntity.SUCCESS(result);
    }

    private  void list(HorsPortalResource parent,List<HorsPortalResource> all) {
        List<HorsPortalResource> childs = all.stream().filter(i ->  null != i.getParentId() && i.getParentId().equals(parent.getId())).collect(Collectors.toList());
        parent.setChildren(childs);
        if (!CollectionUtils.isEmpty(childs)) {
            for (HorsPortalResource child : childs) {
                list(child,all);
            }
        }
    }

}
