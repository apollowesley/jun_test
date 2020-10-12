package x.javaplus.util.templet;

import x.javaplus.string.StringUtil;
import japa.parser.ast.Comment;
import japa.parser.ast.body.MethodDeclaration;

public class Field {

	private String type;
	private String typeName;

	// 分解的名字
	private String name;
	private String funName;
	
	private String comment;
	
	public Field(String name, String value) {
		this.name = name;
		this.type = value;
	}

	public Field( Field field ) {
		this.type 		= field.type;
		this.typeName 	= field.typeName;
		this.name 		= field.name;
		this.funName 	= field.funName;
		this.comment 	= field.comment;
	}
	
	/**
	 * 
	 * @param body
	 */
	public Field( MethodDeclaration body ) {
		
		//
		name		= body.getName();
		type		= body.getType().toString();
		if( type.equals("void") )
			throw new RuntimeException( "数据类型不能为 void at name=" + name );
		Comment x 	= body.getComment();
		comment		= x == null ? "无" : x.getContent();
		
		////
		type		= changeToFunname( type );
		funName		= StringUtil.firstToUpper(name);
		
		typeName	= StringUtil.firstToUpper(type);
		typeName	= typeName.replaceAll( "\\[]", "s" );
		
		type		= changeToCalss( type );
	}

	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getFunName() {
		return funName;
	}
	public String getTypeName() {
		return typeName;
	}
	public String getComment() {
		return comment;
	}
	
	private String changeToFunname( String type ) {
		if( type.equals( "Long" ) )
			return "long";
		if( type.equals( "Double" ) )
			return "double";
		if( type.equals( "Integer" ) )
			return "int";
		if( type.equals( "Float" ) )
			return "float";
		if( type.equals( "Short" ) )
			return "short";
		if( type.equals( "Byte" ) )
			return "byte";
		return type;
	}
	
	private String changeToCalss( String type ) {
		if( type.equals( "long" ) )
			return "Long";
		if( type.equals( "double" ) )
			return "Double";
		if( type.equals( "int" ) )
			return "Integer";
		if( type.equals( "float" ) )
			return "Float";
		if( type.equals( "short" ) )
			return "Short";
		if( type.equals( "byte" ) )
			return "Byte";
		
		return type;
	}

	public String sql() {
		return name+" "+sqlType()+" NULL COMMENT '"+getComment().trim()+"'" + (name.equalsIgnoreCase("id") ? " PRIMARY KEY" : "");
	}

	public String sqlType() {
		if( type.equals( "Long" ) )
			return "bigint(20)";
		if( type.equals( "Double" ) )
			return "double(20,10)";
		if( type.equals( "Integer" ) )
			return "int(11)";
		if( type.equals( "Float" ) )
			return "float(11,2)";
		if( type.equals( "Short" ) )
			return "smallint(6)";
		if( type.equals( "Byte" ) )
			return "tinyint(3)";
		if( type.equals( "String" ) )
			return "varchar(20)";
		if( type.equals( "byte[]" ) )
			return "blob";
		return "varchar(50)";
	}

	
	
}
