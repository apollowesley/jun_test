package luna.com.firefly.service.system;

import java.util.List;
import java.util.Map;

import luna.com.firefly.entity.system.SystemUser;
import luna.com.firefly.repository.system.SystemUserDao;
import luna.com.firefly.utils.jpa.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <用户信息接口>
 * 
 * @author 陆小凤
 * @version [版本号, 2014年6月15日]
 * 
 */
@Component
@Transactional(value = "transactionManager")
public class SystemUserService
{
    @Autowired
    private SystemUserDao userDao;
    
    /**
     * <查询分页返回的用户列表信息>
     * 
     * @param searchParams 查询参数
     * @param pageNumber 查询起始页
     * @param pageSize 查询页大小
     * @param sortType 排序方式
     * @return
     * 
     */
    public Page<SystemUser> getUsers(Map<String, Object> searchParams, int pageNumber, int pageSize)
    {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize);
        Specification<SystemUser> spec = PageUtil.buildSpecification(searchParams, SystemUser.class);
        return userDao.findAll(spec, pageRequest);
    }
    
    public List<SystemUser> findUser(Map<String, Object> searchParams, Sort sort)
    {
        Specification<SystemUser> spec = PageUtil.buildSpecification(searchParams, SystemUser.class);
        return userDao.findAll(spec, sort);
    }
}
