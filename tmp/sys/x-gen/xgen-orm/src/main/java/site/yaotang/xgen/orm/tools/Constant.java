package site.yaotang.xgen.orm.tools;

import java.util.HashMap;
import java.util.Map;

import site.yaotang.xgen.orm.mapping.Table;

/**
 * 存储XML文件信息
 * @author hyt
 * @date 2017年12月30日
 */
public class Constant {

	/** 数据库映射 */
	public static Map<String, String> DBMAP = new HashMap<String, String>();

	/** 表映射 */
	public static Map<String, Table> TABLEMAP = new HashMap<String, Table>();

	private static final String XGENORM_DATASOURCE_XML = "/xgenorm.datasource.xml";
	static {
		XMLFactory.readDBInfo(Constant.class.getResource(XGENORM_DATASOURCE_XML).getFile());
		XMLFactory.readTableInfo(Constant.class.getResource(XGENORM_DATASOURCE_XML).getFile());
	}
}
