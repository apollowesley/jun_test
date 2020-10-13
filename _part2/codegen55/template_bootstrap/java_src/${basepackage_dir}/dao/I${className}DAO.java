<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
package ${basepackage}.dao;

import java.util.List;

import com.schrodinger.zbdp.framework.base.dao.IZbdpHiberBaseDAO;
import com.schrodinger.zcommon.page.ZPage;
import ${basepackage}.model.${className}PO;

public interface I${className}DAO extends IZbdpHiberBaseDAO<${className}PO, String> {
    
    public ZPage<${className}PO> get${className}Page(${className}PO ${classNameFirstLower}PO);
    
    public List<${className}PO> get${className}List(${className}PO ${classNameFirstLower}PO);
    
}