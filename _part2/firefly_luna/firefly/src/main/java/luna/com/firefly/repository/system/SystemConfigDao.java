package luna.com.firefly.repository.system;

import luna.com.firefly.entity.system.SystemConfig;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemConfigDao extends PagingAndSortingRepository<SystemConfig, Long>,
    JpaSpecificationExecutor<SystemConfig>
{
    /**
     * <根据参数名查询>
     * 
     * @param configName 参数名称
     * @return
     */
    SystemConfig findByConfigName(String configName);
}
