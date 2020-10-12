package x.javaplus.csv.util;

import java.text.MessageFormat;

public class CsvType {

	private String type;
	
	public CsvType( String type ){
		this.type = type.toLowerCase();
	}
	
	public String value(){ return type; }

	/**
	 *  定义 类型
	 * @return
	 */
	public String type() {
		if( type.equals( "string" ) )
			return "String";
		if( type.contains( "string;string" ) )
			return "String[]";
		return type;
	}

	/**
	 * 转换包装
	 * @param value  
	 * @return
	 */
	public String parse( String value ) {
		String ret = "{0}";
		if( type.equals("int") ){
			ret = "Integer.parseInt( StringUtil.convertNumberString( {0} ) )";
		} else if( type.equals("short") ){
			ret = "Short.parseShort( StringUtil.convertNumberString( {0} ) )";
		} else if( type.equals("byte") ){
			ret = "Byte.parseByte( StringUtil.convertNumberString( {0} ) )";
		} else if( type.equals("long") ){
			ret = "Long.parseLong( StringUtil.convertNumberString( {0} ) )";
		} else if( type.equals("float") ){
			ret = "Float.parseFloat( StringUtil.convertNumberString( {0} ) )";
		} else if( type.equals("string;string") ){
			ret = "{0}.split(\";\")";
		}
		return MessageFormat.format( ret, value );
	}
	
}
