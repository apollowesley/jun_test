package luna.com.firefly.repository.system;

import luna.com.firefly.entity.system.SystemLog;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemLogDao extends PagingAndSortingRepository<SystemLog, Long>, JpaSpecificationExecutor<SystemLog>
{
    
}
