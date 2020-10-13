<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
package ${basepackage}.service.module.${modulename};

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.schrodinger.zbdp.dao.page.ZPage;
import com.schrodinger.zbdp.service.framework.BaseServiceImpl;

import ${basepackage}.model.module.${classNameFirstLower}.${className}PO;
import ${basepackage}.dao.module.${classNameFirstLower}.I${className}DAO;
import ${basepackage}.service.module.${classNameFirstLower}.I${className}Service;

@Service
@Transactional(readOnly=true)
public class ${className}Service extends BaseServiceImpl<${className}PO, String> implements I${className}Service {

	@Resource
	private I${className}DAO ${classNameFirstLower}DAOImpl;

	public ZPage<${className}PO> get${className}Page(${className}PO ${classNameFirstLower}PO) {
		ZPage<${className}PO> zPage = ${classNameFirstLower}DAOImpl.get${className}Page(${classNameFirstLower}PO);
		return zPage;
	}

	public List<${className}PO> get${className}List(${className}PO ${classNameFirstLower}PO) {
		List<${className}PO> list = ${classNameFirstLower}DAOImpl.get${className}List(${classNameFirstLower}PO);
		return list;
	}

}