
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.INT
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class INT implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType(Integer.class.getName());
		ItemVo.setMybatisJdbcType("INTEGER");
		ItemVo.setMybatisJavaType("int");
		ItemVo.setMybatisTypeHandler("");
		return ItemVo;
	}
}