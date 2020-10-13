package luna.com.firefly.repository.system;

import java.util.List;

import luna.com.firefly.entity.system.SystemDic;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SystemDicDao extends PagingAndSortingRepository<SystemDic, Long>, JpaSpecificationExecutor<SystemDic>
{
    
    /**
     * <根据type 查询dic>
     * 
     * @param type
     * @return
     */
    List<SystemDic> findByType(String type);
    
    /**
     * <根据type和value查询数据字典>
     * 
     * @param type
     * @param value
     * @return
     */
    SystemDic findByTypeAndValue(String type, String value);
    
    /**
     * <查询所有的SystemDic>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Query(" select a from SystemDic a order by sortNum desc ")
    List<SystemDic> findList();
}
