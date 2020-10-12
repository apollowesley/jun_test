
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>

package ${basepackage}.service;
import java.util.List;
import ${basepackage}.service.bo.${className}SaveBO;
import ${basepackage}.service.query.${className}QUERY;
import ${basepackage}.service.bo.${className}RemoveBO;
import ${basepackage}.service.dto.${className}DTO;
import ${basepackage}.service.bo.${className}UpdateBO;
import cn.winner.beans.base.Pagination;
<#include "/java_imports.include">
public interface ${className}Service {

    /**
     * 获取单个对象
     * @param id    主键
     * @return 结果对象
     */
    ${className}DTO get(Long id);

    /**
     * 查询对象列表
     * @param bean  查询条件对象
     * @return  分页对象
     */
    Pagination<${className}DTO> page(${className}QUERY bean);

    /**
     * 查询列表
     * @param bean  查询条件对象
     * @return   集合对象
     * */
    List<${className}DTO> list(${className}QUERY bean);

    /**
     * 保存单个对象
     * @param bean  保存对象
     * @return 主键
     */
    Long doSave(${className}SaveBO bean);

    /**
     * 更新单个对象 id必须有
     * @param bean  更新对象
     * @return 更新的记录条数
     */
    Integer doUpdate(${className}UpdateBO bean);

    /**
     * 按条件删除对象
     * @param bean  条件对象
     * @return  删除的记录条数
     */
    Integer doRemove(${className}RemoveBO bean);

    /**
     * 按主键删除对象
     * @param id    主键
     * @return 删除的记录条数
     */
    Integer doRemove(Long id);

}