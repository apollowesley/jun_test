
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.CLOB
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class CLOB implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType(String.class.getName());
		ItemVo.setMybatisJdbcType("CLOB");
		ItemVo.setMybatisJavaType("byte");
		ItemVo.setMybatisTypeHandler("org.apache.ibatis.type.ClobTypeHandler");
		return ItemVo;
	}
}