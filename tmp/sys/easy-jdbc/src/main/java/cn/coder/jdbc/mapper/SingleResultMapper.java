package cn.coder.jdbc.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import cn.coder.jdbc.core.BeanMapping;
import cn.coder.jdbc.util.FieldUtils;
import cn.coder.jdbc.util.ObjectUtils;

public final class SingleResultMapper<T> extends BaseResultMapper<T> {
	private int hash;
	private Class<T> target;
	private final boolean isPrimitive;

	public SingleResultMapper(Class<T> _target, String sql, Object[] array) {
		super(sql, array);
		this.target = _target;
		this.isPrimitive = FieldUtils.isPrimitive(_target);
		this.hash = Objects.hash(sql, _target);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T doPreparedStatement(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.executeQuery();
		Object t = null;
		// 将判断放在循环外面
		if (isPrimitive) {
			while (rs.next()) {
				t = FieldUtils.toValue(target, rs.getObject(1));
				break;
			}
		} else {
			String[] labels;
			BeanMapping mappings;
			while (rs.next()) {
				mappings = getBeanMapping(target, hash, rs.getMetaData());
				labels = mappings.keys();
				t = ObjectUtils.createBean(target, labels, rs, mappings);
				break;
			}
		}
		rs.close();

		target = null;

		return (T) t;
	}
}
