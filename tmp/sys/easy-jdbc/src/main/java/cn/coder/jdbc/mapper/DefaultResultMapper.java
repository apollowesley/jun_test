package cn.coder.jdbc.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DefaultResultMapper extends BaseResultMapper<Integer> {
	
	public DefaultResultMapper(String sql, Object[] array) {
		super(sql, array);
	}

	@Override
	public Integer doPreparedStatement(PreparedStatement stmt) throws SQLException {
		return stmt.executeUpdate();
	}
	
}
