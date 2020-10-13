package luna.com.firefly.repository.system;

import luna.com.firefly.entity.system.SystemUser;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemUserDao extends PagingAndSortingRepository<SystemUser, Long>,
    JpaSpecificationExecutor<SystemUser>
{
    
}
