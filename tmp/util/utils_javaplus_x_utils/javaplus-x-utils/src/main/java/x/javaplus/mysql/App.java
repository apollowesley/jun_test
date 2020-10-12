package x.javaplus.mysql;


import java.sql.SQLException;

import x.javaplus.mysql.generator.db.MysqlGenerator;
import x.javaplus.mysql.generator.gen.MysqlGenGenerator;
import x.javaplus.util.Util.Check;

public class App {

	/**
	 * 生成mysql主类1
	 * @param args
	 */
	public static void generateMysqlGen(  String[] args ) {
		
		String dtoPath = null;
		String dstPath = null;
		String packageName = null;
		
		for( int i = 0; i < args.length; i+=2 ){
			if( args[i].equals( "-dtoPath" ) )
				dtoPath = args[i+1];
			if( args[i].equals( "-dstPath" ) )
				dstPath = args[i+1];
			if( args[i].equals( "-packageName" ) )
				packageName = args[i+1];
		}
		
		Check.isNull( dtoPath, dstPath, packageName );

		new MysqlGenGenerator( dtoPath, dstPath, packageName );
	}

	/**
	 * 生成mysql数据库ssss
	 * @param database 数据库名字
	 * @param path 表路径
	 */
	public static void generateMysql( String database, String path ) {
		
		try {
			new MysqlGenerator( database, path );
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
