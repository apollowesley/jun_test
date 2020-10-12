package x.javaplus.mysql.generator.gen;

import java.util.List;

import x.javaplus.util.Util;
import x.javaplus.util.templet.Config;
import x.javaplus.util.templet.Templet;


public class MysqlGenGenerator {

	private static final String className = "MysqlGen";
	
	/**
	 * MysqlGen生成器
	 * @param database
	 * @param dtoPath
	 * @param dstPath
	 * @param packageName
	 */
	public MysqlGenGenerator( String dtoPath, String dstPath, String packageName ) {
		
		Templet temp 	= Config.getTemplet("MYSQL_GEN");
		
		temp.set("PACKAGE_NAME", packageName);
		temp.set("CLASS_NAME", className);

		List<Dto> dtos = Config.getDtos(dtoPath);
		temp.append( "GET_DAO", generateGetDao( dtos ) );
		temp.append( "DAO", generateDao( dtos ) );
		temp.append( "DTO", generateDto( dtos ) );
		
		dstPath = dstPath + "/"+className+".java";
		
		String string = temp.toString();
//		System.out.println(string);
		Util.File.write( dstPath, string );
	}

	// getDao函数生成器
	private String generateGetDao( List<Dto> dtos) {
		return new GetDaoGenerator( dtos ).toString();
	}

	// XXXDto.java 生成器
	private String generateDto(List<Dto> dtos) {
		return new DtoGenerator( dtos ).toString();
	}

	// XXXDao.java 生成器
	private String generateDao(List<Dto> dtos) {
		return new DaoGenerator( dtos ).toString();
	}

}
