package luna.com.firefly.repository.system;

import java.util.List;

import luna.com.firefly.entity.system.SystemButton;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemButtonDao extends PagingAndSortingRepository<SystemButton, String>,
    JpaSpecificationExecutor<SystemButton>
{
    /**
     * <根据菜单ID获取菜单下的功能权限> <功能详细描述>
     * 
     * @param parentId
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<SystemButton> findByMenuId(Long menuId);
    
    /**
     * <根据SystemButtonCode获取SystemButton信息> <功能详细描述>
     * 
     * @param parentId
     * @return
     * @see [类、类#方法、类#成员]
     */
    SystemButton findByButtonCode(String ButtonCode);
    
    /**
     * <根据SystemButton code删除>
     * 
     * @param SystemButtonCode
     */
    @Modifying
    @Query("delete from SystemButton where buttonCode=?1")
    void deleteByButtonCode(String buttonCode);
}
