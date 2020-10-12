package net.domor.cg;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.domor.cg.annotation.PrimaryKey;
import net.domor.cg.annotation.Table;
import net.domor.cg.util.FileUtils;
import net.domor.cg.util.FolderUtils;
import net.domor.cg.util.TemplateUtils;
import net.domor.core.Entity;

import org.apache.commons.lang3.StringUtils;

public class CodeGenerator {

	private String projectPath;

	// 实体类名
	private List<String> entityNames = new ArrayList<String>();

	private List<Map<String, Object>> entityMaps = new ArrayList<Map<String, Object>>();

	Map<String, String> fieldTypeMap = new HashMap<String, String>();

	public CodeGenerator(String projectPath) {
		this.projectPath = projectPath;
		init();
	}

	public CodeGenerator(String projectPath, String entityNames) {
		this.projectPath = projectPath;
		setEntityNames(entityNames);
		init();
	}

	public void generate() {
		createDaoCode();
		createServiceCode();
		// createAOCode();
		// createSpringMVCCode();
	}

	private void init() {
		// 如果entityNames中不含实体类，则在projectPath路径对应的目录的子目录下查找实体类
		if (entityNames.size() == 0) {
			findEntity(new File(this.projectPath));
		}

		getEntityField();
	}

	public void setEntityNames(List<String> entityNames) {
		this.entityNames = entityNames;
	}

	public void setEntityNames(String entityNames) {
		this.entityNames = Arrays.asList(entityNames.split(","));
	}

	/**
	 * 生成Dao类及Mapper文件
	 */
	@SuppressWarnings("unchecked")
	public void createDaoCode() {
		if (!this.entityMaps.isEmpty()) {
			for (Map<String, Object> entityMap : entityMaps) {
				// 实体类包名
				String packageName = (String) entityMap.get("package");

				// dao类包名
				String daoPackageName = packageName.replaceAll("entity", "dao");

				// dao类包目录
				String daoPackageDirName = daoPackageName.replaceAll("\\.", "/");

				FolderUtils.mkdirs(this.projectPath + daoPackageDirName);

				String daoClassName = daoPackageName + "." + entityMap.get("entityName") + "Dao";

				entityMap.put("daoClassName", daoClassName);
				entityMap.put("daoPackageName", daoPackageName);

				String daoFileName = this.projectPath + daoPackageDirName + File.separator + entityMap.get("entityName") + "Dao.java";

				String daoContent = TemplateUtils.daoTemplate(entityMap);

				FileUtils.create(daoFileName, daoContent);

				System.out.println("###########" + daoFileName + "###########生成完成");

				String mybatisFileName = this.projectPath + daoPackageDirName + File.separator + entityMap.get("entityName") + ".xml";
				String mybatisContent = TemplateUtils.mybatisTemplate(entityMap);
				FileUtils.create(mybatisFileName, mybatisContent);

				System.out.println("###########" + mybatisFileName + "###########生成完成");

				String mysqlFileName = this.projectPath + daoPackageDirName + File.separator + entityMap.get("entityName") + ".sql";
				StringBuilder mc = new StringBuilder();

				mc.append("CREATE TABLE ").append(entityMap.get("tableName")).append("\r\n").append("(").append("\r\n");

				mc.append("\t").append(entityMap.get("primaryKey"));
				mc.append(mysqlPrimaryCode((String) this.fieldTypeMap.get(entityMap.get("entityName") + "|||" + entityMap.get("primaryKey"))));
				mc.append(" AUTO_INCREMENT").append(",\r\n");

				List<String> fieldlist = (List<String>) entityMap.get("fields");

				for (String field : fieldlist) {
					mc.append("\t").append(field).append(mysqlFieldCode((String) this.fieldTypeMap.get(entityMap.get("entityName") + "|||" + field))).append(",\r\n");
				}

				mc.append("\t").append("PRIMARY KEY (").append(entityMap.get("primaryKey")).append(")\r\n");
				mc.append(")ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=gbk;");

				FileUtils.create(mysqlFileName, mc.toString());

				System.out.println("###########" + mysqlFileName + "###########生成完成");
			}
		}
	}

	public void createServiceCode() {
		if (!this.entityMaps.isEmpty()) {
			for (Map<String, Object> entity : this.entityMaps) {
				String packageName = (String) entity.get("package");
				String servicePackageName = packageName.replaceAll("entity", "service");
				String servicePackageDirName = servicePackageName.replaceAll("\\.", "/");
				FolderUtils.mkdirs(this.projectPath + servicePackageDirName);

				String serviceClassName = servicePackageName + "." + entity.get("entityName") + "Service";
				entity.put("serviceClassName", serviceClassName);
				entity.put("servicePackageName", servicePackageName);

				String serviceFileName = this.projectPath + servicePackageDirName + File.separator + entity.get("entityName") + "Service.java";
				String serviceContent = TemplateUtils.serviceTemplate(entity);
				FileUtils.create(serviceFileName, serviceContent);

				System.out.println("###########" + serviceFileName + "###########生成完成");

				String serviceImplFileName = this.projectPath + servicePackageDirName + File.separator + entity.get("entityName") + "ServiceImpl.java";
				String serviceImplContent = TemplateUtils.serviceImplTemplate(entity);
				FileUtils.create(serviceImplFileName, serviceImplContent);

				System.out.println("###########" + serviceImplFileName + "###########生成完成");
			}
		}
	}

	// public void createAOCode() {
	// if (!this.entityMaps.isEmpty()) {
	// for (Map entity : this.entityMaps) {
	// String packageName = (String) entity.get("package");
	// String aoPackageName = packageName.replaceAll("entity", "ao");
	// String aoPackageDirName = aoPackageName.replaceAll("\\.", "/");
	// FolderUtils.mkdirs(this.projectPath + aoPackageDirName);
	//
	// String aoClassName = aoPackageName + "." + entity.get("entityName") +
	// "AO";
	// entity.put("aoClassName", aoClassName);
	// entity.put("aoPackageName", aoPackageName);
	//
	// String aoFileName = this.projectPath + aoPackageDirName + File.separator
	// + entity.get("entityName") + "AO.java";
	// String aoContent = TemplateUtils.aoTemplate(entity);
	// FileUtils.create(aoFileName, aoContent);
	//
	// System.out.println("###########" + aoFileName + "###########生成完成");
	//
	// String aoImplFileName = this.projectPath + aoPackageDirName +
	// File.separator + entity.get("entityName") + "AOImpl.java";
	// String aoImplContent = TemplateUtils.aoImplTemplate(entity);
	// FileUtils.create(aoImplFileName, aoImplContent);
	//
	// System.out.println("###########" + aoImplFileName + "###########生成完成");
	// }
	// }
	// }
	//
	// public void createSpringMVCCode() {
	// if (!this.entityMap.isEmpty()) {
	// for (Map entity : this.entityMap) {
	// String packageName = (String) entity.get("package");
	// String actionPackageName = packageName.replaceAll("entity", "action");
	// String actionPackageDirName = actionPackageName.replaceAll("\\.", "/");
	// FolderUtils.mkdirs(this.projectPath + actionPackageDirName);
	//
	// String actionClassName = actionPackageName + "." +
	// entity.get("entityName") + "Action";
	// entity.put("actionClassName", actionClassName);
	// entity.put("actionPackageName", actionPackageName);
	//
	// String actionFileName = this.projectPath + actionPackageDirName +
	// File.separator + entity.get("entityName") + "Action.java";
	// String actionContent = TemplateUtils.springMVCTemplate(entity);
	// FileUtils.create(actionFileName, actionContent);
	//
	// System.out.println("###########" + actionFileName + "###########生成完成");
	// }
	// }
	// }

	private String mysqlFieldCode(String fieldType) {
		String s = "";
		if (("int".equals(fieldType)) || ("java.lang.Integer".equals(fieldType))) {
			s = " INT(11) NOT NULL DEFAULT 0";
		} else if (("long".equals(fieldType)) || ("java.lang.Long".equals(fieldType))) {
			s = " BIGINT(20) NOT NULL DEFAULT 0";
		} else if ("java.lang.String".equals(fieldType)) {
			s = " VARCHAR(20) NOT NULL DEFAULT ''";
		} else if (("boolean".equals(fieldType)) || ("java.lang.Boolean".equals(fieldType))) {
			s = " TINYINT(1) NOT NULL DEFAULT 0";
		} else if (("float".equals(fieldType)) || ("java.lang.Float".equals(fieldType))) {
			s = " DOUBLE(11,2) NOT NULL DEFAULT '0'";
		} else if (("double".equals(fieldType)) || ("java.lang.Double".equals(fieldType))) {
			s = " DOUBLE(20,4) NOT NULL DEFAULT '0'";
		} else if ("java.util.Date".equals(fieldType)) {
			s = " DATETIME";
		}

		return s;
	}

	private String mysqlPrimaryCode(String fieldType) {
		String s = "";
		if (("int".equals(fieldType)) || ("java.lang.Integer".equals(fieldType))) {
			s = " INT(11) NOT NULL";
		} else if (("long".equals(fieldType)) || ("java.lang.Long".equals(fieldType))) {
			s = " BIGINT(20) NOT NULL";
		} else if ("java.lang.String".equals(fieldType)) {
			s = " VARCHAR(20) NOT NULL";
		} else if (("boolean".equals(fieldType)) || ("java.lang.Boolean".equals(fieldType))) {
			s = " TINYINT(1) NOT NULL";
		} else if (("float".equals(fieldType)) || ("java.lang.Float".equals(fieldType))) {
			s = " DOUBLE(11,2) NOT NULL";
		} else if (("double".equals(fieldType)) || ("java.lang.Double".equals(fieldType))) {
			s = " DOUBLE(20,4) NOT NULL";
		}

		return s;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getEntityField() {
		for (String entity : this.entityNames) {
			try {
				Class c = Thread.currentThread().getContextClassLoader().loadClass(entity);
				if (!(c.newInstance() instanceof Entity)) {
					continue;
				}

				Map<String, Object> entityMap = new HashMap<String, Object>();
				entityMap.put("name", c.getName());
				entityMap.put("entityName", c.getSimpleName());
				entityMap.put("lowerEntityName", c.getSimpleName().toLowerCase());
				entityMap.put("package", c.getPackage().getName());
				entityMap.put("basePackage", c.getPackage().getName().replaceAll("entity", ""));

				Table t = (Table) c.getAnnotation(Table.class);
				if (t != null) {
					entityMap.put("tableName", t.value());
				} else {
					entityMap.put("tableName", entityMap.get("entityName"));
				}

				Field[] fields = c.getDeclaredFields();
				if (fields != null && fields.length > 0) {
					List<String> fieldNames = new ArrayList<String>();
					for (Field field : fields) {
						if (("serialVersionUID".equals(field.getName())) || (field.isEnumConstant()) || (field.isSynthetic())) {
							continue;
						}
						PrimaryKey pk = (PrimaryKey) field.getAnnotation(PrimaryKey.class);
						if (pk != null) {
							entityMap.put("primaryKey", field.getName());
							entityMap.put("type", field.getType().getName());
						} else {
							fieldNames.add(field.getName());
						}
						this.fieldTypeMap.put(c.getSimpleName() + "|||" + field.getName(), field.getType().getName());
					}
					entityMap.put("fields", fieldNames);
				}

				if (!entityMap.containsKey("primaryKey")) {
					continue;
				}
				this.entityMaps.add(entityMap);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查找目录下的所有实体类
	 */
	private void findEntity(File parentFile) {
		try {
			String[] files = parentFile.list();
			for (String fileName : files) {
				fileName = parentFile.getAbsolutePath() + File.separator + fileName;
				File tempFile = new File(fileName);
				if (tempFile.isDirectory()) {
					findEntity(tempFile);
				} else {
					if (tempFile.getAbsolutePath().indexOf("entity") == -1 || !tempFile.getAbsolutePath().endsWith(".java")) {
						continue;
					}

					// 包名以中有entity并且文件后缀为.java
					String javaFileName = StringUtils.substringAfterLast(tempFile.getAbsolutePath(), this.projectPath);
					javaFileName = javaFileName.substring(0, javaFileName.length() - 5);
					javaFileName = javaFileName.replaceAll("\\\\", ".");
					this.entityNames.add(javaFileName);
					System.out.println("####################找到实体类[" + javaFileName + "]####################");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
