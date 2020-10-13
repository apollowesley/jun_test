package luna.com.firefly.service.system;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <数据字典信息>
 * 
 * @author 陆小凤
 * @version [版本号, 2015年7月16日]
 * 
 */
@Service
public class DictionaryService
{
    
    @Autowired
    private SystemDicService systemDicService;
    
    @Autowired
    private Cache cache;
    
    /**
     * 
     * <初始化数据字典><br />
     * <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    public void initDictionary()
    {
        cache.put(new Element("SystemDic", systemDicService.getSystemDicKeyValue()));
    }
    
    /**
     * 
     * <根据对象名刷新缓存对象><br />
     * <功能详细描述>
     *
     * @param entity
     * @see [类、类#方法、类#成员]
     */
    public void flush(String entity)
    {
        if ("SystemDic".equals(entity))
        {
            cache.put(new Element("SystemDic", systemDicService.getSystemDicKeyValue()));
        }
    }
}
