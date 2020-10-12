
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.service.impl;

import ${basepackage}.service.${className}Service;
import ${basepackage}.domain.${className}Mapper;
import ${basepackage}.domain.${className}DO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import cn.winner.beans.base.Pagination;
import cn.winner.core.orika.mapper.OrikaBeanMapper;
import ${basepackage}.service.bo.${className}SaveBO;
import ${basepackage}.service.query.${className}QUERY;
import ${basepackage}.service.bo.${className}RemoveBO;
import ${basepackage}.service.dto.${className}DTO;
import ${basepackage}.service.bo.${className}UpdateBO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
<#include "/java_copyright.include">
<#include "/java_imports.include">

@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Mapper ${classNameLower}Mapper;


    @Autowired
    private OrikaBeanMapper beanMapper;

    /**
     * 获取单个对象
     * @param id    主键
     * @return 结果对象
     */
    @Override
    public ${className}DTO get(Long id){
        return beanMapper.map(${classNameLower}Mapper.get(id),${className}DTO.class);
    }

    /**
     * 查询对象列表
     * @param bean  查询条件对象
     * @return  分页对象
     */
    @Override
    public Pagination<${className}DTO> page(${className}QUERY bean){
        PageHelper.startPage(bean.getPage(), bean.getPageSize());
        Page<${className}DO> ${classNameLower} = (Page<${className}DO>) ${classNameLower}Mapper.list(beanMapper.map(bean, ${className}DO.class));
        Pagination<${className}DTO> result = new Pagination<>();
        result.setData(beanMapper.mapAsList(${classNameLower}, ${className}DTO.class));
        result.setTotal(${classNameLower}.getTotal());
        return result;
    }

        /**
         * 查询列表
         * @param bean  查询条件对象
         * @return   分页对象
         * */
    @Override
    public List<${className}DTO> list(${className}QUERY bean){
            return beanMapper.mapAsList(${classNameLower}Mapper.list(beanMapper.map(bean, ${className}DO.class)),${className}DTO.class);
        }

    /**
     * 保存单个对象,返回主键
     * @param bean  保存对象
     * @return 主键
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Long doSave(${className}SaveBO bean){
        ${className}DO ${classNameLower} = beanMapper.map(bean, ${className}DO.class);
        ${classNameLower}.setGmtCreate(new Date());
        ${classNameLower}Mapper.save(${classNameLower});
        return ${classNameLower}.getId();
    }

    /**
     * 更新单个对象 id必须有
     * @param bean  更新对象
     * @return 更新的记录条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer doUpdate(${className}UpdateBO bean){
        ${className}DO ${classNameLower} = beanMapper.map(bean, ${className}DO.class);
        ${classNameLower}.setGmtModified(new Date());
        return ${classNameLower}Mapper.update(${classNameLower});
    }

    /**
     * 按条件删除对象
     * @param bean  条件对象
     * @return  删除的记录条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer doRemove(${className}RemoveBO bean){
        ${className}DO ${classNameLower} = beanMapper.map(bean, ${className}DO.class);
        return ${classNameLower}Mapper.remove(${classNameLower});
    }

    /**
     * 按主键删除对象
     * @param id    主键
     * @return 删除的记录条数
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer doRemove(Long id){
        return ${classNameLower}Mapper.delete(id);
        }
}
