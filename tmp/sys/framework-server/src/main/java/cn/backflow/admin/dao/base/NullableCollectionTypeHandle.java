package cn.backflow.admin.dao.base;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MyBatic的<collection/>关联不像<result/>一样忽略缺失的列, 用该TypeHandler处理这个问题
 * Created by hunan on 2017/5/21.
 */
public class NullableCollectionTypeHandle extends BaseTypeHandler<Integer> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Integer parameter, JdbcType jdbcType) throws SQLException {
        System.out.println("setNonNullParameter");
        ps.setInt(i, parameter);
    }

    @Override
    public Integer getNullableResult(ResultSet rs, String columnName) throws SQLException {
        System.out.println("getNullableResult");
        return rs.getInt(columnName);
    }

    @Override
    public Integer getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        System.out.println("getNullableResult");
        return rs.getInt(columnIndex);
    }

    @Override
    public Integer getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        System.out.println("getNullableResult");
        return cs.getInt(columnIndex);
    }
}
