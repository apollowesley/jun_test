package luna.com.firefly.service.system;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import luna.com.firefly.entity.system.SystemDic;
import luna.com.firefly.repository.system.SystemDicDao;
import luna.com.firefly.utils.jpa.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <系统字典业务接口>
 * 
 * @author 陆小凤
 * @version [1.0 2014-1-8]
 * 
 */

@Component
@Transactional(value = "transactionManager")
public class SystemDicService
{
    
    @Autowired
    private SystemDicDao systemDicDao;
    
    /**
     * <根据类型查询字典数据>
     * 
     * @param dicChannelOpenLevel
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<SystemDic> getDicListByType(String type)
    {
        return systemDicDao.findByType(type);
    }
    
    /**
     * <根据type和value查询数据字典>
     * 
     * @param type
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    public SystemDic getDicByTypeAndValue(String type, String value)
    {
        return systemDicDao.findByTypeAndValue(type, value);
    }
    
    /**
     * <查询系统字典表>
     * 
     * @param type
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Page<SystemDic> getSysDicList(Map<String, Object> searchParams, int pageNumber, int pageSize)
    {
        String[] orderBy = {"type", "sortNum"};
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, "asc", orderBy);
        Specification<SystemDic> spec = PageUtil.buildSpecification(searchParams, SystemDic.class);
        return systemDicDao.findAll(spec, pageRequest);
    }
    
    /**
     * <根据id获得系统字典表信息>
     * 
     * @param id
     * @return
     */
    public SystemDic getSystemDic(Long id)
    {
        return systemDicDao.findOne(id);
    }
    
    /**
     * <保存系统字典表信息>
     * 
     * @param entity
     */
    public void saveSysDic(SystemDic entity)
    {
        systemDicDao.save(entity);
    }
    
    /**
     * <删除系统字典表信息>
     * 
     * @param id
     */
    public void deleteSystemDic(Long id)
    {
        systemDicDao.delete(id);
    }
    
    /**
     * 
     * <查询系统字典表信息，字典兑换使用><br />
     * <功能详细描述>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Map<String, String> getSystemDicKeyValue()
    {
        Map<String, String> result = new LinkedHashMap<String, String>();
        List<SystemDic> sdList = (List<SystemDic>)systemDicDao.findList();
        for (SystemDic sd : sdList)
        {
            result.put(sd.getType() + "_" + sd.getValue(), sd.getName());
        }
        return result;
        
    }
}
