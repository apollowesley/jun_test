package luna.com.firefly.service.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import luna.com.firefly.entity.system.SystemButton;
import luna.com.firefly.entity.system.SystemRoleButton;
import luna.com.firefly.entity.system.SystemUserRole;
import luna.com.firefly.repository.system.SystemButtonDao;
import luna.com.firefly.repository.system.SystemRoleButtonDao;
import luna.com.firefly.repository.system.SystemUserRoleDao;
import luna.com.firefly.utils.cache.CacheEvict;
import luna.com.firefly.utils.cache.Cacheable;
import luna.com.firefly.utils.jpa.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 按钮服务
 * 
 * @author 陆小凤
 * @version [版本号, 2014年5月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Component
@Transactional(value = "transactionManager")
public class SystemButtonService
{
    
    @Autowired
    private SystemRoleButtonDao systemRoleButtonDao;
    
    @Autowired
    private SystemButtonDao systemButtonDao;
    
    @Autowired
    private SystemUserRoleDao systemUserRoleDao;
    
    private static final String CACHE_KEY = "luna.com.firefly.service.system.SystemButtonService";
    
    /**
     * 根据用户ID查询该用户下的所有有权限的的按钮代码 <功能详细描述>
     * 
     * @param userId
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<String> findButtonCodesByUserId(Long userId)
    {
        List<String> buttonList = new ArrayList<String>();
        List<SystemUserRole> userRoleRels = systemUserRoleDao.findByUserId(userId);
        for (SystemUserRole SystemUserRole : userRoleRels)
        {
            buttonList.addAll(this.findRoleButtonCodesByRoleId(SystemUserRole.getRoleId()));
        }
        return buttonList;
    }
    
    /**
     * 根据角色ID查询该角色下的所有有权限的按钮代码 <功能详细描述>
     * 
     * @param roleId
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<String> findRoleButtonCodesByRoleId(Long roleId)
    {
        List<String> buttonCode = new ArrayList<String>();
        List<SystemRoleButton> roleButtonList = systemRoleButtonDao.findByRoleId(roleId);
        for (SystemRoleButton SystemRoleButton : roleButtonList)
        {
            buttonCode.add(SystemRoleButton.getButtonCode());
        }
        return buttonCode;
    }
    
    /**
     * 获取按钮分页列表 <功能详细描述>
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param direction
     * @param orderBy
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Page<SystemButton> getButtonList(Map<String, Object> searchParams, int pageNumber, int pageSize,
        String direction, String orderBy)
    {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        Specification<SystemButton> spec = PageUtil.buildSpecification(searchParams, SystemButton.class);
        return systemButtonDao.findAll(spec, pageRequest);
    }
    
    /**
     * 根据按钮代码查询按钮信息 <功能详细描述>
     * 
     * @param buttonCode
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Cacheable(key = CACHE_KEY)
    public SystemButton findByButtonCode(String buttonCode)
    {
        return systemButtonDao.findByButtonCode(buttonCode);
    }
    
    /**
     * 保存按钮信息
     * 
     * @param buttonLatest
     * @param buttonOld
     * @see [类、类#方法、类#成员]
     */
    @CacheEvict(key = CACHE_KEY)
    public void saveButton(SystemButton button)
    {
        systemButtonDao.save(button);
    }
    
    /**
     * 删除按钮信息以及其与角色的关系 <功能详细描述>
     * 
     * @param buttonCode
     * @see [类、类#方法、类#成员]
     */
    @CacheEvict(key = CACHE_KEY)
    public void deleteButtonByButtonCode(String buttonCode)
    {
        systemRoleButtonDao.deleteByButtonCode(buttonCode);
        systemButtonDao.deleteByButtonCode(buttonCode);
    }
}
