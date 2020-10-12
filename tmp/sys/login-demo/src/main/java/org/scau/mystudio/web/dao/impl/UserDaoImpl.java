package org.scau.mystudio.web.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.scau.mystudio.web.bean.User;
import org.scau.mystudio.web.dao.BaseDao;
import org.scau.mystudio.web.dao.UserDao;

import java.util.List;

/**
 * @author jinglun
 * @create 2016-09-11 22:54
 */
public class UserDaoImpl extends BaseDao implements UserDao {

    //保存用户信息到数据库中
    public void saveUser(User user){
        Session session = getSession();
        //将user对象保存到数据库中
        org.hibernate.Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
    }

    //验证用户信息，若正确返回一个User实例，否则返回null
    public User validateUser(String userName, String password){
        Session session = getSession();
        String hql = "from User u where u.userName = ? and u.password = ?";
        Query query = session.createQuery(hql);
        query.setParameter(0,userName);
        query.setParameter(1,password);
        List users = query.list();
        if(users.size()!=0){
            User user = (User)users.get(0);
            return user;
        }
        session.close();
        return null;
    }

    /**9.13景伦-注释掉下面代码
    private SessionFactory sessionFactory;

    private Session getCurrentSession(){
        return sessionFactory.openSession();
    }

    public void saveObject(User obj) throws HibernateException {
        this.getCurrentSession().save(obj);
    }
     */
}
