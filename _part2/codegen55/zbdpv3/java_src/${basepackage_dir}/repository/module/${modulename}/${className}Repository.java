<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
package ${basepackage}.repository.module.${modulename};

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.schrodinger.zbdp.repository.framework.BaseRepository;
import ${basepackage}.model.module.${modulename}.${className}PO;

public interface ${className}Repository extends BaseRepository<${className}PO, String>, JpaSpecificationExecutor<${className}PO> {

}