package luna.com.firefly.repository.system;

import java.util.List;

import luna.com.firefly.entity.system.SystemRole;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemRoleDao extends PagingAndSortingRepository<SystemRole, Long>,
    JpaSpecificationExecutor<SystemRole>
{
    /**
     * <查询不在 所选roleid的 其他角色列表>
     * 
     * @param roleId
     * @return
     */
    List<SystemRole> findByIdNotIn(List<Long> roleId);
    
    /**
     * <查询所有角色>
     * 
     * @return
     */
    List<SystemRole> findAll();
    
    /**
     * <一句话功能简述>
     * 
     * @param roleName
     * @return
     */
    SystemRole findByName(String roleName);
    
    /**
     * <一句话功能简述>
     * 
     * @param roleId
     * @param roleName
     * @return
     */
    @Query("select p from SystemRole p where name =?2 and id !=?1")
    SystemRole findByIdAndName(Long roleId, String roleName);
}
