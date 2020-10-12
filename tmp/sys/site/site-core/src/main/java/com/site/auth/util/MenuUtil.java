package com.site.auth.util;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;

import com.site.auth.dto.PermissionDto;
import com.site.auth.model.Permission;

/**
 * 菜单工具类
 * 
 * @author Vincent.wang
 *
 */
public class MenuUtil implements Serializable {

    private static final long serialVersionUID = -2205059367852213007L;

    public static List<PermissionDto> getMenus(List<Permission> permissions) {
        if (permissions == null || permissions.size() == 0) {
            return null;
        }
        List<PermissionDto> pers = new ArrayList<PermissionDto>();
        try {
            PermissionDto pvo = null, dto = null;

            // 一级菜单
            Map<String, PermissionDto> one = new LinkedHashMap<String, PermissionDto>();
            for (Permission per : permissions) {
                if (per != null && per.getLev() != null && per.getLev() == 1) {
                    pvo = new PermissionDto();
                    PropertyUtils.copyProperties(pvo, per);
                    one.put(per.getId(), pvo);
                }
            }
            // 二级菜单
            Map<String, PermissionDto> two = new LinkedHashMap<String, PermissionDto>();
            for (Permission per : permissions) {
                if (per != null && per.getLev() != null && per.getLev() == 2) {
                    pvo = new PermissionDto();
                    PropertyUtils.copyProperties(pvo, per);
                    two.put(per.getId(), pvo);
                }
            }

            // 三级菜单绑到二级菜单上
            for (Permission p : permissions) {
                if (p != null && p.getLev() != null && p.getLev() == 3 && two.containsKey(p.getParentId())) {
                    pvo = new PermissionDto();
                    PropertyUtils.copyProperties(pvo, p);

                    dto = two.get(p.getParentId());
                    dto.getChildren().add(pvo);
                    two.put(dto.getId(), dto);
                }
            }

            // 二级菜单绑到一级菜单上
            for (Entry<String, PermissionDto> entry : two.entrySet()) {
                pvo = entry.getValue();
                if (one.containsKey(pvo.getParentId())) {
                    dto = one.get(pvo.getParentId());
                    dto.getChildren().add(pvo);
                    one.put(dto.getId(), dto);
                }
            }
            pers.addAll(one.values());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return pers;
    }

}
