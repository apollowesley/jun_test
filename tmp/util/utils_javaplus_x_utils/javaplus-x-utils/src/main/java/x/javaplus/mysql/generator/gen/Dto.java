package x.javaplus.mysql.generator.gen;

import japa.parser.JavaParser;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import x.javaplus.collections.Lists;
import x.javaplus.java.JavaFile;
import x.javaplus.java.JavaFileImpl;
import x.javaplus.util.Util.Collection;
import x.javaplus.util.templet.Field;

/**
 * 数据库 传输层对象
 * 
 * @author deng
 * 
 */
public class Dto {

	private static final String DTO_TAIL = "Dto";
	private JavaFile file;

	/**
	 * @param file
	 *            某个Dto的java文件
	 */
	public Dto(File file) {
		try {
			this.file = new JavaFileImpl(JavaParser.parse(file));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取变量
	 * @param annotationName
	 * @return
	 */
	public List<Field> getVariablesAll() {
		return file.getMethods();
	}
	/**
	 * 克隆一份变量
	 * @return
	 */
	public List<Field> getVariablesAllClone() {
		if( file.getMethods() == null )
			return null;
		List<Field> ret = Lists.newArrayList();
		for( Field field : file.getMethods() ){
			ret.add( new Field( field ) );
		}
		return ret;
	}
	
	public List<Field> getAnnotations(String annotationName) {
		List<AnnotationExpr> as = file.getType().getAnnotations();
		as = Collection.nullToEmpty(as);
		Iterator<AnnotationExpr> it = as.iterator();
		while (it.hasNext()) {
			AnnotationExpr a = it.next();
			if (!a.getName().getName().equals(annotationName)) {
				it.remove();
			}
		}
		return build(as);
	}

	private List<Field> build(List<AnnotationExpr> as) {
		List<Field> ls = Lists.newArrayList();
		for (AnnotationExpr a : as) {
			if (a instanceof SingleMemberAnnotationExpr) {
				SingleMemberAnnotationExpr aa = (SingleMemberAnnotationExpr) a;
				Expression mv = aa.getMemberValue();
				ls.add(new Field(a.getName().getName(), (mv + "").replaceAll("\"", "")));
			}
		}
		return ls;
	}

	public String getDaoClassName() {
		return getSimpleClassName() + "Dao";
	}

	public String getDtoClassName() {
		return getSimpleClassName() + DTO_TAIL;
	}

	/**
	 * 获取类名
	 * @return
	 */
	public String getSimpleClassName() {
		return file.getClassSimpleName();
	}

	/**
	 * 转换为Dto名字
	 * 
	 * @param type
	 * @return
	 */
	public static String toDtoName(String type) {
		return type + DTO_TAIL;
	}

}
