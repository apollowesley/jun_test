
package ${dao.daoPackage};

import ${dao.entityName};
import org.bson.types.ObjectId;
<#if dao.querySimpleModel!=''>
import java.util.List;
import ${dao.queryModel};
</#if>
public interface ${dao.entitySimpleName}Dao {

	void insert(${dao.entitySimpleName} ${dao.entitySimpleName?uncap_first});

	void update(${dao.entitySimpleName} ${dao.entitySimpleName?uncap_first});

	void merge( ${dao.entitySimpleName} ${dao.entitySimpleName?uncap_first}, String... fields);

	void delete(ObjectId id);

	${dao.entitySimpleName} findOne(ObjectId id);
	<#if dao.querySimpleModel != ''>
	
	List<${dao.entitySimpleName}> findAll(${dao.querySimpleModel} ${dao.querySimpleModel?uncap_first});

	long count(${dao.querySimpleModel} ${dao.querySimpleModel?uncap_first});
	</#if>
}