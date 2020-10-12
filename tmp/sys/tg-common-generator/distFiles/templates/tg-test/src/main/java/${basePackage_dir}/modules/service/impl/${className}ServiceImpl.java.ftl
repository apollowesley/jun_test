package ${basePackage}.modules.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ${basePackage}.modules.domain.${table.className};
import ${basePackage}.modules.domain.form.${table.className}Form;
import ${basePackage}.modules.domain.query.${table.className}Query;
import ${basePackage}.modules.repository.${table.className}Dao;
import ${basePackage}.modules.service.${table.className}Service;

/**
 * [${table.remarks!""}]ServiceImpl 
 */
@Service
@Transactional
public class ${table.className}ServiceImpl implements ${table.className}Service {
    @Autowired 
    private ${table.className}Dao ${table.classNameFirstLower}Dao;
    
    /**
     * 通过id得到一个[${table.remarks!""}]
     */
    @Override 
    public ${table.className} get(final String id) throws TgBusinessException {
        return ${table.classNameFirstLower}Dao.get(id);
    }
    
    /**
     * 新增[${table.remarks!""}]
     */
    @Override 
    public void add(final ${table.className}Form form) throws TgBusinessException {
        form.setCreateDataUsername(TgSystemHelper.getCurrentUsername());
        ${table.classNameFirstLower}Dao.insert(form);
    }
    
    /**
     * 根据id删除一个[${table.remarks!""}]
     */
    @Override 
    public int delete(final String id) throws TgBusinessException {
        return ${table.classNameFirstLower}Dao.delete(id);
    }
}