
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.BLOB
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class BLOB implements JdbcTypeHandle {

	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		ItemVo ItemVo = new ItemVo();
		ItemVo.setJavaPropertyType("byte[]");
		ItemVo.setMybatisJdbcType("BLOB");
		ItemVo.setMybatisJavaType("byte");
		ItemVo.setMybatisTypeHandler("org.apache.ibatis.type.BlobTypeHandler");
		return ItemVo;
	}
}