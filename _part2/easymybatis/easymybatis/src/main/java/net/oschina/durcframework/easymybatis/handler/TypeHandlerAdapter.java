/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.oschina.durcframework.easymybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

public abstract class TypeHandlerAdapter<T> extends BaseTypeHandler<T> {
	
	
	/**
	 * 解析数据库字段值，转成java对象值
	 * 
	 * @param columnValue
	 * @return
	 */
	protected abstract T convertValue(Object columnValue);

	/**
	 * 保存到数据库的值
	 * 
	 * @param defaultValue
	 * @return
	 */
	protected abstract Object getFillValue(T defaultValue);
	

	@Override
	public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return this.convertValue(rs.getObject(columnName));
	}

	@Override
	public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return this.convertValue(rs.getObject(columnIndex));
	}

	@Override
	public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return this.convertValue(cs.getObject(columnIndex));
	}

	@Override
	public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			if (jdbcType == null) {
				throw new TypeException(
						"JDBC requires that the JdbcType must be specified for all nullable parameters.");
			}
			try {
				this.setNullParameter(ps, i, parameter, jdbcType);
			} catch (SQLException e) {
				throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . "
						+ "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
						+ "Cause: " + e, e);
			}
		} else {
			try {
				setNonNullParameter(ps, i, parameter, jdbcType);
			} catch (Exception e) {
				throw new TypeException(
						"Error setting non null for parameter #" + i + " with JdbcType " + jdbcType + " . "
								+ "Try setting a different JdbcType for this parameter or a different configuration property. "
								+ "Cause: " + e,
						e);
			}
		}
	}
	
	protected void setNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		this.setParameterValue(ps, i, parameter, jdbcType);
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		this.setParameterValue(ps, i, parameter, jdbcType);
	}

	protected void setParameterValue(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		Object val = this.getFillValue(parameter);
		if(val == null) {
			ps.setNull(i, jdbcType.TYPE_CODE);
		}else {
			if(val instanceof Date) {
				Timestamp date = new Timestamp(((Date)val).getTime());
				ps.setTimestamp(i, date);
			}else {
				ps.setObject(i, val);
			}
		}
	}

	/**
	 * setParameter默认行为
	 * @param ps
	 * @param i
	 * @param parameter
	 * @param jdbcType
	 * @throws SQLException
	 */
	protected void setParameterDefault(PreparedStatement ps, int i, T parameter, JdbcType jdbcType)
			throws SQLException {
		super.setParameter(ps, i, parameter, jdbcType);
	}

}