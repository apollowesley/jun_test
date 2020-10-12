
package codeGenerate.mybatis.generater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import codeGenerate.mybatis.constants.DBType;
import codeGenerate.mybatis.constants.Jdbc4Types;
import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.mybaitsJdbcType.imp.BIGINT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.BLOB;
import codeGenerate.mybatis.mybaitsJdbcType.imp.CHAR;
import codeGenerate.mybatis.mybaitsJdbcType.imp.CLOB;
import codeGenerate.mybatis.mybaitsJdbcType.imp.DATE;
import codeGenerate.mybatis.mybaitsJdbcType.imp.DOUBLE;
import codeGenerate.mybatis.mybaitsJdbcType.imp.FLOAT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.INT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.LONGVARCHAR;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NCHAR;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NCLOB;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NUMERIC;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NVARCHAR;
import codeGenerate.mybatis.mybaitsJdbcType.imp.OTHER;
import codeGenerate.mybatis.mybaitsJdbcType.imp.SMALLINT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.TIME;
import codeGenerate.mybatis.mybaitsJdbcType.imp.TIMESTAMP;
import codeGenerate.mybatis.mybaitsJdbcType.imp.TINYINT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.VARCHAR;
import codeGenerate.mybatis.tempalte.TemPathAbstract;
import codeGenerate.mybatis.utils.StringUtils;
import codeGenerate.mybatis.vo.ConfigVo;
import codeGenerate.mybatis.vo.DbColumnVo;
import codeGenerate.mybatis.vo.ItemVo;
import codeGenerate.mybatis.vo.PackageColumnVo;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class AbstractGenerater {

	private static final String JAVA_LANG = "java.lang";

	private ConfigVo configVo;

	private Set<String> packageSet = new HashSet<String>();

	private static Map<Integer, JdbcTypeHandle> jdbcTypeHandleMap = new HashMap<Integer, JdbcTypeHandle>();

	static {
		//typeMap.put(Types.ARRAY,);
		jdbcTypeHandleMap.put(Types.BIGINT, new BIGINT());
		jdbcTypeHandleMap.put(Types.BINARY, new BLOB());
		//typeMap.put(Types.BIT, );
		jdbcTypeHandleMap.put(Types.BLOB, new BLOB());
		//typeMap.put(Types.BOOLEAN, );
		jdbcTypeHandleMap.put(Types.CHAR, new CHAR());
		jdbcTypeHandleMap.put(Types.CLOB, new CLOB());
		//typeMap.put(Types.DATALINK, );

		//typeMap.put(Types.DISTINCT, );
		jdbcTypeHandleMap.put(Types.DOUBLE, new DOUBLE());
		jdbcTypeHandleMap.put(Types.FLOAT, new FLOAT());
		jdbcTypeHandleMap.put(Types.INTEGER, new INT());
		//typeMap.put(Types.JAVA_OBJECT, );
		//		typeMap.put(Jdbc4Types.LONGNVARCHAR, );
		jdbcTypeHandleMap.put(Types.LONGVARBINARY, new BLOB());
		jdbcTypeHandleMap.put(Types.LONGVARCHAR, new LONGVARCHAR());

		jdbcTypeHandleMap.put(Types.OTHER, new OTHER());
		jdbcTypeHandleMap.put(Jdbc4Types.NCHAR, new NCHAR());
		jdbcTypeHandleMap.put(Jdbc4Types.NCLOB, new NCLOB());
		jdbcTypeHandleMap.put(Jdbc4Types.NVARCHAR, new NVARCHAR());
		//		typeMap.put(Types.NULL, );
		jdbcTypeHandleMap.put(Types.OTHER, new OTHER());
		jdbcTypeHandleMap.put(Types.REAL, new NUMERIC());
		//		typeMap.put(Types.REF, );
		jdbcTypeHandleMap.put(Types.SMALLINT, new SMALLINT());
		//		typeMap.put(Types.STRUCT, );
		jdbcTypeHandleMap.put(Types.DATE, new DATE());
		jdbcTypeHandleMap.put(Types.TIME, new TIME());
		jdbcTypeHandleMap.put(Types.TIMESTAMP, new TIMESTAMP());
		jdbcTypeHandleMap.put(Types.TINYINT, new TINYINT());
		jdbcTypeHandleMap.put(Types.VARBINARY, new BLOB());
		jdbcTypeHandleMap.put(Types.VARCHAR, new VARCHAR());
		jdbcTypeHandleMap.put(Types.NUMERIC, new NUMERIC());
		jdbcTypeHandleMap.put(Types.DECIMAL, new NUMERIC());

	}

	/**
	 * @description: 生成代码
	 * @creator: dw.fu
	 * @createDate: 2015年9月26日 
	 * @modifier:
	 * @modifiedDate:
	 * @throws IOException
	 * @throws TemplateException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void generateCode() throws IOException, TemplateException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println(">>>>>>>>>>>>>>>>>>>表名[" + configVo.getTblName() + "]生成开始<<<<<<<<<<<<<<<<<<<");
		System.out.println(configVo);
		List<PackageColumnVo> packageColumnVoList = createPackageColumnVoList();
		Map<String, Object> root = new HashMap<String, Object>();
		//定义模板常量
		root.put("columnList", packageColumnVoList);
		root.put("config", configVo);
		root.put("packageSet", packageSet);
		String simpleBeanName = configVo.getBeanName().substring(0, 1).toLowerCase() + configVo.getBeanName().substring(1);
		//避免手动输入bean类首个字母小写
		root.put("beanName", configVo.getBeanName().substring(0, 1).toUpperCase() + configVo.getBeanName().substring(1));
		root.put("simpleBeanName", simpleBeanName);
		root.put("beanId", simpleBeanName + "Id");
		root.put("beanShowVo", configVo.getBeanName() + "ShowVo");
		root.put("beanQueryVo", configVo.getBeanName() + "QueryVo");
		root.put("beanVo", configVo.getBeanName() + "Vo");
		root.put("beanQo", configVo.getBeanName() + "Qo");		
		root.put("ctx", "${ctx}");
		Configuration cfg = new Configuration();
		cfg.setClassicCompatible(true);
		cfg.setDirectoryForTemplateLoading(new File(configVo.getTemplateDirectory()));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		//根据模版路径生成相应的代码
		Class<?> tempateClass = Class.forName(configVo.getTemplate());
		TemPathAbstract temPathAbstract = (TemPathAbstract) tempateClass.newInstance();
		Map<String, String> tplFileMap = temPathAbstract.getPathMap(configVo);
		for (Map.Entry<String, String> entry : tplFileMap.entrySet()) {
			String tempalteFile = entry.getKey();
			String generateFile = entry.getValue();
			Template temp = cfg.getTemplate(tempalteFile);
			File file = new File(generateFile);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			OutputStream writer = new FileOutputStream(new File(generateFile));
			Writer out = new OutputStreamWriter(writer);
			temp.process(root, out);
			writer.flush();
			writer.close();
			System.out.println("模板:" + tempalteFile + "==>>生成文件==>>" + generateFile);
		}

		System.out.println(">>>>>>>>>>>>>>>>>>>表名[" + configVo.getTblName() + "]生成结束<<<<<<<<<<<<<<<<<<<");
		System.out.println("\n");
	}

	private List<PackageColumnVo> createPackageColumnVoList() {
		List<PackageColumnVo> list = new ArrayList<PackageColumnVo>();
		List<DbColumnVo> columnList = readDbColumnList();
		for (DbColumnVo column : columnList) {
			list.add(dbColumnToPackageColumn(column));
		}
		return list;
	}

	/**
	 * @description: 读取数据库列
	 * @creator: dw-fu1
	 * @modifier:
	 * @modifiedDate:
	 * @return
	 */
	private List<DbColumnVo> readDbColumnList() {
		List<DbColumnVo> dbColumnVoList = new ArrayList<DbColumnVo>();
		Connection connection = null;
		try {
			connection = getConnection();
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			ResultSet rs = databaseMetaData.getColumns(configVo.getDbName(), null, configVo.getTblName(), null);
			DatabaseMetaData meta = connection.getMetaData();
			while (rs.next()) {
				DbColumnVo vo = new DbColumnVo();
				vo.setColumnName(rs.getString("COLUMN_NAME"));
				vo.setColumnComment(rs.getString("REMARKS"));
				vo.setColumnTypeName(rs.getString("TYPE_NAME"));
				vo.setColumnType(rs.getInt("DATA_TYPE"));
				vo.setPrecision(rs.getInt("COLUMN_SIZE"));
				vo.setScale(rs.getInt("DECIMAL_DIGITS"));
				dbColumnVoList.add(vo);
			}
			//sql server2005,sql server无法读取到注释，需要数据库做查询，重新设置
			if (DBType.SQLSERVER2005.equals(configVo.getDbType()) || DBType.SQLSERVER.equals(configVo.getDbType())) {
				Map<String, String> columnCommontMap = getColumnCommont();
				for (DbColumnVo vo : dbColumnVoList) {
					vo.setColumnComment(columnCommontMap.get(vo.getColumnName()));
				}
			}

			System.out.println(">>>>>>>>>>>>>>>>>>>数据库信息");
			System.out.println("databaseProductName:" + meta.getDatabaseProductName());
			System.out.println("databaseProductVersion:" + meta.getDatabaseProductVersion());
			System.out.println(">>>>>>>>>>>>>>>>>>>读取列信息开始");
			for (DbColumnVo dbColumnVo : dbColumnVoList) {
				System.out.println(dbColumnVo);
			}
			System.out.println(">>>>>>>>>>>>>>>>>>>读取列信息完毕");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return dbColumnVoList;
	}

	/**
	 * @description: 将数据列名转换转换成java
	 * @creator: dw.fu
	 * @createDate: 2015年9月26日 
	 * @modifier:
	 * @modifiedDate:
	 * @param dbColumnVo
	 * @return
	 */
	private PackageColumnVo dbColumnToPackageColumn(DbColumnVo dbColumnVo) {
		PackageColumnVo packageColumnVo = new PackageColumnVo();
		packageColumnVo.setJavaPropertyName(dbColumnNameNameToJavaPropertyName(dbColumnVo.getColumnName()));
		packageColumnVo.setDbColumnName(dbColumnVo.getColumnName());
		packageColumnVo.setDbColumnComment(dbColumnVo.getColumnComment());
		packageColumnVo.setDbColumnPrecision(dbColumnVo.getPrecision());
		packageColumnVo.setDbColumnScale(dbColumnVo.getScale());
		JdbcTypeHandle JdbcTypeHandle = registerTypeHandle().get(dbColumnVo.getColumnTypeName().toUpperCase());
		if (JdbcTypeHandle == null) {
			JdbcTypeHandle = jdbcTypeHandleMap.get(dbColumnVo.getColumnType());
			if (JdbcTypeHandle == null) {
				throw new RuntimeException("该类型处理器不存在,类型为" + dbColumnVo.getColumnTypeName() + ",字段：" + dbColumnVo.getColumnName());
			} else {
				System.out.println("jdbcType:" + dbColumnVo.getColumnTypeName() + "专用类型定义不存在，使用公用定义jdbcType:" + JdbcTypeHandle.getClass().getName());
			}
		}
		ItemVo ItemVo = JdbcTypeHandle.handle(packageColumnVo);
		packageColumnVo.setMybatisJavaType(ItemVo.getMybatisJavaType());
		packageColumnVo.setMybatisJdbcType(ItemVo.getMybatisJdbcType());
		packageColumnVo.setMybatisTypeHandler(ItemVo.getMybatisTypeHandler());
		String javaPropertyType = ItemVo.getJavaPropertyType();
		if (ItemVo.getJavaPropertyType().contains(".")) {
			int index = ItemVo.getJavaPropertyType().lastIndexOf('.');
			packageColumnVo.setJavaPropertyType(javaPropertyType.substring(index + 1));
			packageColumnVo.setJavaPropertyTypePackage(javaPropertyType.substring(0, index));
			//基本类型无需导入包
			if (!JAVA_LANG.equals(packageColumnVo.getJavaPropertyTypePackage())) {
				packageSet.add(javaPropertyType);
			}
		} else {
			packageColumnVo.setJavaPropertyType(ItemVo.getJavaPropertyType());
		}
		return packageColumnVo;
	}

	/**
	 * @description: 数据库底层的列名转换成属性名称，默认峰驼命名
	 * @creator: dw.fu
	 * @createDate: 2015年9月26日 
	 * @modifier:
	 * @modifiedDate:
	 * @param dbColumnName
	 * @return
	 */
	private String dbColumnNameNameToJavaPropertyName(String dbColumnName) {
		String[] names = dbColumnName.split("_");
		StringBuffer sb = new StringBuffer();
		sb.append(names[0].toLowerCase());
		for (int i = 1; i < names.length; i++) {
			if (StringUtils.isNotBlank(names[i])) {
				sb.append(names[i].substring(0, 1).toUpperCase());
				sb.append(names[i].substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}

	/**
	 * @description: 获得列名的注释(由于java jdbc获取不了，需要自已写sql查询，返回key为列名，value为注释的map)
	 * @creator: dw.fu
	 * @createDate: 2015年9月26日 
	 * @modifier:
	 * @modifiedDate:
	 * @return
	 */
	public abstract Map<String, String> getColumnCommont();

	/**
	 * @description: 返回类型处理器
	 * @creator: dw-fu1
	 * @createDate: 2016年1月14日 
	 * @modifier:
	 * @modifiedDate:
	 * @return
	 */
	public abstract Map<String, JdbcTypeHandle> registerTypeHandle();

	public final Connection getConnection() {
		try {
			Class.forName(configVo.getDriverName());
			Properties props = new Properties();
			//获取Oracle元数据 REMARKS信息 
			props.put("remarksReporting", "true");
			//获取MySQL元数据 REMARKS信息    
			props.setProperty("useInformationSchema", "true");
			props.put("user", configVo.getUsername());
			props.put("password", configVo.getPassword());
			return DriverManager.getConnection(configVo.getUrl(), props);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ConfigVo getConfigVo() {
		return configVo;
	}

	public void setConfigVo(ConfigVo configVo) {
		this.configVo = configVo;
	}
}
