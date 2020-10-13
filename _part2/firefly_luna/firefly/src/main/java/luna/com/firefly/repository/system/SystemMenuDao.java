package luna.com.firefly.repository.system;

import java.util.List;

import luna.com.firefly.entity.system.SystemMenu;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemMenuDao extends PagingAndSortingRepository<SystemMenu, Long>,
    JpaSpecificationExecutor<SystemMenu>
{
    /**
     * 根据父id获取menu
     * 
     * @param parentId
     * @return
     */
    List<SystemMenu> findByParentId(Long parentId);
    
    /**
     * 根据id获取menu
     * 
     * @param id
     * @return
     */
    List<SystemMenu> findById(Long id);
    
    /**
     * <根据父id获取menu>
     * 
     * @param parentId
     * @return
     */
    List<SystemMenu> findByParentIdOrderBySortAsc(Long parentId);
    
    /**
     * <更新菜单排序>
     * 
     * @param parentId
     * @param sort
     */
    @Modifying
    @Query("update SystemMenu set sort=sort-1  where parentId =?1 and sort>?2")
    void updateSort(Long parentId, Integer sort);
    
    /**
     * <根据父菜单 查询 最大排序号>
     * 
     * @param parentId
     * @return
     */
    @Query("select MAX(sort) from SystemMenu where parentId =?1")
    Integer getMaxSortNumByParentId(Long parentId);
    
    /**
     * <查询菜单>
     * 
     * @param menuIdList
     * @return
     */
    @Query("select s from SystemMenu s where s.id in (?1)")
    List<SystemMenu> findByMenuIds(List<Long> menuIdList);
    
}
