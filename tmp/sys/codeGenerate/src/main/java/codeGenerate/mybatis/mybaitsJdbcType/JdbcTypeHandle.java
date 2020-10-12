
package codeGenerate.mybatis.mybaitsJdbcType;

import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;

public interface JdbcTypeHandle {

	public ItemVo handle(PackageColumnVo packageColumnVo);
}
