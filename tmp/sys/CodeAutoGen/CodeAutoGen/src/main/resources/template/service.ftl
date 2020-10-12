[#ftl]
package ${servicePackageName};

import java.util.List;
import ${pojoFullClassName};

public interface ${className}Service {

	[#if paramType??]
	/**
	 * 根据主键获取对象
	 * @param arg
	 */
	${className} get${className}(${paramType} arg);
	[/#if]
	
	/**
	 * 获取所有对象
	 */
	List<${className}> get${className}List();
	
	/**
	 * 新增一个对象
	 * @param ${className?uncap_first}
	 */
	void insert${className}(${className} ${className?uncap_first});
	
	[#if paramType??]
	/**
	 * 更新
	 * @param ${className?uncap_first}
	 */
	int update${className}(${className} ${className?uncap_first});
	[/#if]
	
	[#if paramType??]
	/**
	 * 删除
	 * @param arg
	 */
	int delete${className}(${paramType} arg);
	[/#if]
	
	/**
	 * 查询总记录数
	 */
	int get${className}Count();
}