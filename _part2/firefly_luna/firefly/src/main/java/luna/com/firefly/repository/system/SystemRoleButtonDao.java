package luna.com.firefly.repository.system;

import java.util.List;

import luna.com.firefly.entity.system.SystemRoleButton;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemRoleButtonDao extends PagingAndSortingRepository<SystemRoleButton, Long>,
    JpaSpecificationExecutor<SystemRoleButton>
{
    /**
     * 根据角色ID查询角色按钮权限关系
     * 
     * @param roleId
     * @return
     * 
     */
    List<SystemRoleButton> findByRoleId(Long roleId);
    
    /**
     * 根据按钮代码查询角色按钮权限关系
     * 
     * @param buttonCode
     * @return
     * 
     */
    List<SystemRoleButton> findByButtonCode(String buttonCode);
    
    /**
     * 删除角色对应的按钮权限
     * 
     * @param roleId
     * 
     */
    @Modifying
    @Query("delete from SystemRoleButton u where u.roleId=?1")
    void deleteByRoleId(Long roleId);
    
    /**
     * 根据按钮代码删除按钮权限
     * 
     * @param buttonCode
     * 
     */
    @Modifying
    @Query("delete from SystemRoleButton u where u.buttonCode=?1")
    void deleteByButtonCode(String buttonCode);
    
    /**
     * 根据按钮代码删除按钮权限
     * 
     * @param buttonCode
     * 
     */
    @Modifying
    @Query("update SystemRoleButton u set u.buttonCode = ?1 where u.roleId = ?2")
    void updateButtonCodeByRoleId(String buttonCode, Long roleId);
}
