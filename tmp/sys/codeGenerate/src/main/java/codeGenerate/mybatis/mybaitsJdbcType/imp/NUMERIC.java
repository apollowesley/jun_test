
package codeGenerate.mybatis.mybaitsJdbcType.imp;

import java.math.BigDecimal;

import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

/**
 * TODO javadoc for codeGenerate.mybatis.mybaitsJdbcType.DECIMAL
 * @Copyright: 
 * @author: dw-fu1
 * @since: 2018年2月9日
 */
public class NUMERIC implements JdbcTypeHandle {
	@Override
	public ItemVo handle(PackageColumnVo packageColumnVo) {
		//精度大于0或长度大于18
		if (packageColumnVo.getDbColumnScale() > 0 || packageColumnVo.getDbColumnPrecision() > 18) {
			ItemVo ItemVo = new ItemVo();
			ItemVo.setJavaPropertyType(BigDecimal.class.getName());
			ItemVo.setMybatisJdbcType("DECIMAL");
			ItemVo.setMybatisJavaType("bigdecimal");
			return ItemVo;
			//精度都是0的
		} else if (packageColumnVo.getDbColumnPrecision() > 9) {
			ItemVo ItemVo = new ItemVo();
			ItemVo.setJavaPropertyType(Long.class.getName());
			ItemVo.setMybatisJdbcType("DECIMAL");
			ItemVo.setMybatisJavaType("long");
			return ItemVo;
		} else if (packageColumnVo.getDbColumnPrecision() > 4) {
			ItemVo ItemVo = new ItemVo();
			ItemVo.setJavaPropertyType(Integer.class.getName());
			ItemVo.setMybatisJdbcType("DECIMAL");
			ItemVo.setMybatisJavaType("int");
			return ItemVo;
		} else {
			ItemVo ItemVo = new ItemVo();
			ItemVo.setJavaPropertyType(Short.class.getName());
			ItemVo.setMybatisJdbcType("DECIMAL");
			ItemVo.setMybatisJavaType("short");
			return ItemVo;
		}
	}
}