package luna.com.firefly.service.system;

import java.util.List;
import java.util.Map;

import luna.com.firefly.entity.system.SystemConfig;
import luna.com.firefly.repository.system.SystemConfigDao;
import luna.com.firefly.utils.jpa.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <功能详细描述>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年12月9日]
 */
@Component
@Transactional(value = "transactionManager")
public class SystemConfigService
{
    @Autowired
    private SystemConfigDao systemConfigDao;
    
    public List<SystemConfig> queryAllSystemConfig()
    {
        return (List<SystemConfig>)systemConfigDao.findAll();
    }
    
    /**
     * <根据ID删除配置信息>
     * 
     * @param valueOf
     */
    public void deleteConfigById(Long id)
    {
        systemConfigDao.delete(id);
    }
    
    /**
     * <分页查询配置信息>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<SystemConfig> getSystemConfigList(Map<String, Object> searchParams, int pageNumber, int pageSize)
    {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize);
        Specification<SystemConfig> spec = PageUtil.buildSpecification(searchParams, SystemConfig.class);
        return systemConfigDao.findAll(spec, pageRequest);
    }
    
    /**
     * <查询配置>
     * 
     * @param id
     * @return
     */
    public SystemConfig getSystemConfigById(Long id)
    {
        return systemConfigDao.findOne(id);
    }
    
    /**
     * <保存>
     * 
     * @param config
     */
    public void saveSystemConfig(SystemConfig config)
    {
        systemConfigDao.save(config);
    }
    
    /**
     * <一句话功能简述>
     * 
     * @param configName
     * @return
     */
    public SystemConfig getSystemConfigByName(String configName)
    {
        return systemConfigDao.findByConfigName(configName);
    }
    
    public String getConfigValueByName(String configName)
    {
        SystemConfig sc = getSystemConfigByName(configName);
        if (null != sc)
        {
            return sc.getConfigValue();
        }
        else
        {
            return null;
        }
    }
}