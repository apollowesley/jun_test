package x.javaplus.csv;

import java.io.File;
import java.util.List;

import x.javaplus.collections.Lists;
import x.javaplus.csv.generator.CsvGenerator;
import x.javaplus.csv.generator.ObjectGenerator;
import x.javaplus.csv.util.Csv;
import x.javaplus.util.Util;
import x.javaplus.util.Util.Check;

public class App {

	/**
	 * 生成配置表
	 * @param args
	 */
	public static void generateConfigGen( String[] args ) {
		
		String filePath = null;
		String oPath = null;
		String genPath = null;
		String cPackageName = null;
		String oPackageName = null;
		
		for( int i = 0; i < args.length; i+=2 ){
			if( args[i].equals( "-filePath" ) )
				filePath = args[i+1];
			if( args[i].equals( "-oPath" ) )
				oPath = args[i+1];
			if( args[i].equals( "-genPath" ) )
				genPath = args[i+1];
			if( args[i].equals( "-cPackageName" ) )
				cPackageName = args[i+1];
			if( args[i].equals( "-oPackageName" ) )
				oPackageName = args[i+1];
		}
		
		Check.isNull( filePath, oPath, genPath, cPackageName, oPackageName );

		// 获取文件名
		List<File> files = Util.File.getFiles( filePath, "csv" );
		
		List<Csv> csvs = Lists.newArrayList();
		for( File file : files ){
			csvs.add( new Csv( file.getPath() ) );
		}
		
		// 生成gen类
		new CsvGenerator( csvs, filePath, genPath, cPackageName, oPackageName );
		// 生成各个对象类
		new ObjectGenerator( csvs, filePath, oPath, oPackageName );
		
	}
}
