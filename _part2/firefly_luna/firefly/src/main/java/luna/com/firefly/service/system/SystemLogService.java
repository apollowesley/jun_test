package luna.com.firefly.service.system;

import java.util.List;
import java.util.Map;

import luna.com.firefly.entity.system.SystemLog;
import luna.com.firefly.repository.system.SystemLogDao;
import luna.com.firefly.utils.jpa.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <系统日志>
 * 
 * @author 陆小凤
 * @version [1.0, 2015年12月9日]
 */
@Component
@Transactional(value = "transactionManager")
public class SystemLogService
{
    @Autowired
    private SystemLogDao proLogDao;
    
    public Page<SystemLog> queryProLogList(Map<String, Object> searchParams, int pageNumber, int pageSize)
    {
        String[] orderBy = {"proLogId"};
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, "desc", orderBy);
        Specification<SystemLog> spec = PageUtil.buildSpecification(searchParams, SystemLog.class);
        return proLogDao.findAll(spec, pageRequest);
    }
    
    public SystemLog queryProLogDetail(Long logId)
    {
        return proLogDao.findOne(logId);
    }
    
    public void deleteByIds(List<Long> ids)
    {
        Iterable<SystemLog> proLogs = proLogDao.findAll(ids);
        proLogDao.delete(proLogs);
    }
    
    public void save(SystemLog systemLog)
    {
        proLogDao.save(systemLog);
    }
}
