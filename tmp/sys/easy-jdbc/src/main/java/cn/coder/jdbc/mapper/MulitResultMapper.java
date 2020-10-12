package cn.coder.jdbc.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.jdbc.core.BeanMapping;
import cn.coder.jdbc.support.MulitResult;
import cn.coder.jdbc.util.FieldUtils;
import cn.coder.jdbc.util.ObjectUtils;

public final class MulitResultMapper extends BaseResultMapper<Boolean> {
	private static final Logger logger = LoggerFactory.getLogger(MulitResultMapper.class);

	private MulitResult result;

	public MulitResultMapper(MulitResult mr) {
		super(mr.getSql(), mr.getData());
		this.result = mr;
	}

	@Override
	public Boolean doPreparedStatement(PreparedStatement stmt) throws SQLException {
		int index = 0;
		boolean hasResult = stmt.execute();
		while (hasResult) {
			result = toBeanList(stmt.getResultSet(), result, index);
			hasResult = stmt.getMoreResults();
			index++;
		}
		logger.debug("Result count:{}", index);
		return true;
	}

	private static MulitResult toBeanList(ResultSet rs, MulitResult mr, int index) throws SQLException {
		Class<?> target = mr.getTarget(index);
		int hash = Objects.hash(mr.getSql(index), target);
		boolean isPrimitive = FieldUtils.isPrimitive(target);

		Object bean;
		if (isPrimitive) {
			while (rs.next()) {
				bean = FieldUtils.toValue(target, rs.getObject(1));
				mr.putResult(index, bean);
			}
		} else {
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
				mr.putResult(index, bean);
			}
		}
		// 关闭ResultSet
		rs.close();

		return mr;
	}
}
