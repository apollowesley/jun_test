package com.jfast.common.pagination;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoyou on 2018/4/18.
 */
public class PagingResultSetHandler extends DefaultResultSetHandler {

    private RowBounds rowBounds;
    public PagingResultSetHandler(Executor executor, MappedStatement mappedStatement, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql, RowBounds rowBounds) {
        super(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        this.rowBounds = rowBounds;
    }

    @Override
    public List<Object> handleResultSets(Statement stmt) throws SQLException {
        final List<Object> multipleResults = new ArrayList<Object>();
        if (!(rowBounds instanceof PagingBounds)) {
            return multipleResults;
        }
        PagingBounds pagingBounds = (PagingBounds) rowBounds;

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            rs = stmt.getResultSet();
            int tp = rs.getType();
            if (tp == ResultSet.TYPE_SCROLL_INSENSITIVE) {
                rs.last();
                pagingBounds.setTotal(rs.getRow());
                rs.absolute(pagingBounds.getOffset());
                int count = 0;
                ResultSetMetaData metadata = rs.getMetaData();
                int columnCount = metadata.getColumnCount();
                while (rs.next()) {
                    if (count >= pagingBounds.getLimit() && pagingBounds.getLimit() > 0) {
                        break;
                    }
                    Map obj = new HashMap();
                    for (int i = 1; i <= columnCount; ++i) {
                        obj.put(metadata.getColumnLabel(i), getAndConvert(metadata, rs, i));
                    }
                    multipleResults.add(obj);
                    count++;
                }
            } else if (tp == ResultSet.TYPE_FORWARD_ONLY) {
                int crt = 0, added = 0, total = 0;
                while (rs.next()) {
                    // 取翻页范围内的数据
                    if (crt++ >= pagingBounds.getOffset() && (pagingBounds.getLimit() > 0 ? (added++ < pagingBounds.getLimit()) : true)) {
                        ResultSetMetaData metadata = rs.getMetaData();
                        int columnCount = metadata.getColumnCount();
                        Map obj = new HashMap<>();
                        for (int i = 1; i <= columnCount; ++i) {
                            obj.put(metadata.getColumnLabel(i), getAndConvert(metadata, rs, i));
                        }
                        multipleResults.add(obj);
                    }
                    total++;
                }
                pagingBounds.setTotal(total);
            }

        } catch (SQLException ex) {
            if(rs != null) {
                rs.close();
            }
            if(pstmt != null) {
                pstmt.close();
            }
        } finally {
            if(rs != null) {
                rs.close();
            }
            if(pstmt != null) {
                pstmt.close();
            }
        }

        return multipleResults;
    }

    private Object getAndConvert(ResultSetMetaData metadata, ResultSet rs, int index) throws SQLException {
        int type = metadata.getColumnType(index);
        try {
            switch (type) {
                case Types.CLOB:
                    Clob clob = rs.getClob(index);
                    if (clob == null) {
                        return null;
                    }
                    StringBuilder str = new StringBuilder();
                    byte[] bytes = new byte[1024];
                    InputStream in = clob.getAsciiStream();
                    int len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        str.append(new String(bytes, 0, len, "UTF-8"));
                    }
                    clob.free();
                    return str.toString().trim();
                case Types.BLOB:
                    Blob blob = rs.getBlob(index);
                    if (blob == null) {
                        return null;
                    }
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bytes = new byte[1024];
                    in = blob.getBinaryStream();
                    len = 0;
                    while ((len = in.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                    }
                    blob.free();
                    return out.toByteArray();
                default:
                    return rs.getObject(index);
            }
        } catch (IOException ex) {
            throw new SQLException(ex);
        }
    }
}
