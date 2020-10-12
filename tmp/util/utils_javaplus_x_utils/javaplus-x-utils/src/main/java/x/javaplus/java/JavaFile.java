package x.javaplus.java;


import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.TypeDeclaration;

import java.util.List;

import x.javaplus.util.templet.Field;



public interface JavaFile {

	/**
	 * 类
	 * @return
	 */
	TypeDeclaration getType();

	/**
	 * 包
	 * @return
	 */
	String getPackage();

	/**
	 * 变量列表
	 * @return
	 */
	List<Field> getMethods();

	/**
	 * 类名
	 * @return
	 */
	String getClassSimpleName();

	/**
	 * 完整类名 包含了包路径
	 * @return
	 */
	String getClassFullName();

	List<ImportDeclaration> getImports();
}
