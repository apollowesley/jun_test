
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.FLOAT
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class FLOAT implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType(Float.class.getName());
		ItemVo.setMybatisJdbcType("FLOAT");
		ItemVo.setMybatisJavaType("float");
		ItemVo.setMybatisTypeHandler("");
		return ItemVo;
	}
}