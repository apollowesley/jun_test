package ${pakageName}.dao.common;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* 使用统一的接口定义 利于编码习惯统一
* 因为使用autoscan,需要每个mapper.xml都有方法对应的声明
*/
public interface IBaseDAO<T> {

    /**
    * 保存单一对象
    *
    * @param entity
    */
    public int insert(T entity);

    /**
    * 更新对象,如果属性为空，则不会进行对应的属性值更新,如果有属性要更新为null，请参看{@link #updateNull(T)}
    *
    * @param entity 要更新的实体对象
    */
    public int update(T entity);

    public T queryOneById(@Param("id") String id);

    /**
    * 根据条件集合进行指定类型对象集合查询
    *
    * @param condition 进行查询的条件集合
    * @return 返回泛型参数类型的对象集合，如何取到泛型类型参数，请参看{@link #getEntityClass()}
    */
    public List<T> queryList(@Param("condition") Map<String, Object> condition, @Param("orderBy") String orderBy, @Param("sortBy") String sortBy);

    }