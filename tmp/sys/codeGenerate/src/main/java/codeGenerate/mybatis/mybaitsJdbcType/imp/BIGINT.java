
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.BIGINT
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class BIGINT implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType(Long.class.getName());
		ItemVo.setMybatisJdbcType("BIGINT");
		ItemVo.setMybatisJavaType("long");
		ItemVo.setMybatisTypeHandler("");
		return ItemVo;
	}
}