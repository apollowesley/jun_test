package com.course.base;

import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateTableTest {

    //表数
    private int tableNum = 4;

    //表数字的位数
    private static int zeroNum = 2;

    //0的字符串
    private static String zeroString = "";

    @BeforeClass
    public static void before() throws Exception{
        if(zeroNum <= 0){
            throw new Exception("zeroNum 要大于零");
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<zeroNum-1; i++){
            sb.append("0");
        }
        zeroString = sb.toString();
    }


    @Test
    public void createTableSql(){
        createStudentTableSql();
        createCourseTableSql();
        createSClassTableSql();
        createStudentCourseTableSql();
        createBroadCastTest();
    }



    public void createStudentTableSql(){
        String sql =
                "DROP TABLE IF EXISTS student%s; \n"+
                "CREATE TABLE  IF NOT EXISTS student%s \n"+
                "(  `sId` bigint NOT NULL auto_increment,\n" +
                "  `sName` varchar(32) NOT NULL,\n" +
                " `gender` int(2),\n" +
                " `classId` bigint ,\n" +
                "  `email` varchar(32),\n" +
                "  `phoneNum` varchar(32),\n" +
                "  PRIMARY KEY  (`sId`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表';";

        for (int i = 0; i < tableNum; i++) {
            System.out.println(String.format(sql,zeroString+i,zeroString+i));
        }
    }


    public void createCourseTableSql(){
        String sql =
                "DROP TABLE IF EXISTS course%s; \n"+
                        "CREATE TABLE  IF NOT EXISTS course%s \n"+
                        "(  `cId` bigint NOT NULL auto_increment,\n" +
                        "  `cName` varchar(32) NOT NULL,\n" +
                        " `teacher` varchar(32),\n" +
                        "  PRIMARY KEY  (`cId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成绩表';";

        for (int i = 0; i < tableNum; i++) {
            System.out.println(String.format(sql,zeroString+i,zeroString+i));
        }
    }


    public void createSClassTableSql(){
        String sql =
                "DROP TABLE IF EXISTS sClass%s; \n"+
                        "CREATE TABLE  IF NOT EXISTS sClass%s \n"+
                        "(  `classId` bigint NOT NULL auto_increment,\n" +
                        "  `className` varchar(32) NOT NULL,\n" +
                        " `header` varchar(32),\n" +
                        "  PRIMARY KEY  (`classId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';";

        for (int i = 0; i < tableNum; i++) {
            System.out.println(String.format(sql,zeroString+i,zeroString+i));
        }
    }


    public void createStudentCourseTableSql(){
        String sql =
                "DROP TABLE IF EXISTS studentCourse%s; \n"+
                        "CREATE TABLE  IF NOT EXISTS studentCourse%s \n"+
                        "(  `scId` bigint NOT NULL auto_increment,\n" +
                        "  `cId` bigint NOT NULL,\n" +
                        "  `sId` bigint NOT NULL ,\n" +
                        "  `score` double NOT NULL ,\n" +
                        "  `examDate` datetime NOT NULL ,\n" +
                        "  PRIMARY KEY  (`scId`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选课表';";

        for (int i = 0; i < tableNum; i++) {
            System.out.println(String.format(sql,zeroString+i,zeroString+i));
        }
    }


    public void createBroadCastTest(){
        String sql =
                "DROP TABLE IF EXISTS broadcast; \n"+
                        "CREATE TABLE  IF NOT EXISTS broadcast \n"+
                        "(  `id` bigint NOT NULL auto_increment,\n" +
                        "  `name` varchar(32) NOT NULL,\n" +
                        " `school` varchar(32),\n" +
                        "  PRIMARY KEY  (`id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试广播表';";
        System.out.println(sql);
    }
}
