[#ftl]
package ${servicePackageName}.impl;

import java.util.List;
import ${pojoFullClassName};
import ${servicePackageName}.${className}Service;
import ${daoPackageName}.${className}Dao;

public class ${className}ServiceImpl implements ${className}Service {
	
	[#assign daoName = className + 'Dao']
	private ${daoName} ${daoName?uncap_first};

	[#if paramType??]
	/**
	 * 根据主键获取对象
	 * @param arg
	 */
	public ${className} get${className}(${paramType} arg){
		return ${daoName?uncap_first}.querySingle(arg);
	}
	[/#if]
	
	/**
	 * 获取所有对象
	 */
	public List<${className}> get${className}List(){
		return ${daoName?uncap_first}.queryFull();
	}
	
	/**
	 * 新增一个对象
	 * @param ${className?uncap_first}
	 */
	public void insert${className}(${className} ${className?uncap_first}){
		${daoName?uncap_first}.insert(${className?uncap_first});
	}
	
	[#if paramType??]
	/**
	 * 更新对象
	 * @param ${className?uncap_first}
	 */
	public int update${className}(${className} ${className?uncap_first}){
		return ${daoName?uncap_first}.update(${className?uncap_first});
	}
	[/#if]
	
	[#if paramType??]
	/**
	 * 删除对象
	 * @param arg
	 */
	public int delete${className}(${paramType} arg){
		return ${daoName?uncap_first}.delete(arg);
	}
	[/#if]
	
	/**
	 * 查询总记录数
	 */
	public int get${className}Count(){
		return ${daoName?uncap_first}.queryCount();
	}
	
	public void set${daoName}(${daoName} ${daoName?uncap_first}){
		this.${daoName?uncap_first} = ${daoName?uncap_first};
	}
}