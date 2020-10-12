package example.abssqr.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import example.abssqr.do.Table1DO;
import java.util.List;
import example.abssqr.mapper.Table1Mapper;

/**
* The Table table1.
* TABLE1
*/
@Repository
public class Table1DAO{

    @Autowired
    private Table1Mapper table1Mapper;

    /**
     * desc:插入表:table1.<br/>
     * @param entity entity
     * @return Long
     */
    public Long insert(Table1DO entity){
        return table1Mapper.insert(entity);
    }
    /**
     * desc:批量插入表:table1.<br/>
     * @param list list
     * @return Long
     */
    public Long insertBatch(List<Table1DO> list){
        return table1Mapper.insertBatch(list);
    }
    /**
     * desc:根据主键删除数据:table1.<br/>
     * @param id id
     * @return Long
     */
    public Long deleteById(Integer id){
        return table1Mapper.deleteById(id);
    }
    /**
     * desc:根据主键获取数据:table1.<br/>
     * @param id id
     * @return Table1DO
     */
    public Table1DO getById(Integer id){
        return table1Mapper.getById(id);
    }
}
