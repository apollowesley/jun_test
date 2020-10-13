<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>
<#assign classNameLowerCase = className?lower_case>
package ${basepackage}.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;
import com.schrodinger.zbdp.framework.base.dao.impl.ZbdpHiberBaseDAOImpl;
import com.schrodinger.zcommon.page.ZPage;
import com.schrodinger.zutil.sql.SQLUtils;

import ${basepackage}.dao.I${className}DAO;
import ${basepackage}.model.${className}PO;

@Repository
public class ${className}DAOImpl extends ZbdpHiberBaseDAOImpl<${className}PO, String> implements I${className}DAO{
	
	private Search getSearch(${className}PO ${classNameFirstLower}PO){
		Search search = new Search();
		<#list table.columns as column>
		<#if !column.pk>
		<#if column.isDateTimeColumn>
		Date ${column.columnNameFirstLower} = ${classNameFirstLower}PO.get${column.columnName}();
		if(${column.columnNameFirstLower} != null){
			search.addFilterEqual("${column.columnNameFirstLower}", ${column.columnNameFirstLower});
		}
	    <#elseif column.isIntegerColumn>
	    int ${column.columnNameFirstLower} = ${classNameFirstLower}PO.get${column.columnName}();
		if(${column.columnNameFirstLower} > 0){
			search.addFilterEqual("${column.columnNameFirstLower}", ${column.columnNameFirstLower});
		}
	    <#elseif column.isStringColumn>
	    String ${column.columnNameFirstLower} = ${classNameFirstLower}PO.get${column.columnName}();
		if(StringUtils.isNotEmpty(${column.columnNameFirstLower})){
			search.addFilterLike("${column.columnNameFirstLower}", SQLUtils.processForLike(${column.columnNameFirstLower}));
		}
	    <#else>
		String ${column.columnNameFirstLower} = ${classNameFirstLower}PO.get${column.columnName}();
		if(StringUtils.isNotEmpty(${column.columnNameFirstLower})){
			search.addFilterLike("${column.columnNameFirstLower}", ${column.columnNameFirstLower});
		}
		</#if>
		</#if>
		</#list>
		return search;
	}
	
    public ZPage<${className}PO> get${className}Page(${className}PO ${classNameFirstLower}PO){
    	Search search = getSearch(${classNameFirstLower}PO);
		int start = ${classNameFirstLower}PO.getPageParam().getStart();
		int count = ${classNameFirstLower}PO.getPageParam().getCount();
		ZPage<${className}PO> page = super.getPageBySearch(search, start, count);
		return page;
    }
    
    public List<${className}PO> get${className}List(${className}PO ${classNameFirstLower}PO){
    	Search search = getSearch(${classNameFirstLower}PO);
		return super.search(search);
	}
    
}
