package org.neuedu.fly.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/10/21-17:14
 **/
public class SqlSessionUtil {

    private static SqlSessionFactory factory;

    //解析配置文件，获取SqlSessionFactory
    static{
        try {
            InputStream stream = Resources.getResourceAsStream("mybatis-config.xml");
            factory = new SqlSessionFactoryBuilder().build(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取SqlSession
    public static SqlSession getSession(){
        return factory.openSession();
    }

}
