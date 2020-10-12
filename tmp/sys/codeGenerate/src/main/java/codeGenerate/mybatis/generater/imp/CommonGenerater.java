
package codeGenerate.mybatis.generater.imp;

import java.util.HashMap;
import java.util.Map;

import codeGenerate.mybatis.generater.AbstractGenerater;
import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;

public class CommonGenerater extends AbstractGenerater {

	@Override
	public Map<String, String> getColumnCommont() {
		Map<String, String> columnCommontMap = new HashMap<>();
		return columnCommontMap;
	}

	@Override
	public Map<String, JdbcTypeHandle> registerTypeHandle() {
		Map<String, JdbcTypeHandle> map = new HashMap<>();
		return map;
	}
}
