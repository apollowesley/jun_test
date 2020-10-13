package luna.com.firefly.repository.system;

import java.util.List;

import luna.com.firefly.entity.system.SystemRoleMenu;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemRoleMenuDao extends PagingAndSortingRepository<SystemRoleMenu, Long>,
    JpaSpecificationExecutor<SystemRoleMenu>
{
    /**
     * <根据菜单Id和角色Id删除按钮权限>
     * 
     * @param menuId
     * @param roleId
     * 
     */
    @Modifying
    @Query("delete from SystemRoleMenu u where u.menuId=?1")
    void deleteByMenuId(Long menuId);
    
    List<SystemRoleMenu> findByRoleId(Long roleId);
    
    @Modifying
    @Query("delete from SystemRoleMenu u where u.roleId=?1")
    void deleteByRoleId(Long id);
    
    /**
     * <一句话功能简述>
     * 
     * @param roleIdList
     * @return
     * 
     */
    @Query("select distinct(menuId) from  SystemRoleMenu u where u.roleId in(?1)")
    List<Long> findMenuIdByRoleId(List<Long> roleIdList);
}
