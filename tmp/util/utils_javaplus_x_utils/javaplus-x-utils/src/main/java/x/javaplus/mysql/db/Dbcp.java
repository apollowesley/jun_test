package x.javaplus.mysql.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import x.javaplus.collections.Lists;
import x.javaplus.util.Resources;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

/**
 * Dbcp 是数据库连接池的总管理类 封装了对于连接池的相关操作
 * 
 * @author deng
 */
public class Dbcp {
	private Dbcp() { }
	
	// 数据库
	private static DruidDataSource 	dataSource 	= null;
	
	private final static Log 		logger 		= LogFactory.getLog(Dbcp.class);
	
	// 静态初始化
	static {
		initialize();
	}
	private static void initialize() {
		try {
			Properties properties = loadPropertyFile( "db.properties" );
			dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource( properties );
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	private static Properties loadPropertyFile( String configName ) {
		if ( null == configName || configName.isEmpty() )
			throw new IllegalArgumentException( "Properties file path can not be null : " + configName );

		Properties properties = new Properties();
		try {
			
			InputStream input = new FileInputStream( Resources.getResource( configName ) );
			properties.load( input );
			input.close();
		
		} catch (FileNotFoundException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }

		return properties;
	}

	/**
	 *	从连接池中获取一个有效连接
	 * @return
	 * 		返回一个可用数据库连接
	 */
	public static DruidPooledConnection getConnection(){
		DruidPooledConnection conn = null;
		try {
			
			conn 	= dataSource.getConnection();
			
			//logger.debug( prefixion() + "get connection, " + dataSource.getActiveCount() + "/" + dataSource.getMaxActive() );
			
		} catch (SQLException e) { 
			logger.error( prefixion() + "get connection error!", e );
		}
		return conn;
	}

	/**
	 * 打印 数据库 当前使用情况
	 */
	public static void print(){
		logger.debug( prefixion() + "current connections:" + dataSource.getActiveCount() + "/" + dataSource.getMaxActive() );
	}
	
	/**
	 * 关闭本次连接
	 * @param rs
	 * @param st
	 * @param con
	 */
	public static void close( ResultSet rs, PreparedStatement st, DruidPooledConnection con ){
		try {
			
			if( con != null ) con.close();
			if( st != null ) st.close();
			if( rs != null ) rs.close();
			
			//logger.debug( prefixion() + "close connection, " + dataSource.getActiveCount() + "/" + dataSource.getMaxActive() );
		
		} catch (SQLException e) { 
			logger.error( prefixion() + "close connection error!", e );
		}
	}
	
	/**
	 * 清空所有表数据 
	 * @param database 数据库
	 * @param args 忽略的表名
	 */
	public static void pruge( String database, String ... args ){
		
		try {
			DBObject o = DBObject.create();
			
			List<String> allTableName = getAllTableName( database, args );
			for( String name : allTableName ){
			
				o.prepareStatement( "TRUNCATE TABLE `" +database+"`."+name );
			
				o.executeUpdate();
			}
			o.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除所有表
	 * @param database 数据库
	 * @param args 忽略的表名
	 */
	public static void drop( String database, String ... args ){
		
		try {
			DBObject o = DBObject.create();
			
			List<String> allTableName = getAllTableName( database, args );
			for( String name : allTableName ){
			
				o.prepareStatement( "DROP TABLE `" +database+"`."+ name );
			
				o.executeUpdate();
			}
			o.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static List<String> getAllTableName( String database, String[] args ) {
		List<String> list		= Lists.newArrayList();
		try {
			DBObject o = DBObject.create();
			o.prepareStatement( "SELECT TABLE_SCHEMA as 'Use', TABLE_NAME as 'TableName' FROM INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+database+"' order by TABLE_NAME" );
			o.executeQuery();
			while( o.next() ){
				
				String name = o.getString( "TableName" );
				
				if( isHave( name, args ) )
					continue;
				
				list.add( name );
			}
			o.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private static boolean isHave(String name, String[] args) {
		if( args == null )
			return false;
		for( int i = 0; i < args.length; i++ ){
			if( name.equalsIgnoreCase( args[i] ) )
				return true;
		}
		return false;
	}
	
	// 前缀
	private static String prefixion(){
		return "[ Dbcp ]: ";
	}
}
