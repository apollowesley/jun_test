package ${basePackage}.${servicePackage}.${serviceImplPackage};

import ${basePackage}.${entityPackage}.${entityCamelName};
import ${basePackage}.${servicePackage}.${entityCamelName}Service;
import ${basePackage}.${daoPackage}.${entityCamelName}Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
/**
 * ${remark!}操作相关
 */
public class ${entityCamelName}ServiceImpl implements ${entityCamelName}Service {

	@Autowired
	private ${entityCamelName}Mapper ${entityName}Mapper;
    @Override
    public int insert(${entityCamelName} vo) {
        return ${entityName}Mapper.insertSelective(vo);
    }

    @Override
    public int insertList(List<${entityCamelName}> voList) {
        return ${entityName}Mapper.insertList(voList);
    }

    @Override
    public int delete(${primaryPropertyType!} id) {
        return ${entityName}Mapper.delete(id);
    }

    @Override
    public int deleteBatch(List<${primaryPropertyType!}> idList) {
        return ${entityName}Mapper.deleteBatch(idList);
    }

    @Override
    public List<${entityCamelName}> getList(${entityCamelName} vo) {
        return ${entityName}Mapper.listByEntity(vo);
    }

    @Override
    public int update(${entityCamelName} vo) {
        return ${entityName}Mapper.update(vo);
    }

}
