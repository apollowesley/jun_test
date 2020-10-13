<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
package ${basepackage}.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.schrodinger.zbdp.framework.base.dao.IZbdpHiberBaseDAO;
import com.schrodinger.zbdp.framework.base.service.impl.BaseServiceImpl;
import com.schrodinger.zcommon.page.ZPage;
import com.schrodinger.zcommon.spring.SpringBeanFactory;

import ${basepackage}.dao.I${className}DAO;
import ${basepackage}.model.${className}PO;
import ${basepackage}.service.I${className}Service;

@Service
public class ${className}ServiceImpl extends
		BaseServiceImpl<${className}PO, String> implements I${className}Service {
	@Resource
	private I${className}DAO ${classNameFirstLower}DAOImpl;

	@Resource(name = "${classNameFirstLower}DAOImpl")
    public void setZbdpHiberBaseDAOImpl(
            IZbdpHiberBaseDAO<${className}PO, String> zbdpHiberBaseDAOImpl) {
        super.setZbdpHiberBaseDAOImpl(zbdpHiberBaseDAOImpl);
    }
	
	public static I${className}Service getInstance() {
		I${className}Service ${classNameFirstLower}Service = (I${className}Service) SpringBeanFactory
				.getBean("${classNameFirstLower}ServiceImpl");
		return ${classNameFirstLower}Service;
	}

	public ZPage<${className}PO> get${className}Page(${className}PO ${classNameFirstLower}PO) {
        ZPage<${className}PO> page = ${classNameFirstLower}DAOImpl.get${className}Page(${classNameFirstLower}PO);
        return page;
    }
	
	public List<${className}PO> get${className}List(${className}PO ${classNameFirstLower}PO){
		List<${className}PO> list = ${classNameFirstLower}DAOImpl.get${className}List(${classNameFirstLower}PO);
		return list;
	}
}
