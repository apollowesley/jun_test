package ${packageName};

import com.jfinal.core.Controller;

import ${modelPackageName}.*;

public class ${tableNameFirstUpper}Controller extends Controller {
	public void index() {
		setAttr("${tableName}Page", ${tableNameFirstUpper}.dao.paginate(getParaToInt(0, 1), 10, "select *", "from ${tableName} order by id asc"));
		render("list.html");
	}
	
	public void add() {
		<#list foreigns as f>
	    setAttr("${f.refName?lower_case}", ${f.refName}.dao.find("select * from ${f.refName?lower_case}"));
	    </#list>
	    render("edit.html");
	}
	
	public void save() {
		${tableNameFirstUpper} ${tableName} = getModel(${tableNameFirstUpper}.class);
		if (${tableName}.get("id") == null) {
			${tableName}.save();
		} else {
			${tableName}.update();
		}
		redirect("/${tableName}");
	}
	
	public void edit() {
		<#list foreigns as f>
		setAttr("${f.refName?lower_case}", ${f.refName}.dao.find("select * from ${f.refName?lower_case}"));
	    </#list>
		setAttr("${tableName}", ${tableNameFirstUpper}.dao.findById(getParaToInt()));
		render("edit.html");
	}
	
	public void update() {
		getModel(${tableNameFirstUpper}.class).update();
		redirect("/${tableName}");
	}
	
	public void delete() {
		${tableNameFirstUpper}.dao.deleteById(getParaToInt());
		redirect("/${tableName}");
	}
}
