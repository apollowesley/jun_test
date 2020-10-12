package ${basePackage}.modules.repository;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ${basePackage}.modules.domain.${table.className};
import ${basePackage}.modules.domain.form.${table.className}Form;
import ${basePackage}.modules.domain.query.${table.className}Query;

/**
 * [${table.remarks!""}]Dao 
 */
public interface ${table.className}Dao {
    /**
     * 通过id得到一个[${table.remarks!""}]
     */
    ${table.className} get(final String id);
    
    /**
     * 插入数据[${table.remarks!""}]
     */
    void insert(final ${table.className}Form form);
    
    /**
     * 根据id删除一个[${table.remarks!""}]
     */
    int delete(final String id);
}