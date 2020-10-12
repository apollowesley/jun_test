package ${mapper.packageName};
<#if mapper.imports??>
<#list mapper.imports as imp>
import ${imp};
</#list>
</#if>
public interface ${mapper.className} {
    /**
     * 通过id物理删除${tableName}的数据.
     */
    int deleteById(Integer id);

    /**
     * 向表${tableName}中插入数据.
     */
    int insert(${mapper.entityModel.className} ${(mapper.entityModel.className)?uncap_first});

    /**
     * 通过id查询表${tableName}.
     */
    ${mapper.entityModel.className} selectById(Integer id);

    /**
     * 通过id修改表${tableName}.
     */
    int updateById(${mapper.entityModel.className} ${(mapper.entityModel.className)?uncap_first});

}