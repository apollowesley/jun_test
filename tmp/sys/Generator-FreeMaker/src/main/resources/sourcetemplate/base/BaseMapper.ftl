    package ${basePackage}.base;

import java.util.List;

/**
 * @Auther: lishun
 * @Date: 2019/4/15 14:14
 * @Description:
 */
public interface BaseMapper<T,PK>  {
    int insert(T vo);
    int insertSelective(T vo);
    int insertList(List<T> voList);
    int delete(PK primaryKey);
    int deleteBatch(List<PK> idList);
    int update(T vo);
    T selectOne(T vo);
    T selectById(PK primaryKey);
    List<T> listAll();
    List<T> listByEntity(T vo);

}
