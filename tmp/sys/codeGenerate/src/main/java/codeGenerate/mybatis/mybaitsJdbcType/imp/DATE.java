
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import java.util.Date;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.DATE
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class DATE implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType(Date.class.getName());
		ItemVo.setMybatisJdbcType("DATE");
		ItemVo.setMybatisJavaType("date");
		ItemVo.setMybatisTypeHandler("");
		return ItemVo;
	}
}