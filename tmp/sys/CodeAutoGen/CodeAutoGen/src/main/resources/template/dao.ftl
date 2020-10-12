[#ftl]
package ${daoPackageName};

import java.util.List;
import ${pojoFullClassName};

public interface ${className}Dao {

	[#if paramType??]
	/**
	 * 单查询
	 * @param arg
	 */
	${className} querySingle(${paramType} arg);
	[/#if]
	
	/**
	 * 全查询
	 */
	List<${className}> queryFull();
	
	/**
	 * 插入
	 * @param ${className?uncap_first}
	 */
	void insert(${className} ${className?uncap_first});
	
	[#if paramType??]
	/**
	 * 更新
	 * @param ${className?uncap_first}
	 */
	int update(${className} ${className?uncap_first});
	[/#if]
	
	[#if paramType??]
	/**
	 * 删除
	 * @param arg
	 */
	int delete(${paramType} arg);
	[/#if]
	
	/**
	 * 查询总记录数
	 */
	int queryCount();
}