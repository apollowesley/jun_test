package com.imooc;

import com.imooc.pojo.Lesson;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * <p>
 * 主程序
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2020/1/6 14:47
 */
public class Main {
    @SneakyThrows
    public boolean insertLesson(Lesson lesson) {
        // 加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 创建数据库连接
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test";
        String username = "root";
        String password = "root";
        @Cleanup Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        // 预编译
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `t_lesson`(`title`,`teacher`,`create_time`,`update_time`) VALUES (?,?,?,?)");

        // 填充 SQL 参数
        preparedStatement.setString(1, lesson.getTitle());
        preparedStatement.setString(2, lesson.getTeacher());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(lesson.getCreateTime()));
        preparedStatement.setTimestamp(4, Timestamp.valueOf(lesson.getUpdateTime()));

        // 执行
        return preparedStatement.execute();
    }

    @SneakyThrows
    public boolean deleteLesson(Long id) {
        // 加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 创建数据库连接
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test";
        String username = "root";
        String password = "root";
        @Cleanup Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        // 预编译
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `t_lesson` WHERE `id` = ?");

        // 填充 SQL 参数
        preparedStatement.setLong(1, id);

        // 执行
        return preparedStatement.execute();
    }

    @SneakyThrows
    public boolean updateLesson(Lesson lesson) {
        if (lesson == null || lesson.getId() == null) {
            return false;
        }

        // 加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 创建数据库连接
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test";
        String username = "root";
        String password = "root";
        @Cleanup Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

        // 预编译
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `t_lesson` SET `title` = ?,`teacher` = ?,`update_time` = ? WHERE `id` = ?");

        // 填充 SQL 参数
        preparedStatement.setString(1, lesson.getTitle());
        preparedStatement.setString(1, lesson.getTeacher());
        preparedStatement.setTimestamp(1, Timestamp.valueOf(lesson.getUpdateTime()));

        // 执行
        return preparedStatement.execute();
    }

@SneakyThrows
public Lesson selectLesson(Long id) {
    // 加载数据库驱动
    Class.forName("com.mysql.cj.jdbc.Driver");

    // 创建数据库连接
    String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test";
    String username = "root";
    String password = "root";
    @Cleanup Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

    // 预编译
    @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `t_lesson` WHERE `id` = ?");

    // 填充 SQL 参数
    preparedStatement.setLong(1, id);

    // 执行
    @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

    Lesson lesson = null;

    while (resultSet.next()) {
        lesson = new Lesson();
        lesson.setId(resultSet.getLong("id"));
        lesson.setTitle(resultSet.getString("title"));
        lesson.setTeacher(resultSet.getString("teacher"));
        LocalDateTime createTime = resultSet.getTimestamp("create_time").toLocalDateTime();
        lesson.setCreateTime(createTime);
        LocalDateTime updateTime = resultSet.getTimestamp("update_time").toLocalDateTime();
        lesson.setUpdateTime(updateTime);
    }

    return lesson;
}


}
