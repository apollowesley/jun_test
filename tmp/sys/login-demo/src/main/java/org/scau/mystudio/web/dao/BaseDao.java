package org.scau.mystudio.web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

/**
 * @author jinglun
 * @create 2016-09-13 9:53
 */

//封装一些基本操作
@Repository
public class BaseDao {

    private SessionFactory sessionFactory;
    public SessionFactory sessionFactory(){
        return sessionFactory;
    }
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    public Session getSession(){
        Session session = sessionFactory.openSession();
        return session;
    }
}
