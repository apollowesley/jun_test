package cn.uncode.dal.generator.support;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BeanUtil {

	public BeanUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 创建JavaBean文件
	 * 
	 * @param tbName
	 * @param collist
	 * @param infoMap
	 * @return
	 */
	public String createBean(String tbName, List<Map<String, String>> collist, Map<String, String> infoMap) {

		StringBuilder statics = new StringBuilder();
		StringBuilder fields = new StringBuilder();
		StringBuilder methods = new StringBuilder();

		StringBuilder classInfo = new StringBuilder("/**\n");
		statics.append("\t").append("public final static String TABLE_NAME = \"").append(tbName).append("\";").append("\n");
		for (Map<String, String> colmap : collist) {
			String field = colmap.get("filed").toString();
			//全小写的变量名称
			String srcField = field.toLowerCase();
			//ksudi定制，id,version,createtime使用父类的，此处无需生成
			if("id".equals(srcField) || "version".equals(srcField) || "createtime".equals(srcField) ){
				continue;
			}
			String remarks = "";
			if (colmap.get("remarks") != null) {
				remarks = colmap.get("remarks").toString();
			}
			// T1.DATA_PRECISION,T1.DATA_SCALE
			int precision = 0;
			if (colmap.get("data_precision") != null) {
				precision = Integer.parseInt(colmap.get("data_precision"));
			}
			int scale = 0;
			if (colmap.get("data_scale") != null) {
				scale = Integer.parseInt(colmap.get("data_scale"));
			}
			String type = typeTrans(colmap.get("type").toString(), precision, scale);
			statics.append(getStaticStr(srcField));
			fields.append(getFieldStr(srcField, srcField, type, remarks));
			methods.append(getMethodStr(srcField, type));

		}
		classInfo.append(" * 数据库实体类,此类由Uncode自动生成\n");
		classInfo.append(" * @author uncode\n");
		classInfo.append(" * @date").append(new SimpleDateFormat(" yyyy-MM-dd").format(new Date())).append("\n");
		classInfo.append(" */\n");
		classInfo.append("public class ").append(StringUtil.upperFirestChar(tbName)).append(" extends ");
		if (infoMap.get("dtoBasePath") != null && !infoMap.get("dtoBasePath").toString().equals("")) {
			String path = infoMap.get("dtoBasePath");
			classInfo.append(path.substring(path.lastIndexOf(".")+1));
		}else{
			classInfo.append("BaseDTO");
		}
		classInfo.append("");
		classInfo.append(" {\n");
		classInfo.append(statics);
		classInfo.append("\n");
		classInfo.append(fields);
		classInfo.append("\n");
		classInfo.append(methods);
		classInfo.append("\n");
		classInfo.append("}");

		File file = new File(infoMap.get("catName") + File.separator, StringUtil.upperFirestChar(tbName) + ".java");

		try {
			StringBuffer strBuf = new StringBuffer("");
			if (infoMap.get("packName") != null && !infoMap.get("packName").toString().equals("")) {
				strBuf.append("package " + infoMap.get("packName").toString() + ";\n\n");
			}
			if (classInfo.indexOf("Date") > 0) {
				strBuf.append("import java.util.Date;\n");
			}
			if (infoMap.get("dtoBasePath") != null && !infoMap.get("dtoBasePath").toString().equals("")) {
				strBuf.append("import ").append(infoMap.get("dtoBasePath")).append(";\n");
			}else{
				strBuf.append("import cn.uncode.dal.core.BaseDTO;\n");
			}
			strBuf.append(classInfo);

			ReadWriteFileWithEncode.write(file.getAbsolutePath(), strBuf.toString(), "UTF-8");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 数据库字段类型与JAVA类型转换
	 * 
	 * @param type
	 * @return
	 */
	public String typeTrans(String type, int precision, int scale) {
		type = type.toUpperCase();
		String result = null;
		if (type.contains("NUMBER")) {
			if (scale > 0) {
				result = "Double";
			} else {
				if (precision > 8) {
					result = "Long";
				} else {
					result = "Integer";
				}
			}
		} else if (type.contains("BIGINT")) {
			result = "Long";
		}  else if (type.contains("TINYINT")) {
			result = "Boolean";
		} else if (type.contains("INT") || type.contains("INTEGER")) {
			result = "Integer";
		} else if (type.contains("DATETIME") || type.contains("TIMESTAMP") || type.contains("DATE") || type.contains("TIME")) {
			result = "Date";
		}else if (type.contains("VARCHAR") || // || type.contains("DATE")|| type.contains("TIME") ||
												// type.contains("TIMESTAMP")
				type.contains("VARCHAR") || type.contains("TEXT") || type.contains("ENUM") || type.contains("SET")) {
			result = "String";
		} else if (type.contains("BINARY") || type.contains("BLOB")) {
			result = "byte[]";
		} else if (type.contains("DECIMAL")) {
			// BigDecimal
			result = "Double";
		} else if (type.contains("BIT")) {
			result = "Boolean";
		} else {
			result = "String";
		}
		// System.out.println("-------------- src= " + type + ",\t---- new= " + result);
		return result;
	}

	// T1.DATA_PRECISION,T1.DATA_SCALE

	/**
	 * 获取方法字符串
	 * 
	 * @param field
	 * @param type
	 * @return
	 */
	private String getMethodStr(String field, String type) {
		StringBuilder get = new StringBuilder("\tpublic ");
		get.append(type).append(" ");
		if (type.equals("boolean")) {
			get.append("is");
			get.append(StringUtil.upperFirestChar(field));
		} else {
			get.append("get");
			get.append(StringUtil.upperFirestChar(field));
		}
		get.append("(){").append("\n\t\treturn this.").append(field).append(";\n\t}\n");
		StringBuilder set = new StringBuilder("\tpublic void ");

		set.append("set");
		set.append(StringUtil.upperFirestChar(field));

		set.append("(").append(type).append(" ").append(field).append("){\n\t\tthis.").append(field).append(" = ").append(field).append(";\n\t}\n");
		get.append(set);
		return get.toString();
	}
	/**
	 * 
	 * @param field
	 * @param type
	 * @param remarks
	 * @return
	 */
	private String getFieldStr(String srcField, String field, String type, String remarks) {
		StringBuilder sb = new StringBuilder();
		sb.append("\t/**\n");
		sb.append("\t * ").append(remarks).append("\n");
		sb.append("\t */\n");
		sb.append("\t").append("private ").append(type).append(" ").append(field).append(";");
		sb.append("\n");
		return sb.toString();
	}
	/**
	 * 
	 * @param field
	 * @return
	 */
	private String getStaticStr(String srcField) {
		String staticStr = srcField;
		staticStr = staticStr.toUpperCase();
		StringBuilder sb = new StringBuilder();
		sb.append("\t").append("public final static String ").append(staticStr).append(" = \"").append(srcField).append("\";").append("\n");
		return sb.toString();
	}
}
