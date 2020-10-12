package com.jfast.web.admin.service.system;


import com.jfast.common.model.UserSession;
import com.jfast.common.utils.ResultCode;
import com.jfast.common.utils.ObjectUtils;
import com.jfast.web.admin.service.AdminApiService;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/3/22 22:05
 */
@Service
public class SystemMenuService extends AdminApiService {


    public List<Map> getMenuByParent() {
        return findParent(new ArrayList<>(), ResultCode.FAIL);
    }

    public List<Map> getMenuByUser() {
        UserSession userSession = getUserSession();
        List<Map> menuList = userSession.getMenuList();
        if (ObjectUtils.isEmpty(menuList)) {
            Integer userId = userSession.getUserId();
            menuList = sqlSessionTemplate.selectList("system.menu.list.findByUser", userId);
            Map params = new HashMap<>();
            for (Map menu : menuList) {
                Integer parentId = (Integer)menu.get("id");
                params.put("parentId", parentId);
                params.put("adminId", userId);
                List<Map> children = sqlSessionTemplate.selectList("system.menu.list.findByParentIdAndRoleId", params);
                menu.put("children", children);
            }
            userSession.setMenuList(menuList);
        }
        return menuList;
    }

    /**
     * 获取顶级分类菜单
     * @author zengjintao
     * @version 1.0
     * @create 2018/11/8 10:24
     **/
    public List<Map> findParent(List<Map> result, Integer parentId) {
        List<Map> resultList = getChildren(parentId);
        for (Map menuMap : resultList) {
            Integer value = (Integer)menuMap.get("value");
            List<Map> childrenList = getChildren(value);
            if (ObjectUtils.isNotEmpty(childrenList)) {
                menuMap.put("children", childrenList);
            }
            result.add(menuMap);
        }
        return result;
    }

    /**
     * 获取子类菜单
     * @author zengjintao
     * @version 1.0
     * @create 2018/11/7 17:17
     **/
    private List<Map> getChildren(Integer parentId) {
        Map params = new HashMap<>();
        params.put("parentId", parentId);
        List<Map> childrenList =  sqlSessionTemplate.selectList("system.menu.list.findByParentId", params);
        for (Map children : childrenList) {
            Integer value = (Integer)children.get("value");
            List<Map> list = getChildren(value);
            if (ObjectUtils.isNotEmpty(list)) {
                children.put("children", list);
            }
        }
        return childrenList;
    }

    public Map findById(Integer id) {
        Map menuMap = sqlSessionTemplate.selectOne("system.menu.findById", id);
        int parentId = (Integer)menuMap.get("parent_id");
        List<Integer> parentIds = new ArrayList<>();
        parentIds = getParentIds(parentId, parentIds);
        Collections.reverse(parentIds); // 对集合倒序排序
        menuMap.put("parent_id", parentIds);
        return menuMap;
    }

    private List<Integer> getParentIds(int parentId, List<Integer> parentIds) {
        if (parentId != ResultCode.FAIL) {
            Map parentMap = sqlSessionTemplate.selectOne("system.menu.findById", parentId);
            if (ObjectUtils.isNotEmpty(parentMap)) {
                int newParentId = (Integer)parentMap.get("parent_id");
                parentIds.add((Integer)parentMap.get("id"));
                return getParentIds(newParentId, parentIds);
            }
        }
        return parentIds;
    }
}
