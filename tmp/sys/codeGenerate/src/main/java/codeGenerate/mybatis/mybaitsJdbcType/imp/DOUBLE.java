
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.DOUBLE
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class DOUBLE implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType(Double.class.getName());
		ItemVo.setMybatisJdbcType("DOUBLE");
		ItemVo.setMybatisJavaType("double");
		ItemVo.setMybatisTypeHandler("");
		return ItemVo;
	}
}