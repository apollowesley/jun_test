
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.TINYINT
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class TINYINT implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType(Byte.class.getName());
		ItemVo.setMybatisJdbcType("TINYINT");
		ItemVo.setMybatisJavaType("byte");
		ItemVo.setMybatisTypeHandler("");
		return ItemVo;
	}
}