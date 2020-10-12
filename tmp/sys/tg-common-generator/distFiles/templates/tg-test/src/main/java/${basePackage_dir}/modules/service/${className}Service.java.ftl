package ${basePackage}.modules.service;

import java.util.List;
import ${basePackage}.modules.domain.${table.className};
import ${basePackage}.modules.domain.form.${table.className}Form;
import ${basePackage}.modules.domain.query.${table.className}Query;

/**
 * [${table.remarks!""}]Service
 */
public interface ${table.className}Service {
    /**
     * 通过id得到一个[${table.remarks!""}]
     */
    ${table.className} get(final String id) throws TgBusinessException;

    /**
     * 新增[${table.remarks!""}]
     */
    void add(final ${table.className}Form form) throws TgBusinessException;

    /**
     * 根据id删除一个[${table.remarks!""}]
     */
    int delete(final String id) throws TgBusinessException;
}