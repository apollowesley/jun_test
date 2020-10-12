package cn.coder.jdbc.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.coder.jdbc.core.EntityWrapper;
import cn.coder.jdbc.core.EntityWrapper.SQLType;

public final class EntityResultMapper extends BaseResultMapper<Boolean> {

	private final EntityWrapper wrapper;

	public EntityResultMapper(Object data, SQLType type) {
		this(getEntityWrapper(data, type));
	}

	public EntityResultMapper(final EntityWrapper wrapper) {
		super(wrapper.getSql(), wrapper.getData(), wrapper.returnGeneratedKey());
		this.wrapper = wrapper;
	}

	@Override
	public Boolean doPreparedStatement(PreparedStatement stmt) throws SQLException {
		int result = wrapper.getSqlType() == SQLType.SELECT 
				? getFirstValue(stmt.executeQuery()) 
				: stmt.executeUpdate();

		// 如果返回主键
		if (wrapper.returnGeneratedKey()) {
			int pk = getFirstValue(stmt.getGeneratedKeys());
			if (pk > 0)
				wrapper.setGeneratedKey(pk);
		}
		wrapper.clear();
		return result > 0;
	}

	private int getFirstValue(ResultSet result) throws SQLException {
		int num = 0;
		while (result.next()) {
			num = result.getInt(1);
			break;
		}
		result.close();
		return num;
	}
}
