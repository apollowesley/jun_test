
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.NCHAR
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class NCHAR implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType(String.class.getName());
		ItemVo.setMybatisJdbcType("NCHAR");
		ItemVo.setMybatisJavaType("string");
		ItemVo.setMybatisTypeHandler("");
		return ItemVo;
	}
}