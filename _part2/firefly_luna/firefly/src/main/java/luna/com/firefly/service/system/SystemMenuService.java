package luna.com.firefly.service.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import luna.com.firefly.entity.system.SystemMenu;
import luna.com.firefly.repository.system.SystemMenuDao;
import luna.com.firefly.repository.system.SystemRoleMenuDao;
import luna.com.firefly.repository.system.SystemUserRoleDao;
import luna.com.firefly.utils.jpa.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * <功能详细描述>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年12月9日]
 */
@Component
@Transactional(value = "transactionManager")
public class SystemMenuService
{
    
    @Autowired
    private SystemMenuDao menuDao;
    
    @Autowired
    private SystemRoleMenuDao roleMenuRelDao;
    
    @Autowired
    private SystemUserRoleDao userRoleRelDao;
    
    /**
     * <根据 parentId获得menu列表>
     * 
     * @param parentId
     * @return
     */
    public List<SystemMenu> getMenuByParentId(Long parentId)
    {
        List<SystemMenu> list = menuDao.findByParentIdOrderBySortAsc(parentId);
        for (SystemMenu menu : list)
        {
            if (menu != null)
            {
                List<SystemMenu> childMenus = getMenuByParentId(menu.getId());
                menu.setChildMenus(childMenus);
            }
        }
        return list;
    }
    
    /**
     * <保存menu>
     * 
     * @param menu
     */
    public void saveMenu(SystemMenu menu)
    {
        menuDao.save(menu);
    }
    
    /**
     * <更新菜单信息>
     * 
     * @param menu
     */
    public void updateMenu(Long parentId, Integer sort)
    {
        menuDao.updateSort(parentId, sort);
    }
    
    /**
     * <根据parentId获得最大的sortnum>
     * 
     * @param parentId
     * @return
     */
    public Integer getMaxSortNumByParentId(Long parentId)
    {
        return menuDao.getMaxSortNumByParentId(parentId);
    }
    
    /**
     * <获得菜单管理信息>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<SystemMenu> getMenuList(Map<String, Object> searchParams, int pageNumber, int pageSize)
    {
        String parentId = "parentId";
        String sortnum = "sort";
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, parentId, sortnum);
        Specification<SystemMenu> spec = PageUtil.buildSpecification(searchParams, SystemMenu.class);
        
        return menuDao.findAll(spec, pageRequest);
    }
    
    /**
     * <获取菜单列表>
     * 
     * @return
     */
    public List<SystemMenu> getAllMenuList()
    {
        return (List<SystemMenu>)menuDao.findAll();
    }
    
    /**
     * <根据menuId查询>
     * 
     * @param id
     * @return
     */
    public SystemMenu getMenuById(Long id)
    {
        return menuDao.findOne(id);
    }
    
    /**
     * <删除 menu>
     * 
     * @param valueOf
     */
    public void deleteMenuById(Long menuId)
    {
        roleMenuRelDao.deleteByMenuId(menuId);
        menuDao.delete(menuId);
    }
    
    /**
     * 根据userId获取菜单列表 当前菜单较少，采取一次查询后封装 后续菜单增加后，可多次查询DB再封装
     * 
     * @param userId
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<SystemMenu> getMenuByUserId(Long userId)
    {
        List<SystemMenu> retMenus = Lists.newArrayList();
        List<Long> roleIdList = new ArrayList<Long>();
        roleIdList = userRoleRelDao.findRoleIdByUserId(userId);
        List<Long> menuIdList = roleMenuRelDao.findMenuIdByRoleId(roleIdList);
        if (!CollectionUtils.isEmpty(menuIdList))
        {
            List<SystemMenu> menus = menuDao.findByMenuIds(menuIdList);
            if (!CollectionUtils.isEmpty(menus))
            {
                Map<Long, SystemMenu> parentMenus = Maps.newHashMap();
                
                // 封装主menu
                for (SystemMenu menu : menus)
                {
                    if (menu != null && menu.getParentId() != null && menu.getParentId().longValue() == 0L)
                    {
                        parentMenus.put(menu.getId(), menu);
                    }
                }
                
                // 封装子menu列表到主menu
                SystemMenu parentMenu = null;
                List<SystemMenu> childMenus = null;
                for (SystemMenu menu : menus)
                {
                    if (menu != null && menu.getParentId() != null)
                    {
                        parentMenu = parentMenus.get(menu.getParentId());
                        if (parentMenu != null)
                        {
                            childMenus = parentMenu.getChildMenus();
                            if (null == childMenus)
                            {
                                childMenus = Lists.newArrayList();
                            }
                            childMenus.add(menu);
                            
                            parentMenu.setChildMenus(childMenus);
                        }
                    }
                }
                
                retMenus = Lists.newArrayList(parentMenus.values());
                
                // 对子menu进行排序
                for (SystemMenu SystemMenu : retMenus)
                {
                    Collections.sort(SystemMenu.getChildMenus(), new Comparator<SystemMenu>()
                    {
                        @Override
                        public int compare(SystemMenu o1, SystemMenu o2)
                        {
                            return o1.getSort() - o2.getSort();
                        }
                    });
                }
                
                // 对主menu进行排序
                Collections.sort(retMenus, new Comparator<SystemMenu>()
                {
                    @Override
                    public int compare(SystemMenu o1, SystemMenu o2)
                    {
                        return o1.getSort() - o2.getSort();
                    }
                });
            }
        }
        return retMenus;
    }
    
}
