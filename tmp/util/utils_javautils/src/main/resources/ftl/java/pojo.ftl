<#-- 实体类	 v1.0
-------------------------------------------------------
是否必须		  需要的值			注释			类型
	是		package			包名			String;
	是		className 		类名			String;
	是		names 	  		字段名		list<String>
	是		types			返回值类型		list<String>
	否		extends			继承			String
-------------------------------------------------------
	*还不能实现接口
-->
package ${package};

import java.io.Serializable;
<#include "import.ftl"> 

public class ${className} ${extends} implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
<#list names as name>
	private ${types[name_index]} ${name}${values[name_index]};

</#list>
<#include "getset.ftl">
}