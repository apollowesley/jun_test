package cn.uncode.dal.service.mogo;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import cn.uncode.dal.core.MongoDAL;
import cn.uncode.dal.criteria.QueryCriteria;
import cn.uncode.dal.descriptor.QueryResult;

public abstract class AbstractBaseMongoService<T> implements BaseMongoService<T>{
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AbstractBaseMongoService.class);
	
	public static final String RECORD_TOTAL = "recordTotal";
	public final static String RESULT = "list";
	public final static String PAGE = "page";
	public static final String ANDCOLUMN = "andcolumn";
	public static final String APPEND = "append";
	public final static int MAX_QUERY_NUMBER = 1000;
	public static final int PAGE_SIZE = 15;
	
	@Autowired
	protected MongoDAL mongo3DAL;
	
	private Class<T> clz;
	
    @SuppressWarnings("unchecked")
	private Class<T> getClz() {
        if (clz == null) {
            clz = (Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
        }
        return clz;
    }
    
    /**
     * 保存对象
     *
     * @param t
     */
    public String save(T t) {
        Assert.notNull(t, "Entity is required");
        try {
			return String.valueOf(mongo3DAL.insert(t));
		} catch (Exception e) {
			log.error("insert t to mongo failed.", e);
			return null;
		}
    }
    /**
     * 获取对象
     * @param id
     */
    public T getById(String id){
    	 if (null == id) return null;
         clz = this.getClz();
         QueryResult result = this.mongo3DAL.selectByPrimaryKey(clz, id);
         if (null == result || result.get().isEmpty()) return null;
         return result.as(clz);
    }
    /**
  	 * 根据id更新对象
  	 * @param id 数据id
  	 */
    public void updateById(T t){
    	if (null == t) return;
    	clz = this.getClz();
        this.mongo3DAL.updateByPrimaryKey(t);
    }
    /**
  	 * 根据id删除
  	 * @param t 对象
  	 */
    public void deleteById(T t){
    	if (null == t) return;
    	clz = this.getClz();
        this.mongo3DAL.deleteByPrimaryKey(t);
    }

	/**
	 * 根据queryCriteria删除
	 * @param queryCriteria 自定义条件
     */
    public int deleteByCriteria(QueryCriteria queryCriteria){
    	return this.mongo3DAL.deleteByCriteria(queryCriteria);
	}
    
    
	public void saveMany(List<Object> list){
		if(CollectionUtils.isEmpty(list)) return;
		clz = this.getClz();
		this.mongo3DAL.insertMany(clz.getSimpleName().toLowerCase(), list);
	}
}
