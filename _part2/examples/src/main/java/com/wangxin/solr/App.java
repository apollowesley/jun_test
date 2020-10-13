package com.wangxin.solr;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alibaba.fastjson.JSON;

public class App {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String connectionString = "jdbc:solr://127.0.0.1:2281,127.0.0.1:2381,127.0.0.1:2481?collection=xjh&aggregationMode=map_reduce&numWorkers=1";
            connection = DriverManager.getConnection(connectionString);
            statement = connection.createStatement();
            String sql = "SELECT id, goodsName, brandName , word , gkey FROM xjh ORDER BY id";//
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String goodsName = resultSet.getString("goodsName");
                String brandName = resultSet.getString("brandName");
                String word = resultSet.getString("word");
                String gkey = resultSet.getString("gkey");

                ItemDocument doc = new ItemDocument();
                doc.setId(id);
                doc.setGoodsName(goodsName);
                doc.setBrandName(brandName);
                doc.setWord(word);
                doc.setGkey(gkey);
                System.out.println(JSON.toJSONString(doc));
            }
        } catch (SQLException sqlEx) {
            System.out.println("查询solr报错: " + sqlEx.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception ex) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ex) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                }
            }
        }
    }
}