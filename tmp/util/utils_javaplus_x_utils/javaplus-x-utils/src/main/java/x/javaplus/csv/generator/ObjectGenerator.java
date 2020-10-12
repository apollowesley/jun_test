package x.javaplus.csv.generator;

import java.util.List;

import x.javaplus.csv.util.Csv;
import x.javaplus.csv.util.CsvType;
import x.javaplus.string.StringPrinter;
import x.javaplus.string.StringUtil;
import x.javaplus.util.Util;
import x.javaplus.util.templet.Config;
import x.javaplus.util.templet.Templet;

public class ObjectGenerator {

	public ObjectGenerator(List<Csv> csvs, String filePath, String oPath, String oPackageName) {
		
		
		for( Csv csv : csvs ){
			
			String className 	= csv.getFileName().substring( 0, csv.getFileName().length() - 4 );
			className			= StringUtil.firstToUpper(className) + "Po";
			
			Templet temp 		= Config.getTemplet( "CSV_OBJECT" );
			temp.set( "PACKAGE_NAME", oPackageName );
			temp.set( "CLASS_NAME", className );
			
			List<String> names = csv.getNames();
			List<CsvType> types = csv.getTypes();
			
			temp.append( "FIELDS_PUBLIC_FINAL", generatorPublicVar( names, types ) );
			temp.append( "SET_CLONE", generatorSetVarClone( names ) );
			temp.append( "SET_VAR", generatorSetVar( names, types ) );
			
			String content = temp.toString().trim();
//			System.out.println( content );
			Util.File.write( oPath+"/"+className+".java", content );
		} 
		
	}

	private String generatorSetVarClone( List<String> names ) {
		StringPrinter print = new StringPrinter();
		Templet temp = Config.getTemplet( "SET_CLONE" );
		
		for( int i = 0; i < names.size(); i++ ){
			
			temp.set( "NAME", names.get(i) );
			
			if( i == names.size()-1 )
				print.println( temp.toString() );
			else
				print.print( temp.toString() );
			temp.clear();
		}
		
		return print.toString();
	}

	private String generatorPublicVar( List<String> names, List<CsvType> types ) {
		StringPrinter print = new StringPrinter();
		Templet temp = Config.getTemplet( "FIELDS_PUBLIC_FINAL" );
		
		for( int i = 0; i < names.size(); i++ ){
			
			temp.set( "NAME", names.get(i) );
			temp.set( "TYPE", types.get(i).type() );
			
			if( i == names.size()-1 )
				print.println( temp.toString() );
			else
				print.print( temp.toString() );
			temp.clear();
		}
		
		return print.toString();
	}

	private String generatorSetVar( List<String> names, List<CsvType> types ) {
		StringPrinter print = new StringPrinter();
		Templet temp = Config.getTemplet( "SET_VAR" );
		
		for( int i = 0; i < names.size(); i++ ){
		
			temp.set( "NAME", names.get(i) );
			//StringUtil.convertNumberString(
			String value = "data.get(\""+names.get(i)+"\")";
			temp.set( "TYPE_PARSE", types.get(i).parse( value ) );
			
			if( i == names.size()-1 )
				print.println( temp.toString() );
			else
				print.print( temp.toString() );
			temp.clear();
		}
		
		return print.toString();
	}

	

}
