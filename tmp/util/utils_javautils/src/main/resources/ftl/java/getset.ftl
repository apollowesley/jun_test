<#-- getset方法	 v1.0
-------------------------------------------------------
是否必须		  需要的值			注释			类型
	否		 names			字段名字		List<String>
-------------------------------------------------------
-->
<#list names as name>
	public void set${'${name}'?cap_first}(${types[name_index]} ${name}){
		this.${name} = ${name};
	}
	
	public ${types[name_index]} get${'${name}'?cap_first}(){
		return ${name};
	}
	
</#list>