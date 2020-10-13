package luna.com.firefly.service.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import luna.com.firefly.entity.system.SystemRole;
import luna.com.firefly.entity.system.SystemRoleButton;
import luna.com.firefly.entity.system.SystemRoleMenu;
import luna.com.firefly.entity.system.SystemUserRole;
import luna.com.firefly.repository.system.SystemRoleButtonDao;
import luna.com.firefly.repository.system.SystemRoleDao;
import luna.com.firefly.repository.system.SystemRoleMenuDao;
import luna.com.firefly.repository.system.SystemUserRoleDao;
import luna.com.firefly.service.system.ShiroDbRealm.ShiroUser;
import luna.com.firefly.utils.jpa.PageUtil;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

 

/**
 * <角色管理接口> <处理角色信息接口>
 * 
 * @author 陆小凤
 * @version [1.0, 2013-11-21]
 */
@Component
@Transactional(value = "transactionManager")
public class SystemRoleService
{
    
    @Autowired
    private SystemRoleDao roleDao;
    
    @Autowired
    private SystemRoleMenuDao roleMenuDao;
    
    @Autowired
    private SystemRoleButtonDao roleButtonRelDao;
    
    @Autowired
    private SystemUserRoleDao userRoleDao;
    
    public static final String BUTTON_PREFIX = "button_";
    
    /**
     * <保存角色信息>
     * 
     * @param entity
     */
    public void saveRole(SystemRole entity)
    {
        roleDao.save(entity);
    }
    
    /**
     * <更新角色和菜单关系信息>
     * 
     * @param menuId
     * @param roleId
     */
    public void updateRoleMenuRel(String menuId, String roleId)
    {
        Long id = Long.valueOf(roleId);
        roleMenuDao.deleteByRoleId(id);
        SystemRoleButton rbr = null;
        List<SystemRoleButton> rbrList = new ArrayList<SystemRoleButton>();
        List<SystemRoleMenu> rmrList = new ArrayList<SystemRoleMenu>();
        SystemRoleMenu rmr = null;
        ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
        
        if (!"".equals(menuId))
        {
            String[] menuIds = menuId.split(",");
            roleButtonRelDao.deleteByRoleId(Long.parseLong(roleId));
            for (String ids : menuIds)
            {
                if (ids.startsWith(BUTTON_PREFIX))
                {
                    
                    String buttonCode = ids.substring(ids.indexOf(BUTTON_PREFIX) + BUTTON_PREFIX.length());
                    rbr = new SystemRoleButton();
                    rbr.setButtonCode(buttonCode);
                    rbr.setCreateTime(new Date());
                    rbr.setRoleId(Long.parseLong(roleId));
                    rbr.setCreateUser(user.name);
                    rbrList.add(rbr);
                }
                else
                {
                    rmr = new SystemRoleMenu();
                    rmr.setCreateTime(new Date());
                    rmr.setCreateUser(user.name);
                    rmr.setMenuId(Long.parseLong(ids));
                    rmr.setRoleId(id);
                    rmrList.add(rmr);
                }
            }
            roleButtonRelDao.save(rbrList);
            roleMenuDao.save(rmrList);
        }
    }
    
    /**
     * <更新角色和用户关系信息>
     * 
     * @param userId
     * @param roleId
     */
    public void InsertUserRoleRel(String userId, String roleId)
    {
        Long rId = Long.valueOf(roleId);
        Long uId = Long.valueOf(userId);
        Long count = 0L;
        ShiroUser user = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
        if (1 > count)
        {
            SystemUserRole urr = new SystemUserRole();
            urr.setRoleId(rId);
            urr.setUserId(uId);
            urr.setCreateTime(new Date());
            urr.setCreateUser(user.name);
            userRoleDao.save(urr);
        }
    }
    
    /**
     * <删除角色和用户关系信息>
     * 
     * @param userId
     * @param roleId
     */
    public void DelUserRoleRel(String userId, String roleId)
    {
        Long rId = Long.valueOf(roleId);
        Long uId = Long.valueOf(userId);
        userRoleDao.deleteByUserRoleId(uId, rId);
        
    }
    
    /**
     * <根据roleId 获取角色和菜单的关系信息>
     * 
     * @param roleId
     * @return
     */
    public List<SystemRoleMenu> getRoleMenuRel(Long roleId)
    {
        return roleMenuDao.findByRoleId(roleId);
    }
    
    /**
     * <根据userId 获取用户和角色的关系信息>
     * 
     * @param roleId
     * @return
     */
    public List<SystemUserRole> getUserRoleRel(Long userId)
    {
        List<SystemUserRole> rel = new ArrayList<SystemUserRole>();
        rel = userRoleDao.findByUserId(userId);
        return rel;
    }
    
    /**
     * <根据id获得角色信息>
     * 
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    public SystemRole getRole(Long id)
    {
        return roleDao.findOne(id);
    }
    
    /**
     * <获得所有角色信息>
     * 
     * @return
     */
    public List<SystemRole> getAllRole()
    {
        return roleDao.findAll();
    }
    
    /**
     * <删除角色信息>
     * 
     * @param id
     */
    public void deleteRole(Long id)
    {
        roleDao.delete(id);
    }
    
    /**
     * <获得角色信息列表>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<SystemRole> getRoleList(Map<String, Object> searchParams, int pageNumber, int pageSize)
    {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize);
        Specification<SystemRole> spec = PageUtil.buildSpecification(searchParams, SystemRole.class);
        return roleDao.findAll(spec, pageRequest);
    }
    
    /**
     * <获得用户没有绑定的角色信息列表>
     * 
     * @param roleId
     * @return
     */
    public List<SystemRole> getRoleLists(List<Long> roleId)
    {
        return roleDao.findByIdNotIn(roleId);
        
    }
    
    /**
     * <根据角色名查询>
     * 
     * @param roleName
     * @return
     */
    public SystemRole findRoleByName(String roleName)
    {
        return roleDao.findByName(roleName);
    }
    
    /**
     * <一句话功能简述>
     * 
     * @param roleName
     * @param roleId
     * @return
     */
    public SystemRole findRoleByNameAndId(String roleName, Long roleId)
    {
        return roleDao.findByIdAndName(roleId, roleName);
    }
    
    /**
     * <一句话功能简述>
     * 
     * @param roleId
     * @return
     */
    public SystemRole findOne(Long roleId)
    {
        return roleDao.findOne(roleId);
    }
    
    public List<String> findRoleButtonCodesByRoleId(Long roleId)
    {
        List<String> buttonCode = new ArrayList<String>();
        List<SystemRoleButton> roleButtonList = roleButtonRelDao.findByRoleId(roleId);
        for (SystemRoleButton SystemRoleButton : roleButtonList)
        {
            buttonCode.add(SystemRoleButton.getButtonCode());
        }
        return buttonCode;
    }
}
