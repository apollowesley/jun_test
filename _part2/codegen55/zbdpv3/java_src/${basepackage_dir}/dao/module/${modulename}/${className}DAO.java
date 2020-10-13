<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>
<#assign classNameLowerCase = className?lower_case>
package ${basepackage}.dao.module.${modulename};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.schrodinger.zbdp.dao.framework.impl.BaseDAOImpl;
import com.schrodinger.zbdp.dao.page.PageUtils;
import com.schrodinger.zbdp.dao.page.ZPage;
import com.schrodinger.zbdp.repository.framework.Criteria;
import com.schrodinger.zbdp.repository.framework.Restrictions;

import ${basepackage}.model.module.${modulename}.${className}PO;
import ${basepackage}.dao.module.${modulename}.I${className}DAO;
import ${basepackage}.repository.module.${modulename}.${className}Repository;

@Repository
public class ${className}DAO extends BaseDAOImpl<${className}PO, String> implements I${className}DAO {
	
	@Autowired
	private ${className}Repository ${classNameFirstLower}Repository;

	public ZPage<${className}PO> get${className}Page(${className}PO ${classNameFirstLower}PO) {
		Criteria<${className}PO> c = getCriteria(${classNameFirstLower}PO);
		int currentPage = ${classNameFirstLower}PO.getPageParam().getCurrentPage();
		int pageSize = ${classNameFirstLower}PO.getPageParam().getPageSize();
		Page<${className}PO> page = ${classNameFirstLower}Repository.findAll(c, new PageRequest(currentPage, pageSize));
		ZPage<${className}PO> zPage = PageUtils.convertSpringPageToZPage(page);
		return zPage;
	}

	public List<${className}PO> get${className}List(${className}PO ${classNameFirstLower}PO) {
		Criteria<${className}PO> c = getCriteria(${classNameFirstLower}PO);
		return ${classNameFirstLower}Repository.findAll(c);
	}

	private Criteria<${className}PO> getCriteria(${className}PO ${classNameFirstLower}PO) {
		Criteria<${className}PO> c = new Criteria<${className}PO>();
		<#list table.columns as column>
		<#if !column.pk>
		<#if column.isDateTimeColumn>
		Date ${column.columnNameFirstLower} = ${classNameFirstLower}PO.get${column.columnName}();
		if (${column.columnNameFirstLower} != null) {
			c.add(Restrictions.eq("${column.columnNameFirstLower}", ${column.columnNameFirstLower}, true));
		}
	    <#elseif column.isIntegerColumn>
	    int ${column.columnNameFirstLower} = ${classNameFirstLower}PO.get${column.columnName}();
	    if (${column.columnNameFirstLower} != null) {
			c.add(Restrictions.eq("${column.columnNameFirstLower}", ${column.columnNameFirstLower}, true));
		}
	    <#elseif column.isStringColumn>
	    String ${column.columnNameFirstLower} = ${classNameFirstLower}PO.get${column.columnName}();
		if (!StringUtils.isEmpty(${column.columnNameFirstLower})) {
			c.add(Restrictions.like("${column.columnNameFirstLower}", ${column.columnNameFirstLower}, true));
		}
	    <#else>
		String ${column.columnNameFirstLower} = ${classNameFirstLower}PO.get${column.columnName}();
		if (!StringUtils.isEmpty(${column.columnNameFirstLower})) {
			c.add(Restrictions.like("${column.columnNameFirstLower}", ${column.columnNameFirstLower}, true));
		}
		</#if>
		</#if>
		</#list>
		return c;
	}
	
}
