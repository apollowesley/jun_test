package ${basePackage}.base;

import java.util.List;

/**
 * @Auther: lishun
 * @Date: 2019/4/15 14:23
 * @Description:
 */
public interface BaseService<T,PK> {
    int insert(T vo);
    int insertList(List<T> voList);
    int delete(PK id);
    int deleteBatch(List<PK> idList);
    List<T> getList(T vo);
    int update(T vo);
}
