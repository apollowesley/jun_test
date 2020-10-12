package org.jiucheng.orm.util;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jiucheng.log.Logger;
import org.jiucheng.orm.DataAccessException;
import org.jiucheng.orm.Sql;

public class JdbcUtil {
	
	private static final Logger log = Logger.getLogger(JdbcUtil.class);

	public static List<Map> resultSetToListMap(ResultSet rs) {
	    if(null == rs) {
	        return null;
	    }
        List<Map> result = new ArrayList<Map>();
        String[] metaDatas = metaDataOfResultSet(rs);
        Map rows;
        try {
            while (rs.next()) {
                rows = new HashMap();
                for (int i = 0; i < metaDatas.length; i++) {
                    rows.put(metaDatas[i], rs.getObject(i + 1));
                }
                result.add(rows);
            }
        } catch (SQLException e) {
            if(log.isErrorEnabled()) {
                log.error("", e);
            }
            throw new DataAccessException(e);
        }
		return result;
	}
	
	public static String[] metaDataOfResultSet(ResultSet rs) {
		String[] result = new String[]{};
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			result = new String[cols];
			for (int i = 1; i <= cols; i++) {
				result[i - 1] = rsmd.getColumnLabel(i);
			}
		} catch (SQLException e) {
            if(log.isErrorEnabled()) {
                log.error("", e);
            }
            throw new DataAccessException(e);
		}
		return result;
	}
	
	public static List<Map> list(Connection conn, Sql sh){
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        try{
            pStmt = conn.prepareStatement(sh.getSql());
            buildPreparedStatement(pStmt, sh.getValues());
            rs = pStmt.executeQuery();
            return resultSetToListMap(rs);
        }catch(SQLException e) {
            if(log.isErrorEnabled()) {
                log.error("", e);
            }
            throw new DataAccessException(e);
        }finally {
            close(rs);
            close(pStmt);
        }
    }
    
    public static int update(Connection conn, Sql sh) {
        int r;
        PreparedStatement pStmt = null;
        try {
            pStmt = conn.prepareStatement(sh.getSql());
            buildPreparedStatement(pStmt, sh.getValues());
            r = pStmt.executeUpdate();
        }catch(SQLException e) {
            if(log.isErrorEnabled()) {
                log.error("",e);
            }
            throw new DataAccessException(e);
        }finally {
            close(pStmt);
        }
        return r;
    }
    
    public static Serializable save(Connection conn, Sql sh) {
        Object r = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        try {
            pStmt = conn.prepareStatement(sh.getSql(), Statement.RETURN_GENERATED_KEYS);
            buildPreparedStatement(pStmt, sh.getValues());
            pStmt.execute();
            rs = pStmt.getGeneratedKeys();
            if(rs.next()) {
                r = rs.getObject(1);
            }  
        }catch(SQLException e) {
            if(log.isErrorEnabled()) {
            	log.error(e.getMessage(),e);
            }
            throw new DataAccessException(e);
        }finally {
            close(rs);
            close(pStmt);
        }
        return (Serializable) r;
    }
    
    public static int delete(Connection conn, Sql sh) {
        return update(conn, sh);
    }
    
    public static List<Map> call(Connection conn, Sql sh) {
        List<Map> r = new ArrayList<Map>();
        CallableStatement cStmt = null;
        ResultSet rs = null;
        try {
            cStmt = conn.prepareCall(sh.getSql());
            buildCallableStatement(cStmt, sh.getValues());
            cStmt.execute();
            rs = cStmt.getResultSet();
            r = resultSetToListMap(rs); 
        }catch(SQLException e) {
            if(log.isErrorEnabled()) {
                log.error("",e);
            }
            throw new DataAccessException(e);
        }finally {
            close(rs);
            close(cStmt);
        }
        return r;
    }
    
    public static ResultSet getResultSet(Connection conn, Sql sh) {
        PreparedStatement pStmt;
        ResultSet rs;
        try {
            pStmt = conn.prepareStatement(sh.getSql());
            buildPreparedStatement(pStmt, sh.getValues());
            pStmt.execute();
            rs = pStmt.getResultSet();
        }catch(SQLException e) {
            if(log.isErrorEnabled()) {
                log.error("", e);
            }
            throw new DataAccessException(e);
        }
        return rs;
    }
    
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                if(!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                if(log.isErrorEnabled()) {
                	log.error("close Connection fault", e);
                }
            }
        }
    }
    
	public static void close(ResultSet rs){
	    if(null != rs) {
            try {
                rs.close();
            } catch (SQLException e) {
                if(log.isErrorEnabled()) {
                	log.error("close ResultSet fault", e);
                }
            }
	    }
	}
	
	public static void close(Statement stmt){
        if(null != stmt) {
            try {
                stmt.close();
            } catch (SQLException e) {
                if(log.isErrorEnabled()) {
                	log.error("close Statement fault", e);
                }
            }
        }
	}
	
    private static void buildPreparedStatement(PreparedStatement pStmt, List<Object> args) throws SQLException{
        for(int i = 0; i < args.size(); i ++) {
            pStmt.setObject(i + 1, args.get(i));
        }
    }
    
    private static void buildCallableStatement(CallableStatement cStmt, List<Object> args) throws SQLException {
        for(int i = 0; i < args.size(); i ++) {
            cStmt.setObject(i + 1, args.get(i));
        }
    }
}
