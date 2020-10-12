package x.javaplus.mysql.generator.db;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import x.javaplus.mysql.db.DBObject;
import x.javaplus.mysql.generator.gen.Dto;
import x.javaplus.util.templet.Config;
import x.javaplus.util.templet.Field;

/**
 * 数据库生成
 * @author deng		
 * @date 2015-7-14 上午9:17:32
 */
public class MysqlGenerator {

	private DBObject dbo ;
	private final String database;
	
	public MysqlGenerator( String database, String args ) throws SQLException {
		
		if( database == null || database.isEmpty() )
			throw new RuntimeException( "数据库名字错误" );
			
		this.database 	= database;
		dbo 			= DBObject.create();
		
		// 这里先创建数据库
		createDatabase( );
		
		List<Dto> dtos = Config.getDtos( args );
		for( Dto d : dtos ){
			
			String table 	= d.getSimpleClassName().toLowerCase();
			
			if( tableIsExists( table ) ){
				createColumns( getDBTableName(table), d.getVariablesAllClone() );
				
			}else{
				createTable( getDBTableName(table), d.getVariablesAll() );
			}
		}
		
		dbo.close();
	}

	// 获取经过封装的表名
	private String getDBTableName(String table) {
		return "`"+database+"`."+table;
	}

	// 创建数据库
	private void createDatabase( ) throws SQLException {
		//"CREATE DATABASE IF NOT EXISTS `kkgameserver5`"
		dbo.prepareStatement( "CREATE DATABASE IF NOT EXISTS `" + database + "`" );
		dbo.executeUpdate();
	}

	// 遍历字段列表 检测 有就修改 没有就添加 
	private void createColumns( String table, List<Field> columns ) throws SQLException {
		if( columns == null || columns.isEmpty() )
			return;
		
		dbo.prepareStatement( "DESC " +table );
		dbo.executeQuery();
		while( dbo.next() ){
			
			String name = dbo.getString("Field");
			String type = dbo.getString("Type");
			
			// 看有没有这个字段 如果没有 那么就删除掉  有的话 就看是否需要修改
			Field o 	= getField( name, columns );
			if( o == null ){
				dropColumn( table, name );
			}else if( !o.sqlType().equalsIgnoreCase( type ) ){
				modifyColumn( table, o );
			}
//			System.out.println( type + ", " + name );
		}
		
		// 这里检测是否还有字段 没有就直接返回
		if( columns.isEmpty() )
			return;
		for( Field f : columns ){
			addColumn( table, f );
		}
	}
	
	// 删除表里面的一个字段
	private void dropColumn( String table, String column ) throws SQLException {
		dbo.prepareStatement( "alter table "+table+" drop column " + column );
		dbo.executeUpdate();
	}

	private Field getField( String name, List<Field> columns ){
		Iterator<Field> iter = columns.iterator();
		while( iter.hasNext() ){
			Field o = iter.next();
			if( o.getName().equalsIgnoreCase( name ) ){
				iter.remove();
				return o;
			}
		}
		return null;
	}

	// 创建字段
	private void createTable( String table, List<Field> columns ) throws SQLException {
		if( columns == null || columns.isEmpty() )
			return;
		String sql = "";
		for( Field f : columns ){
			if( !sql.isEmpty() ) 
				sql += ",";
			sql += f.sql();
		}
		sql = "CREATE TABLE "+table+" (" +sql+ ")ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;";
		dbo.prepareStatement( sql );
		dbo.executeUpdate();
	}
	
	// 添加一个字段到表里 
	private void addColumn( String table, Field f ) throws SQLException {
		dbo.prepareStatement( "ALTER TABLE "+table+" ADD "+f.sql() );
		dbo.executeUpdate();
	}

	// 修改一个字段
	private void modifyColumn( String table, Field f ) throws SQLException {
		dbo.prepareStatement( "ALTER TABLE "+table+" MODIFY COLUMN "+f.sql() );
		dbo.executeUpdate();
	}
	
	// 检查这个表 是否存在
	private boolean tableIsExists( String table ) throws SQLException {
//		dbo.prepareStatement( "SHOW TABLES LIKE `"+database+"`."+table );
		dbo.prepareStatement( "select `TABLE_NAME` from `INFORMATION_SCHEMA`.`TABLES` where `TABLE_SCHEMA`='"+database+"' and `TABLE_NAME`='"+table+"'" );
		dbo.executeQuery();
		return dbo.next();
	}
	
	// 检查这个字段是否存在
//	private String columnIsExists( String table, String column ) throws SQLException {
//		dbo.prepareStatement( "DESC " +table + " " + column );
//		dbo.executeQuery();
//		return (String) (dbo.next() ? dbo.get("Type") : null);
//	}

}
