
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.CHAR
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class CHAR implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType(String.class.getName());
		ItemVo.setMybatisJdbcType("CHAR");
		ItemVo.setMybatisJavaType("string");
		ItemVo.setMybatisTypeHandler("");
		return ItemVo;
	}
}