package db;

import db.model.TableFiledModel;
import utils.PropertiesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

/**
 * Created by eason on 2017/8/23.
 */
public class MySQLTableComment {
    public static Connection getMySQLConnection() throws Exception {
        String  url = PropertiesUtils.getConf("url");
        String  user =PropertiesUtils.getConf("user");
        String   password=PropertiesUtils.getConf("password");
        String   jdbc=PropertiesUtils.getConf("jdbc");
        Class.forName(jdbc);

        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }


    /**
     * 获取当前数据库下的所有表名称
     *
     * @return
     * @throws Exception
     */
    public static List getAllTableName() throws Exception {
        List tables = new ArrayList();
        Connection conn = getMySQLConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES ");
        while (rs.next()) {
            String tableName = rs.getString(1);
            tables.add(tableName);
        }
        rs.close();
        stmt.close();
        conn.close();
        return tables;
    }


    /**
     * 获得某表的建表语句
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public static Map getCommentByTableName(List tableName) throws Exception {
        Map map = new HashMap();
        Connection conn = getMySQLConnection();
        Statement stmt = conn.createStatement();
        for (int i = 0; i < tableName.size(); i++) {
            String table = (String) tableName.get(i);
            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + table);
            if (rs != null && rs.next()) {
                String createDDL = rs.getString(2);
                String comment = parse(createDDL);
                map.put(table, comment);
            }
            rs.close();
        }
        stmt.close();
        conn.close();
        return map;
    }

    /**
     * 获得某表中所有字段的注释
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    public static Map<String,List<TableFiledModel>> getColumnCommentByTableName(List tableName) throws Exception {
        Map tables = new HashMap();
        Connection conn = getMySQLConnection();
        Statement stmt = conn.createStatement();
        Statement stmt1 = conn.createStatement();
        for (int i = 0; i < tableName.size(); i++) {
            String table = (String) tableName.get(i);
            ResultSet rs1 = stmt1.executeQuery("select  * from " + table);
            ResultSet rs = stmt.executeQuery("show full columns from " + table);
            System.out.println("【" + table + "】");
            List<TableFiledModel> TableFieldModels = new ArrayList();
            ResultSetMetaData data =rs1.getMetaData();
            Map<String,String> columnsJavaTypes = new HashMap();
            Map<String,String> importClass = new HashMap();
            int index=1;
            while(index<=data.getColumnCount()){
                columnsJavaTypes.put(data.getColumnName(index),data.getColumnClassName(index));
                importClass.put(data.getColumnClassName(index),data.getColumnClassName(index));
                System.out.println(data.getColumnTypeName(index) +"   "+data.getColumnClassName(index)+"   "+data.getColumnName(index) +" ");
                index++;
            }
            while (rs.next()) {
                     String  columnsName  = rs.getString("Field");
                     //String  columnsType  = rs.getString("Type");
                     String  comment  = rs.getString("Comment");
                String columnsJavaType =columnsJavaTypes.get(columnsName);
                String type =columnsJavaType.substring(columnsJavaType.lastIndexOf(".")+1);
                //System.out.println(rs.getString("Field") +"\t:\t"+ rs.getString("Type")+ "\t:\t" + rs.getString("REMARKS"));
                TableFieldModels.add(new TableFiledModel(columnsName,type,comment,importClass));
            }
            tables.put(table,TableFieldModels);
            rs.close();
        }
        stmt1.close();
            stmt.close();
            conn.close();
        return  tables;
//      return map;
    }




    /**
     * 返回注释信息
     *
     * @param all
     * @return
     */

    public static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }

    public static void main(String[] args) throws Exception {
        List tables = getAllTableName();
        Map tablesComment = getCommentByTableName(tables);
        Set names = tablesComment.keySet();
        Iterator iter = names.iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            System.out.println("Table Name: " + name + ", Comment: " + tablesComment.get(name));
        }

        getColumnCommentByTableName(tables);
    }
}