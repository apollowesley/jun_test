package cn.coder.jdbc.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.coder.jdbc.util.ObjectUtils;

public final class MulitResult {
	private final List<String> sqlArray;
	private final List<Class<?>> targetArray;
	private final List<Object[]> dataArray;
	private final HashMap<Integer, List<Object>> resultMap;

	public MulitResult() {
		sqlArray = new ArrayList<>();
		targetArray = new ArrayList<>();
		dataArray = new ArrayList<>();
		resultMap = new HashMap<>();
	}

	public <T> MulitResult add(Class<T> target, String sql, Object... array) {
		this.targetArray.add(target);
		this.sqlArray.add(sql);
		this.dataArray.add(array);
		return this;
	}

	public String getSql() {
		StringBuilder script = new StringBuilder();
		for (String sql : sqlArray) {
			script.append(sql).append(";");
		}
		return script.toString();
	}

	public Object[] getData() {
		Object[] temp = new Object[0];
		for (Object[] data : dataArray) {
			temp = ObjectUtils.mergeArray(temp, data);
		}
		return temp;
	}

	public Class<?> getTarget(int index) {
		return targetArray.get(index);
	}

	public String getSql(int index) {
		return sqlArray.get(index);
	}

	public void putResult(int index, Object obj) {
		if (resultMap.containsKey(index)) {
			resultMap.get(index).add(obj);
		} else {
			ArrayList<Object> temp = new ArrayList<>();
			temp.add(obj);
			resultMap.put(index, temp);
		}
	}

	/**
	 * 通过索引获取查询结果
	 * @param index 从0开始
	 * @return
	 */
	public Object getResult(int index) {
		return resultMap.get(index).get(0);
	}

	public List<?> getResultList(int index) {
		return resultMap.get(index);
	}
}
