package com.itmuch.gen.converter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.itmuch.gen.db.entity.Field;
import com.itmuch.gen.db.entity.Model;
import com.itmuch.gen.db.entity.impl.BehaviorModel;
import com.itmuch.gen.db.entity.impl.EntityModel;
import com.itmuch.gen.db.util.DBUtil;
import com.itmuch.gen.entity.Config;
import com.itmuch.gen.entity.ConfigDetail;
import com.itmuch.gen.freemarker.FreemarkerUtil;
import com.itmuch.gen.util.StringUtil;

public class DB2Entity {
    Properties prop = null;
    Config config = null;

    public DB2Entity(Properties prop, Config config) {
        this.prop = prop;
        this.config = config;
    }

    // 不同环境下结果不同, 不靠谱, 先顶上, 最好用getResource()来做
    public static final String WORKSPACE_PATH = System.getProperty("user.dir");

    public List<Field> convertDB2Fields(String tableName) throws Exception {
        Connection conn = DBUtil.getConnection(this.prop);

        DatabaseMetaData metadata = conn.getMetaData();
        ResultSet rs = metadata.getColumns("", "", tableName, null);
        // 实体中的属性, 对应表中的字段
        List<Field> fields = new ArrayList<Field>();
        while (rs.next()) {
            String name = rs.getString("COLUMN_NAME");
            String type = rs.getString("TYPE_NAME");
            String comment = rs.getString("REMARKS");
            String javaType = this.getJavaType(type);

            Field field = new Field();
            field.setFieldName(StringUtil.camelName(name));
            field.setFieldType(javaType);
            field.setComment(comment);
            fields.add(field);
        }

        //        this.genDomain(modelClassName, modelPackageName, modelPath);
        //
        //        this.genMapper(config, tableName, modelClassName);

        return fields;
    }

    public void entry(Properties prop, Config config) throws Exception {
        String tableName = this.prop.getProperty("tableName");

        EntityModel domain = this.genDomain(tableName);
        BehaviorModel mapper = this.genMapper(domain.getClassName() + "Mapper", domain);

        this.writeEnv(tableName, "model.ftl", domain.getPath(), "domain", domain);

        this.writeEnv(tableName, "mapper.ftl", mapper.getPath(), "mapper", mapper);
    }

    private EntityModel genDomain(String tableName) throws Exception {

        // 实体的类名
        String modelClassName = this.prop.getProperty("entityName");
        ConfigDetail modelConfig = this.config.getModelConfig();
        String modelTargetProject = modelConfig.getTargetProject();
        // 实体的包名
        String modelPackageName = modelConfig.getTargetPackage();

        String modelPath = this.genPath(modelTargetProject, modelPackageName);
        System.out.println("实体生成的路径:" + modelPath);

        EntityModel domain = new EntityModel();
        domain.setPackageName(modelPackageName);
        domain.setPath(modelPath);
        domain.setClassName(modelClassName);
        domain.setFields(this.convertDB2Fields(tableName));
        return domain;
    }

    private BehaviorModel genMapper(/*String tableName,*/String mapperClassName, EntityModel entityModel) throws IOException, Exception {
        // 处理mapper开始
        ConfigDetail mapperConfig = this.config.getMapperConfig();
        String mapperPackageName = mapperConfig.getTargetPackage();
        String mapperTargetProject = mapperConfig.getTargetProject();
        String mapperPath = this.genPath(mapperTargetProject, mapperPackageName);
        System.out.println("mapper生成的路径" + mapperPath);

        BehaviorModel mapper = new BehaviorModel();
        // String mapperClassName = modelClassName + "Mapper";
        mapper.setEntityModel(entityModel);
        mapper.setPackageName(mapperPackageName);
        mapper.setClassName(mapperClassName);
        List<String> list = new ArrayList<String>();
        list.add(entityModel.getPackageName() + "." + entityModel.getClassName());
        mapper.setImports(list);
        mapper.setPath(mapperPath);

        return mapper;
        // this.writeEnv(tableName, mapperPath, mapperClassName);
    }

    /**
     * 写到文件中
     * @param tableName
     * @param mapperPath
     * @param mapperClassName
     * @throws Exception
     */
    private void writeEnv(String tableName, String tempName, String mapperPath, String key, Model model) throws Exception {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put(key, model);
        // root.put("mapper", this.mapper);
        root.put("tableName", tableName);
        System.out.println("需要写到环境中的map:" + root);

        FreemarkerUtil.print(tempName, root, mapperPath, model.getClassName() + ".java");
    }

    /**
     * 获得模型的生成路径
     * @param config
     * @return
     * @throws IOException
     */
    private String genPath(String targetProject, String packageName) {

        String packagePath = packageName.replaceAll("\\.", "/");
        return WORKSPACE_PATH + "/" + targetProject + "/" + packagePath + "/";
    }

    public String getJavaType(String type) {
        type = type.toLowerCase();
        if ("char".equals(type) || "varchar".equals(type) || "nvarchar".equals(type)) {
            return "String";
        } else if ("int".equals(type)) {
            return "Integer";
        } else if ("bigint".equals(type)) {
            return "Long";
        } else if ("timestamp".equals(type) || "date".equals(type) || "datetime".equals(type)) {
            return "java.sql.Timestamp";
        } else if ("decimal".equals(type)) {
            return "Double";
        } else if ("image".equals(type)) {
            return "byte[]";
        } else if ("smallint".equals(type)) {
            return "int";
        } else if ("tinyint".equals(type)) {
            return "byte";
        }
        return null;
    }
}
