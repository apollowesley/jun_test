package com.imooc.util;

import com.imooc.pojo.Lesson;

import java.util.List;

/**
 * <p>
 * 工具类测试
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2020/1/6 15:45
 */
public class DbUtilTest {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";
        String username = "root";
        String password = "root";
        DbUtil dbUtil = new DbUtil(jdbcUrl, username, password);
        final List<Lesson> lessonList = dbUtil.executeQuery("SELECT * FROM `t_lesson`", (rs, rowNum) -> {
            Lesson lesson = new Lesson();
            lesson.setId(rs.getLong("id"));
            lesson.setTitle(rs.getString("title"));
            lesson.setTeacher(rs.getString("teacher"));
            lesson.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
            lesson.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
            return lesson;
        });

        lessonList.forEach(System.out::println);
    }
}
