package com.imooc;

import com.imooc.pojo.Lesson;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 主程序
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2020/1/6 14:08
 */
public class SimpleQuery {
    @SneakyThrows
    public static void main(String[] args) {
        // 加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 创建数据库连接
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test";
        String username = "root";
        String password = "root";
        @Cleanup Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        // 创建 SQL 执行对象
        @Cleanup Statement statement = connection.createStatement();

        // 执行 SQL 语句
        @Cleanup ResultSet resultSet = statement.executeQuery("SELECT * FROM `t_lesson`");

        // 处理返回结果集对象
        List<Lesson> lessonList = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            String teacher = resultSet.getString("teacher");
            LocalDateTime createTime = resultSet.getTimestamp("create_time").toLocalDateTime();
            LocalDateTime updateTime = resultSet.getTimestamp("update_time").toLocalDateTime();

            Lesson lesson = new Lesson();
            lesson.setId(id);
            lesson.setTitle(title);
            lesson.setTeacher(teacher);
            lesson.setCreateTime(createTime);
            lesson.setUpdateTime(updateTime);

            lessonList.add(lesson);
        }

        // 打印结果
        lessonList.forEach(System.out::println);

        // 释放资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
