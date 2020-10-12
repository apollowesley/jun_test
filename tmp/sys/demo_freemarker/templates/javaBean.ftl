package ${qualifiedName};

/**
 * 
 * @author zhoux
 * @Date ${date}
 * @Email zhoux@souche.com
 * @Desc ${className}
 */
public class ${className} {

	public ${className}(){}
	
<#list fieldList as bean>
	private ${bean.getJavaType()} ${bean.getJavaName()};
	
	public ${bean.getJavaType()} get${bean.getMethodName()}(){
		return ${bean.getJavaName()};
	}	
	public void set${bean.getMethodName()}(${bean.getJavaType()} ${bean.getJavaName()}){
	
		return ${bean.getJavaName()};
	}
</#list>	


}