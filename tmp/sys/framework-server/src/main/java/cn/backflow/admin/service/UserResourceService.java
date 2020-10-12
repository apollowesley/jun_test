package cn.backflow.admin.service;

import cn.backflow.admin.dao.UserResourceDao;
import cn.backflow.admin.entity.User;
import cn.backflow.admin.entity.UserResource;
import cn.backflow.admin.service.base.BaseService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserResourceService extends BaseService<UserResource, Integer> {

    @Autowired
    private UserResourceDao userResourceDao;


    public List<Map<String, Object>> findByResourceTypeAndResourceId(String resourceType, Integer resourceId) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("resourceType", resourceType);
        parameter.put("resourceId", resourceId);
        return userResourceDao.findByParameter(parameter);
    }

    /**
     * 类名转表名
     *
     * @param clazz  java类
     * @param prefix 表前缀
     * @return 表名
     */
    public static String classToTable(Class<?> clazz, String prefix) {
        if (prefix == null)
            prefix = "";
        return (prefix + StringUtils.join(
                StringUtils.splitByCharacterTypeCamelCase(clazz.getSimpleName()), "_")
        ).toLowerCase();
    }

    /**
     * 资源分配
     *
     * @param o        资源对象
     * @param targets  分配目标
     * @param assigner 分配者
     */
    @Transactional
    public int assign(Object o, List<Integer> targets, User assigner) throws Exception {
        Integer resource_id = (Integer) MethodUtils.invokeMethod(o, "getId"); // 资源ID
        String resource_type = o.getClass().getSimpleName();

        deleteByResourceTypeAndResourceId(resource_type, resource_id);
        if (targets.isEmpty()) {
            return 0;
        }

        List<UserResource> urs = new ArrayList<>();
        for (Integer user : targets) {
            UserResource ur = new UserResource();
            ur.setUserId(user);
            ur.setResourceType(resource_type);
            ur.setResourceId(resource_id);
            urs.add(ur);
        }
        return userResourceDao.insertBatch(urs);
    }

    @Transactional
    public int deleteByResourceTypeAndResourceId(String resourceType, Integer resourceId) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("resourceType", resourceType);
        parameter.put("resourceId", resourceId);
        return deleteByParameter(parameter);
    }

    @Transactional
    public int deleteByParameter(Object parameter) throws DataAccessException {
        return userResourceDao.deleteByParameter(parameter);
    }

}