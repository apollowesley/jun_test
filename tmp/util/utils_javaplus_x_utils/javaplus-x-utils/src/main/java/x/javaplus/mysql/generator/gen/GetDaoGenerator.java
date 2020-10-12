package x.javaplus.mysql.generator.gen;

import java.util.List;

import x.javaplus.string.StringPrinter;
import x.javaplus.util.templet.Config;
import x.javaplus.util.templet.Templet;

public class GetDaoGenerator {

	private StringPrinter ret;
	
	public GetDaoGenerator( List<Dto> dtos ) {
	
		ret	= new StringPrinter();
		Templet temp = Config.getTemplet("GET_DAO");
		
		for( Dto dto : dtos ){
			String className = dto.getSimpleClassName();
			temp.set( "CLASS_NAME", className );
			temp.set( "DB_NAME", className.toLowerCase() );
//			ret.println( temp.toString() );
			ret.println( temp.toString().replaceAll("\r{2,50}", "\r") );
			temp.clear();
		}
	}
	
	public String toString(){
		return ret.toString();
	}

}
