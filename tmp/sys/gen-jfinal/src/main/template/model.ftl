package ${packageName};

import java.util.List;

import com.jfinal.plugin.activerecord.Model; 

public class ${tableNameFirstUpper} extends Model<${tableNameFirstUpper}>{

    private static final long serialVersionUID = 1L;
    public static final ${tableNameFirstUpper} dao = new ${tableNameFirstUpper}();
    
	<#list foreigns as f>
    public ${f.refName} get${f.refName}() {
    	return ${f.refName}.dao.findById(get("${f.forColName}"));
    }
    </#list>
 }