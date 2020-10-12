package com.dtdream.rdic.saas.hibernate;

import com.dtdream.rdic.saas.app.Results;
import com.dtdream.rdic.saas.def.CommonException;
import com.dtdream.rdic.saas.utils.*;
import org.hibernate.*;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Ozz8 on 2015/06/09.
 */
abstract public class HibernateBasic {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session session () {
        return sessionFactory.getCurrentSession();
    }

    public final static int size = 10;

    public Long count (List list) {
        if (null != list && list.size() > 0) {
            Object o = list.get(0);
            if (o instanceof Long)
                return ((Long) o).longValue();
            if (o instanceof BigInteger)
                return ((BigInteger) o).longValue();
            if (o instanceof BigDecimal)
                return ((BigDecimal) o).longValue();
        }
        return Long.valueOf(0);
    }

    public List<Serializable> insert (Session session, Object... objects) {
        List<Serializable> results = new ArrayList<>();
        if (null == objects || objects.length <= 0)
            return results;
        for (int i = 0 ; i < objects.length; ++ i) {
            results.add(session.save(objects[i]));
        }
        return results;
    }

    public Serializable insert (Object o) {
        return this.session().save(o);
    }

    public void delete (Object o) {
        this.session().delete(o);
    }

    public int delete (StringBuilder sb) throws CommonException {
        return update(sb, new HibernateDelete());
    }

    public void update (Object o) {
        this.session().update(o);

    }

    public void delete
        (
            StringBuilder builder,
            HibernateDeletor deletor
        ) throws CommonException
    {
        Session session = session();
        String hql = builder.toString();
        logger.debug(hql);
        ScrollableResults customers =
            session.createQuery(hql).setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
        try {
            if (customers.first())
                deletor.delete(customers.get(0));
        } catch (Exception e) {
            logger.error("删除数据失败：", e);
            throw new CommonException(Results.FAIL_UPDT_DB);
        }
    }

    public<T> int update (
        StringBuilder builder,
        HibernateUpdater<T> updater,
        Object... paramters
    ) throws CommonException {
        Session session = session();
        String hql = builder.toString();
        logger.debug(hql);
        ScrollableResults customers =
            session.createQuery(hql).setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
        int count=0;
        try {
            while (customers.next()) {
                updater.update((T) customers.get(0), count, paramters);
                if (++count % 20 == 0) {
                    //20, same as the JDBC batch size(hibernate.jdbc.batch_size)
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }
        } catch (Exception e) {
            logger.error("更新数据库失败：", e);
            throw new CommonException(Results.FAIL_UPDT_DB);
        }
        return count;
    }

    public void saveOrUpdate (Object o) {
        this.session().saveOrUpdate(o);

    }

    @SuppressWarnings("unchecked")
    public List query (String hql) {
        return this.session().createQuery(hql).list();
    }

    @SuppressWarnings("unchecked")
    public List query (String hql, Map<String,Object> param) {
        Query q = this.session().createQuery(hql);
        if (param != null && !param.isEmpty()) {
            Set<String> set = param.keySet();
            Iterator<String> it = set.iterator();
            String key;
            Object value;
            while(it.hasNext())
            {
                key = it.next();
                value = param.get(key);
                q.setParameter(key, value);
            }
        }
        return q.list();
    }

    @SuppressWarnings("unchecked")
    public List query (String hql, Map<String,Object> param, Integer start, Integer count) {
        if(start == null || start < 1){
            start = 1;
        }
        if(count == null || count < 1){
            count = 10;
        }

        Query q = this.session().createQuery(hql);

        if (param != null && !param.isEmpty()) {
            Set <String> set = param.keySet();
            Iterator<String> it = set.iterator();
            String key;
            Object value;
            while(it.hasNext())
            {
                key = it.next();
                value = param.get(key);
                q.setParameter(key, value);
            }
        }
        return q.setFirstResult(start).setMaxResults(count).list();
    }

    @SuppressWarnings("unchecked")
    public<T> T get (Class<T> c, Serializable id) {
        return (T)this.session().get(c , id);
    }

    public<T> T get (String hql, Map<String,Object> param) {
        List<T> l = this.query(hql, param);
        if (l != null && l.size() > 0) {
            return l.get(0);
        } else {
            return null;
        }
    }

    public Long count (String hql) {
        return (Long)this.session().createQuery(hql).uniqueResult();
    }

    public Long count (String hql, Map<String,Object> param) {
        Query q = this.session().createQuery(hql);
        if (param != null && !param.isEmpty()) {
            Set <String> set = param.keySet();
            Iterator<String> it = set.iterator();
            String key;
            Object value;
            while(it.hasNext())
            {
                key = it.next();
                value = param.get(key);
                q.setParameter(key, value);
            }
        }
        return (Long) q.uniqueResult();
    }

    public Integer execute (String hql) {
        return this.session().createQuery(hql).executeUpdate();
    }

    public Integer execute (String hql, Map<String,Object> param) {
        Query q = session().createQuery(hql);
        if (param != null && !param.isEmpty()) {
            Set <String> set = param.keySet();
            Iterator<String> it = set.iterator();
            String key;
            Object value;
            while(it.hasNext())
            {
                key = it.next();
                value = param.get(key);
                q.setParameter(key, value);
            }
        }
        return q.executeUpdate();
    }

    public List execute (StringBuilder builder) {
        return execute(builder, 0, Integer.MAX_VALUE);
    }

    public List execute (StringBuilder builder, int offset, int maximum) {
        String hql = builder.toString();
        logger.debug(hql);
        if (! KitUtils.check(hql).success()) {
            return null;
        }
        Query objQuery = session().createQuery(hql);
        if (null == objQuery) {
            return null;
        }
        objQuery.setFirstResult(offset);
        objQuery.setMaxResults(maximum);

        return objQuery.list();
    }

    /**
     * 执行原生态SQL语句
     * @param sb
     * @return
     */
    public void executeSQL (StringBuilder sb) {
        logger.debug(sb.toString());
        session().createSQLQuery(sb.toString()).executeUpdate();
    }

    public List querySQL (StringBuilder sb, Class<?>... classes) {
        logger.debug(sb.toString());
        SQLQuery query = session().createSQLQuery(sb.toString());
        if (null != classes) {
            for (int i = 0; i < classes.length; ++i)
                query.addEntity(classes[i]);
        }
        return query.list();
    }

    public<T> List<T> SQL (List<HibernateSelector> selectors, CharSequence from, HibernateExtractor<T> extractor) {
        if (null == selectors || selectors.size() <= 0)
            return null;
        List<T> results = new ArrayList<>(size);
        /**
         * 生成查询语句
         */
        StringBuilder sb = new StringBuilder("select ");
        Iterator<HibernateSelector> it = selectors.iterator();
        HibernateSelector item = null;
        while (it.hasNext()) {
            if (null != item)
                sb.append(',');
            item = it.next();
            sb.append(item.getSelect()).append(" as ").append(item.getAs());
        }
        sb.append(' ').append(from);
        logger.debug(sb.toString());
        /**
         * 构造查询
         */
        SQLQuery query = session().createSQLQuery(sb.toString());
        it = selectors.iterator();
        while (it.hasNext()) {
            item = it.next();
            query.addScalar(item.getAs(), item.getScalar());
        }
        /**
         * 构造查询结果
         */
        T record;
        List<Object[]> rows = query.list();
        Iterator<Object[]> cursor = rows.iterator();
        Object[] row;
        while (cursor.hasNext()) {
            row = cursor.next();
            record = extractor.extract(row);
            if (null != record)
                results.add(record);
        }
        return results;
    }

    public List<HibernateSelector> format (List<HibernateSelector> selectors, String select, String as, Type type) {
        if (null == selectors)
            selectors = new ArrayList<>(size);
        selectors.add(new HibernateSelector(select, as, type));
        return selectors;
    }
}
