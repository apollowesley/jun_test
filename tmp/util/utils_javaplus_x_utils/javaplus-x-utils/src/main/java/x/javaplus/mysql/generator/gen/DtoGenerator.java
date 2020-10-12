package x.javaplus.mysql.generator.gen;

import java.util.List;

import x.javaplus.string.StringPrinter;
import x.javaplus.util.templet.Config;
import x.javaplus.util.templet.Field;
import x.javaplus.util.templet.Templet;


public class DtoGenerator {

	private StringPrinter ret;

	public DtoGenerator( List<Dto> dtos ) {
		
		ret	= new StringPrinter();
		
		Templet temp = Config.getTemplet("DTO");
		
		for( Dto dto : dtos ){
			
			temp.set( "CLASS_NAME", dto.getSimpleClassName() );
			
			List<Field> vars = dto.getVariablesAll();
			
			StringPrinter v 	= new StringPrinter();
			StringPrinter copy 	= new StringPrinter();
			StringPrinter getv 	= new StringPrinter();
			StringPrinter setv 	= new StringPrinter();
			StringPrinter cs 	= new StringPrinter();
			StringPrinter from 	= new StringPrinter();
			String str = "";
			
			for( Field var : vars ){
				generatorVar( v, var );
				generatorCopyVar( copy, var );
				generatorGetVar( getv, var );
				generatorSetVar( setv, var );
				generatorCS( cs, var );
				generatorFromObjVar( from, var );
				if( !str.isEmpty() ) str += "+\",\"+";
				str += ( "\""+var.getName()+"=\"+" + var.getName() );
			}
			
			temp.append( "FIELDS_PRIVATE", v.toString() );
			temp.append( "COPY_FS", copy.toString() );
			temp.append( "GETTER", getv.toString() );
			temp.append( "SETTER", setv.toString() );
			temp.append( "CHANGE_SQL", cs.toString() );
			temp.append( "FROM_DBOBJECT", from.toString() );
			temp.set( "TOSTRING", str );
			
			
			ret.println( temp.toString().replaceAll("\r{2,50}", "\r") );
			temp.clear();
		}
	}

	private void generatorCS(StringPrinter v, Field var) {
		Templet temp = Config.getTemplet("CHANGE_SQL");
		temp.set( "VARIATE_TYPE", var.getType() );
		temp.set( "VARIATE_NAME", var.getName() );
		temp.set( "VALUE", var.getType().equals( "String" ) ? "\"'\"+x+\"'\"" : "x" );
		String string = temp.toString();
		v.println( string );
	}

	private void generatorFromObjVar(StringPrinter v, Field var) {
		Templet temp = Config.getTemplet("FROM_DBOBJECT");
		temp.set( "VARIATE_NAME", var.getName() );
		temp.set( "TYPE_NAME", var.getTypeName() );
		String string = temp.toString();
		v.println( string );
	}

	private void generatorCopyVar(StringPrinter v, Field var) {
		Templet temp = Config.getTemplet("COPY_FS");
		temp.set( "VARIATE_NAME", var.getName() );
		String string = temp.toString();
		v.println( string );
	}

	private void generatorSetVar(StringPrinter v, Field var) {
		Templet temp = Config.getTemplet("SETTER");
		temp.set( "COMMENT", var.getComment() );
		temp.set( "FUN_NAME", var.getFunName() );
		temp.set( "VARIATE_TYPE", var.getType() );
		temp.set( "VARIATE_NAME", var.getName() );
		v.println( temp.toString() );
	}

	private void generatorGetVar(StringPrinter v, Field var) {
		Templet temp = Config.getTemplet("GETTER");
		temp.set( "COMMENT", var.getComment() );
		temp.set( "FUN_NAME", var.getFunName() );
		temp.set( "VARIATE_TYPE", var.getType() );
		temp.set( "VARIATE_NAME", var.getName() );
		v.println( temp.toString() );
	}

	private void generatorVar(StringPrinter v, Field var) {
		Templet temp = Config.getTemplet("FIELDS_PRIVATE");
		temp.set( "VARIATE_TYPE", var.getType() );
		temp.set( "VARIATE_NAME", var.getName() );
		v.println( temp.toString() );
	}

	public String toString(){
		return ret.toString();
	}


}
