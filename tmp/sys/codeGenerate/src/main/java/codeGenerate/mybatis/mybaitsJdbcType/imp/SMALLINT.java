
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.SMALLINT
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class SMALLINT implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType(Short.class.getName());
		ItemVo.setMybatisJdbcType("SMALLINT");
		ItemVo.setMybatisJavaType("short");
		ItemVo.setMybatisTypeHandler("");
		return ItemVo;
	}
}