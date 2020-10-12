package cn.coder.jdbc.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.jdbc.core.BeanMapping;
import cn.coder.jdbc.util.FieldUtils;
import cn.coder.jdbc.util.ObjectUtils;

public final class QueryResultMapper<T> extends BaseResultMapper<List<T>> {
	private static final Logger logger = LoggerFactory.getLogger(QueryResultMapper.class);

	private int hash;
	private Class<T> target;
	private final boolean isPrimitive;

	public QueryResultMapper(Class<T> _target, String sql, Object[] array) {
		super(sql, array);
		this.target = _target;
		this.isPrimitive = FieldUtils.isPrimitive(_target);
		this.hash = Objects.hash(sql, _target);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> doPreparedStatement(PreparedStatement stmt) throws SQLException {
		List<T> dataList = new ArrayList<>();
		ResultSet rs = stmt.executeQuery();
		int count = 0;
		if (isPrimitive) {
			while (rs.next()) {
				dataList.add((T) FieldUtils.toValue(target, rs.getObject(1)));
				count++;
			}
		} else {
			Object bean;
			String[] labels = null;
			BeanMapping mappings = null;
			while (rs.next()) {
				if (mappings == null) {
					mappings = getBeanMapping(target, hash, rs.getMetaData());
					labels = mappings.keys();
				}
				bean = ObjectUtils.createBean(target, labels, rs, mappings);
				if (bean == null) {
					break;
				}
				dataList.add((T) bean);
				count++;
			}
		}
		rs.close();

		target = null;

		logger.debug("Result count:{}", count);
		return dataList;
	}

}
