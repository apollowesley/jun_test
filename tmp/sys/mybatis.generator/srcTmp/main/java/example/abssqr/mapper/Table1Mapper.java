package example.abssqr.mapper;

import example.abssqr.do.Table1DO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 由于需要对分页支持,请直接使用对应的DAO类
 * The Table table1.
 * TABLE1
 */
public interface Table1Mapper{

    /**
     * desc:插入表:table1.<br/>
     * @param entity entity
     * @return Long
     */
    Long insert(Table1DO entity);
    /**
     * desc:批量插入表:table1.<br/>
     * @param list list
     * @return Long
     */
    Long insertBatch(List<Table1DO> list);
    /**
     * desc:根据主键删除数据:table1.<br/>
     * @param id id
     * @return Long
     */
    Long deleteById(Integer id);
    /**
     * desc:根据主键获取数据:table1.<br/>
     * @param id id
     * @return Table1DO
     */
    Table1DO getById(Integer id);
}
