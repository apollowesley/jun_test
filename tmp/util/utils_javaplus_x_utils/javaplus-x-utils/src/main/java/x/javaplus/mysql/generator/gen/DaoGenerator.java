package x.javaplus.mysql.generator.gen;

import java.util.List;

import x.javaplus.string.StringPrinter;
import x.javaplus.util.templet.Config;
import x.javaplus.util.templet.Field;
import x.javaplus.util.templet.Templet;


public class DaoGenerator {
	
	private StringPrinter ret;

	public DaoGenerator( List<Dto> dtos ) {
		
		ret	= new StringPrinter();
		
		Templet temp = Config.getTemplet("DAO");
		
		for( Dto dto : dtos ){
			
			temp.set( "CLASS_NAME", dto.getSimpleClassName() );
			
			List<Field> vars = dto.getVariablesAll();
			StringPrinter setobject	= new StringPrinter();
			
			String type = "String";
			String varName = "\"'\"+id+\"'\"";
			
			for( Field var : vars ){
				generatorSetobject( setobject, var );
				if( var.getName().equals( "id" ) ){
					if( !var.getType().equals( type ) ){
						type = var.getType();
						varName = "String.valueOf(id)";
					}
				}
			}
			
			temp.set( "VARID_TYPE", type );
			temp.set( "VARID_NAME", varName );
			temp.append( "SETOBJECT", setobject.toString() );
			
			ret.println( temp.toString().replaceAll("\r{2,50}", "\r") );
			temp.clear();
		}
	}

	private void generatorSetobject(StringPrinter v, Field var) {
		Templet temp = Config.getTemplet("SETOBJECT");
		temp.set( "VARIATE_NAME", var.getName() );
		temp.set( "FUN_NAME", var.getFunName() );
		String string = temp.toString();
		v.println( string );
	}

	public String toString(){
		return "\t"+ret.toString();
	}
	
}