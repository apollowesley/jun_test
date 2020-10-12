package x.javaplus.mysql.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidPooledConnection;

/**
 * DB连接对象
 * @author deng		
 */
public class DBObject {
	
	private DruidPooledConnection con;
	
	private PreparedStatement pst;
	
	private ResultSet ret;
	
	private int parameterIndex = 0;
	
	public static DBObject create() {
		return new DBObject( Dbcp.getConnection() );
	}
	
	public static DBObject create( String sql ) throws SQLException {
		return new DBObject( Dbcp.getConnection(), sql );
	}
	
	private DBObject( DruidPooledConnection con, String sql ) throws SQLException{
		this.con = con;
		this.pst = con.prepareStatement( sql );
	}
	private DBObject( DruidPooledConnection con ) {
		this.con = con;
	}
	
	public void prepareStatement( String sql ) throws SQLException{
		this.pst = con.prepareStatement( sql );
	}
	
	public void executeQuery() throws SQLException{
		ret = pst.executeQuery();
	}
	
	public int executeUpdate() throws SQLException{
		return pst.executeUpdate();
	}
	
	public void close() {
		Dbcp.close( ret, pst, con );
	}
	
	public boolean next() throws SQLException {
		return ret.next();
	}
	
	/////////////////////////// set /////////////////////////////////////
//	public void setByte( int x ) throws SQLException{
//		pst.setByte( ++parameterIndex, (byte) x );
//	}
//	public void setShort( int x ) throws SQLException{
//		pst.setShort( ++parameterIndex, (short) x );
//	}
//	public void setInt( int x ) throws SQLException{
//		pst.setInt( ++parameterIndex, x );
//	}
//	public void setFloat( float x ) throws SQLException{
//		pst.setFloat( ++parameterIndex, x );
//	}
//	public void setLong( long x ) throws SQLException{
//		pst.setLong( ++parameterIndex, x );
//	}
//	public void setDouble( double x ) throws SQLException{
//		pst.setDouble( ++parameterIndex, x );
//	}
//	public void setString( String x ) throws SQLException{
//		pst.setString( ++parameterIndex, x );
//	}
	public void setBytes( byte[] x ) {
		try {
			pst.setBytes( ++parameterIndex, x );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void set( Object x ) {
		try {
			pst.setObject( ++parameterIndex, x );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////// get /////////////////////////////////////
	public byte getByte( String x ) {
		try {
			return ret.getByte( x );
		} catch (SQLException e) {
			return 0;
		}
	}
	public short getShort( String x ) {
		try {
			return ret.getShort( x );
		} catch (SQLException e) {
			return 0;
		}
	}
	public int getInt( String x ) {
		try {
			return ret.getInt( x );
		} catch (SQLException e) {
			return 0;
		}
	}
	public int getInt( int x ) {
		try {
			return ret.getInt( x );
		} catch (SQLException e) {
			return 0;
		}
	}
	public float getFloat( String x ) {
		try {
			return ret.getFloat( x );
		} catch (SQLException e) {
			return 0;
		}
	}
	public long getLong( String x ) {
		try {
			return ret.getLong( x );
		} catch (SQLException e) {
			return 0;
		}
	}
	public long getLong( int x ) {
		try {
			return ret.getLong( x );
		} catch (SQLException e) {
			return 0;
		}
	}
	public double getDouble( String x ) {
		try {
			return ret.getDouble( x );
		} catch (SQLException e) {
			return 0;
		}
	}
	public double getDouble( int x ) {
		try {
			return ret.getDouble( x );
		} catch (SQLException e) {
			return 0;
		}
	}
	public String getString( String x ) {
		try {
			return ret.getString( x );
		} catch (SQLException e) {
			return "";
		}
	}
	public String getString( int x ) {
		try {
			return ret.getString( x );
		} catch (SQLException e) {
			return "";
		}
	}
	public byte[] getBytes( String x ) {
		try {
			return ret.getBytes( x );
		} catch (SQLException e) {
			return null;
		}
	}
	public byte[] getBytes( int x ) {
		try {
			return ret.getBytes( x );
		} catch (SQLException e) {
			return null;
		}
	}
	public Object get( String x ) {
		try {
			return ret.getObject( x );
		} catch (SQLException e) {
			return null;
		}
	}
	public Object get( int x ) {
		try {
			return ret.getObject( x );
		} catch (SQLException e) {
			return null;
		}
	}
	
	
}
