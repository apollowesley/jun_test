package com.imzdx.common.model.generator;

import java.util.ArrayList;
import java.util.List;

public class _TppGenerator {

    public static void main(String[] args) {
        String className = "Comment";
        String tableName = "comment";

        JfGenerator.me
                .setSrcFolder("src/main/java")
                .setViewFolder("src/main/webapp")
                .setPackageBase("com")
                .setBasePath("imzdx")
//        .tableSql(getSqlList())
                .javaRender(className, tableName)
                .htmlRender(className, tableName);

        System.out.println("---------OK-刷新一下项目吧---------");
    }


    private static List<String> getSqlList() {
        ArrayList<String> sqlList = new ArrayList<String>();

        sqlList.add("DROP TABLE IF EXISTS `t_test`;");
        sqlList.add("CREATE TABLE `t_test` ( " +
                "  `id` int(11) NOT NULL AUTO_INCREMENT, " +
                "  `test` varchar(255) DEFAULT NULL, " +
                "  PRIMARY KEY (`id`) " +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

        return sqlList;
    }


}
