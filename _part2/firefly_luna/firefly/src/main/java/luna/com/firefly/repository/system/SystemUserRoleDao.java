package luna.com.firefly.repository.system;

import java.util.List;

import luna.com.firefly.entity.system.SystemUserRole;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemUserRoleDao extends PagingAndSortingRepository<SystemUserRole, Long>,
    JpaSpecificationExecutor<SystemUserRole>
{
    
    /**
     * <根据userId 查询 用户角色关系>
     * 
     * @param userId
     * @return
     * 
     */
    @Query(" select r from SystemUserRole r where r.userId=?1 ")
    List<SystemUserRole> findByUserId(Long userId);
    
    /**
     * <根据userId + roleId 删除 用户角色关系>
     * 
     * @param uId
     * @param rId
     * 
     */
    @Modifying
    @Query("delete from SystemUserRole where userId=?1 and roleId=?2 ")
    void deleteByUserRoleId(Long uId, Long rId);
    
    /**
     * <根据 userId 和 roleId查询 是否存在 用户角色关系>
     * 
     * @param uId
     * @param rId
     * @return
     */
    @Query(" select COUNT(r) from SystemUserRole r where r.userId=?1  and r.roleId=?2")
    Long getCountByUserRoleId(Long uId, Long rId);
    
    /**
     * <根据用户id 查询 角色id>
     * 
     * @param userId
     * @return
     */
    @Query(" select roleId from SystemUserRole r where r.userId=?1 ")
    List<Long> findRoleIdByUserId(Long userId);
    
}
