package club.lis.generator;

import club.lis.config.ConfigUtil;
import club.lis.mapper.MysqlMapper;
import club.lis.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: lishun
 * @Date: 2019/4/1 14:23
 * @Description:
 */
public class Generator {

    private final static Logger logger = LoggerFactory.getLogger(Generator.class);

    /*/**
     *
     * 功能描述:
     *
     * @param: 首字母大写的名称
     * @return: java.lang.String
     * @auther: Airy
     * @date: 2019/4/1 16:53
     */
    public static String getPropertiesName(String name){
        char[] chars = name.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char)(chars[0] - 32);
        }
        return new String(chars);
    }

    public static String generatorEntity(String tableName){
        if(!DBUtil.haveTable(tableName)){
            logger.error("不存在此表");
        }
        StringBuffer entity = new StringBuffer();
        StringBuffer entityHeader = new StringBuffer();
        entityHeader.append("package " + ConfigUtil.pojoPackage +";\n\nimport java.io.Serializable;\n");

        List<String> columns = DBUtil.getColumnNames(tableName);
        List<String> columnTypes = DBUtil.getColumnTypes(tableName);
        List<String> columnComments = DBUtil.getColumnComments(tableName);
        Boolean isTime = false,isDate = false;
        for (String columnType:columnTypes) {
            switch (columnType){
                case "TIMESTAMP":
                    if(!isTime){
                        entityHeader.append("import java.sql.Timestamp;\n");
                    }
                    isTime = true;
                    break;
                case "DATE":
                    if(!isDate){
                        entityHeader.append("import java.util.Date;\n");
                    }
                    isDate = true;
                    break;
            }

        }
        entity.append("public class " + getTableName(tableName) + " implements Serializable {\n\n");
        for (int i=0; i< columns.size();i++) {
            entity.append("\t//" + columnComments.get(i) + "\n");
            entity.append("\tprivate "  +MysqlMapper.MYSQL_MAP.get(columnTypes.get(i)) + " " + getEntityName(columns.get(i)) + ";\n\n");
        }

        for (int i=0; i< columns.size();i++) {
            entity.append("\tpublic "  +MysqlMapper.MYSQL_MAP.get(columnTypes.get(i)) + " " + getEntityMethodName(columns.get(i),"get") + "() {");
            entity.append("\n\t\treturn " + getEntityName(columns.get(i))+";");
            entity.append("\n\t}\n\n");
            entity.append("\tpublic void " + getEntityMethodName(columns.get(i) ,"set") + "(" + MysqlMapper.MYSQL_MAP.get(columnTypes.get(i)) + " " + getEntityName(columns.get(i)) + ") {");
            entity.append("\n\t\tthis." + getEntityName(columns.get(i)) + " = " + getEntityName(columns.get(i)) + ";");
            entity.append("\n\t}\n\n");
        }

        entity.append("}");

        entityHeader.append(entity);

        System.out.println(entityHeader);

        return new String(entityHeader);
    }

    public static String getEntityName(String entytyName){
        if(entytyName.indexOf("_") != -1){
            String name;
            String [] entityArr = entytyName.split("_");
            name = entityArr[0];
            for (int i=1;i<entityArr.length;i++){
                name = name + getPropertiesName(entityArr[i]);
            }
            return name;
        }else{
            return entytyName;
        }
    }

    public static String getEntityMethodName(String entityName,String methodNamePre){
        if(entityName.indexOf("_") != -1){
            String name = methodNamePre;
            String [] entityArr = entityName.split("_");;
            for (int i=0;i<entityArr.length;i++){
                name = name + getPropertiesName(entityArr[i]);
            }
            return name;
        }else{
            return methodNamePre + getPropertiesName(entityName);
        }
    }

    public static String getTableName(String tableName){
        if(tableName.indexOf("_") != -1){
            String name = "";
            String [] entityArr = tableName.split("_");;
            for (int i=0;i<entityArr.length;i++){
                char[] chars = entityArr[i].toCharArray();
                if (chars[0] >= 'a' && chars[0] <= 'z') {
                    chars[0] = (char)(chars[0] - 32);
                }
                name = name + new String(chars);
            }
            return name;
        }else{

            return getPropertiesName(tableName);
        }
    }

    public static String generatorDao(String tableName){
        StringBuffer dao = new StringBuffer();
        dao.append("package " + ConfigUtil.daoPackage + ";\n\n");
        dao.append("import " + ConfigUtil.pojoPackage + "." + getTableName(tableName) + ";\n");
        dao.append("import java.util.List;\n\n");

        dao.append("public interface I" + getTableName(tableName) + "Dao {\n\n");
        dao.append("\tint insert(" + getTableName(tableName) + " " + getEntityName(tableName) + ");\n\n");
        dao.append("\tint update(" + getTableName(tableName) + " " + getEntityName(tableName) + ");\n\n");
        dao.append("\tint delete(String id);\n\n");
        dao.append("\tint selectOne(" + getTableName(tableName) + " " + getEntityName(tableName) + ");\n\n");
        dao.append("\tList<" + getTableName(tableName) + "> get"+ getTableName(tableName) +"List(" + getTableName(tableName) + " " + getEntityName(tableName) + ");\n\n");
        dao.append("\tint deleteBatch(List<" + getTableName(tableName) + "> "+ getEntityName(tableName) +"List);\n\n");
        dao.append("}");
        return new String(dao);
    }

    public static String generatorMapper(String tableName){
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        sb.append("<mapper namespace=\"" + ConfigUtil.daoPackage + ".I" + getTableName(tableName) + "Dao\">\n");
        sb.append(getResultMap(tableName));
        sb.append(getBase_Column_List(tableName));
        sb.append(getInsert(tableName));
        sb.append(getUpdate(tableName));
        sb.append(getDelete(tableName));
        sb.append(getSelectOne(tableName));
        sb.append(getSelectList(tableName));
        sb.append(getDeleteBatch(tableName));
        sb.append("</mapper>");
        System.out.println(new StringBuffer(sb));
        return new String(sb);
    }
    public static String getResultMap(String tableName) {
        StringBuffer sb = new StringBuffer();

        sb.append("\t<resultMap id=\"BaseResultMap\" type=\"" + ConfigUtil.pojoPackage + "." + getTableName(tableName) + "\">\n");
        List<String> columns = DBUtil.getColumnNames(tableName);
        List<String> columnTypes = DBUtil.getColumnTypes(tableName);
        for (int i=0;i<columns.size();i++){
            if(i == 0){
                sb.append("\t\t<id column=\"" + columns.get(i) + "\" jdbcType=\"" + columnTypes.get(i) + "\" property=\""+ getEntityName(columns.get(i)) +"\" />\n");
            }else{
                sb.append("\t\t<result column=\"" + columns.get(i) + "\" jdbcType=\"" + columnTypes.get(i) + "\" property=\""+ getEntityName(columns.get(i)) +"\" />\n");
            }
        }

        sb.append("\t</resultMap>\n");
        return new String(sb);
    }

    public static String getBase_Column_List(String tableName){
        StringBuffer sb = new StringBuffer();
        sb.append("\t<sql id=\"Base_Column_List\">\n\t\t");
        List<String> columns = DBUtil.getColumnNames(tableName);
        for (int i=0;i<columns.size();i++){
            sb.append(columns.get(i));
            if(i!=columns.size()-1){
                sb.append(",");
            }
        }
        sb.append("\n\t</sql>");
        sb.append("\n");

        return new String(sb);
    }

    public static String getInsert(String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("\t<insert id=\"insert\" parameterType=\""+ConfigUtil.pojoPackage + "." + getTableName(tableName) +"\">");
        sb.append("\n");
        sb.append("\t\tinsert into "+tableName+" (");
        List<String> columns = DBUtil.getColumnNames(tableName);
        List<String> columnTypes = DBUtil.getColumnTypes(tableName);
        List<String> columnComments = DBUtil.getColumnComments(tableName);
        for (int i=0;i<columns.size();i++){
            sb.append(columns.get(i));
            if(i!=columns.size()-1){
                sb.append(",");
            }
        }
        sb.append(")\n\t\tvalues(");
        for (int i=0;i<columns.size();i++){
            sb.append("#{");
            sb.append(getEntityName(columns.get(i)));
            sb.append(",jdbcType=" + columnTypes.get(i)+"}");
            if(i!=columns.size()-1){
                sb.append(",");
            }
        }
        sb.append(")\n\t</insert>\n");
        return new String(sb);
    }

    public static String getUpdate(String tableName){
        StringBuffer sb = new StringBuffer();
        sb.append("\t<update id=\"update\" parameterType=\""+ConfigUtil.pojoPackage + "." + getTableName(tableName) +"\">");
        sb.append("\n");
        sb.append("\t\tupdate "+tableName+"");
        sb.append("\n");
        sb.append("\t\t<trim prefix=\"set\" suffixOverrides=\",\" >");
        sb.append("\n");
        List<String> columns = DBUtil.getColumnNames(tableName);
        List<String> columnTypes = DBUtil.getColumnTypes(tableName);
        for (int i=1;i<columns.size();i++){
            sb.append("\t\t\t<if test=\"" + getEntityName(columns.get(i)) + " != null and " + getEntityName(columns.get(i)) + " != ''\">\n");
            sb.append("\t\t\t\t" + columns.get(i) + " = #{" + getEntityName(columns.get(i)) + ", jdbcType=" + columnTypes.get(i) + "}\n");
            sb.append("\t\t\t</if>\n");
        }
        sb.append("\t\t</trim>\n");
        sb.append("\t\twhere " + columns.get(0) + " = #{" + getEntityName(columns.get(0)) + ", jdbcType=" + columnTypes.get(0) + "}\n");
        sb.append("\t</update>\n");

        return new String(sb);
    }

    public static String getDelete(String tableName){
        StringBuffer sb = new StringBuffer();
        List<String> columns = DBUtil.getColumnNames(tableName);
        List<String> columnTypes = DBUtil.getColumnTypes(tableName);
        String id = columns.get(0);
        sb.append("\t<delete id=\"delete\" parameterType=\"java.lang.String\">");
        sb.append("\n");
        sb.append("\t\tdelete from "+tableName+"");
        sb.append("\n");
        sb.append("\t\twhere "+id+" = #{"+getEntityName(id)+",jdbcType=" + columnTypes.get(0) + "}");
        sb.append("\n");
        sb.append("\t</delete>");
        sb.append("\n");sb.append("\n");

        return new String(sb);
    }

    public static String getSelectOne(String tableName){
        StringBuffer sb = new StringBuffer();
        List<String> columns = DBUtil.getColumnNames(tableName);
        List<String> columnTypes = DBUtil.getColumnTypes(tableName);

        sb.append("\t<select id=\"selectOne\" parameterType=\"" + ConfigUtil.pojoPackage + "." + getTableName(tableName) + "\" resultMap=\"BaseResultMap\">");
        sb.append("\n");
        sb.append("\t\tselect <include refid=\"Base_Column_List\" />");
        sb.append("\n");
        sb.append("\t\tfrom " + tableName + " where 1 = 1\n");
        for (int i=0;i<columns.size();i++){
            sb.append("\t\t<if test=\"" + getEntityName(columns.get(i)) + " != null and " + getEntityName(columns.get(i)) + " != '' \">");
            sb.append("\n");
            sb.append("\t\t\tAND " + columns.get(i) + " = #{" + getEntityName(columns.get(i)) + ",jdbcType=" + columnTypes.get(i) + "}");
            sb.append("\n");
            sb.append("\t\t</if>");
            sb.append("\n");
        }
        sb.append("\t\tlimit 1\n");
        sb.append("\t</select>\n\n");

        return new String(sb);
    }

    public static String getSelectList(String tableName){
        StringBuffer sb = new StringBuffer();
        List<String> columns = DBUtil.getColumnNames(tableName);
        List<String> columnTypes = DBUtil.getColumnTypes(tableName);
        sb.append("\t<select id=\"get" + getTableName(tableName) + "List\" parameterType=\"" + ConfigUtil.pojoPackage + "." + getTableName(tableName) + "\" resultMap=\"BaseResultMap\">");
        sb.append("\n");
        sb.append("\t\tselect <include refid=\"Base_Column_List\" />");
        sb.append("\n");
        sb.append("\t\tfrom " + tableName + " where 1 = 1\n");
        for (int i=0;i<columns.size();i++){
            sb.append("\t\t<if test=\"" + getEntityName(columns.get(i)) + " != null and " + getEntityName(columns.get(i)) + " != '' \">");
            sb.append("\n");
            sb.append("\t\t\tAND " + columns.get(i) + " = #{" + getEntityName(columns.get(i)) + ",jdbcType=" + columnTypes.get(i) + "}");
            sb.append("\n");
            sb.append("\t\t</if>");
            sb.append("\n");
        }


        sb.append("\n");
        sb.append("\t</select>");
        sb.append("\n"); sb.append("\n");

        return new String(sb);
    }

    public static String getDeleteBatch(String tableName){
        StringBuffer sb = new StringBuffer();
        List<String> columns = DBUtil.getColumnNames(tableName);
        sb.append("\t<delete id=\"deleteBatch\" parameterType=\"java.util.List\">\n");
        sb.append("\t\tdelete from " + tableName + "\n");
        sb.append("\t\twhere " + columns.get(0) + " in\n");
        sb.append("\t\t<foreach collection=\"list\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\">\n");
        sb.append("\t\t\t#{item}\n");
        sb.append("\t\t</foreach>\n");
        sb.append("\t</delete>\n");
        return new String(sb);
    }

    public static void main(String[] args) {
        FileUtil fileUtil = new FileUtil();
        try {
            fileUtil.writeToFile(getTableName("biz_comment") + ".java",generatorEntity("biz_comment"));
            fileUtil.writeToFile("I" + getTableName("biz_comment") + "Dao.java",generatorDao("biz_comment"));
            fileUtil.writeToFile(getTableName("biz_comment") + "Mapper.xml",generatorMapper("biz_comment"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*List<String> a = DBUtil.getColumnTypes("biz_comment");
        for (String s:a
             ) {
            System.out.println(s);
        }*/
    }
}
