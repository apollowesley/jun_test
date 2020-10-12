package com.foo.common.base.utils;

import com.foo.common.base.entity.FooUser;
import com.foo.common.base.entity.FooUserTrip;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Auto Generated By IntelliJIdea on 9/25/16.
 */
public class CommonDatabaseHelper {
    final private static Logger logger = LoggerFactory.getLogger(CommonDatabaseHelper.class);

    @Test
    public void dropAndCreateTable() throws IOException {
        ClassPathResource myPath = new ClassPathResource("hibernate-localhost-mysql.properties");
        Assert.assertTrue(myPath.exists());

        Properties p = new Properties();
        p.load(myPath.getInputStream());

        Configuration configuration = new Configuration().setProperties(p);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        SessionFactory myFactory = configuration.addAnnotatedClass(FooUserTrip.class).buildSessionFactory(serviceRegistry);

        Session session = myFactory.openSession();

        List<FooUserTrip> fooUserTrips = Lists.newArrayList(session.createQuery("from FooUserTrip").list());
        logger.info("fooUserTrips size is:{}", fooUserTrips.size());

        session.close();
        myFactory.close();
    }

    @Test
    public void simpleTest() {
//        logger.info("{}",   RandomUtils.nextInt(, Months.MAX_VALUE.getMonths()));
    }

    @Test
    public void initTableData() throws IOException {
        ClassPathResource myPath = new ClassPathResource("hibernate-localhost-mysql.properties");
        Assert.assertTrue(myPath.exists());

        Properties p = new Properties();
        p.load(myPath.getInputStream());

        Configuration configuration = new Configuration().setProperties(p);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        SessionFactory myFactory = configuration
                .addAnnotatedClass(FooUser.class)
                .addAnnotatedClass(FooUserTrip.class)
                .buildSessionFactory(serviceRegistry);

        Session session = myFactory.openSession();

        Transaction tx = null;

        // See:http://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html/ch13.html#transactions-demarcation-nonmanaged
        try {
            tx = session.beginTransaction();
            // tx.setTimeout(200);
            final String entityName = "FooUserTrip";
//            session.createQuery("delete from " + entityName).executeUpdate();
            FooUserTrip fooUserTrip;
            FooUser fooUser;
            DateTime startDateTime = new DateTime();

            String fooUserId;
            String randomUserName;
            DateTime time;
            for (int i = 0; i < 10; i++) {
                fooUserId = FooUtils.generateUUID();
                randomUserName = "张全蛋" + RandomUtils.nextInt(1000, 10000);
                fooUser = new FooUser();
                fooUser.setId(fooUserId);
                fooUser.setAge(RandomUtils.nextInt(18, 50));
                fooUser.setTime(startDateTime.toDate());
                fooUser.setName(randomUserName);

                //Generate data for each random user.
                for (int j = 0; j < RandomUtils.nextInt(10, 50); j++) {
                    fooUserTrip = new FooUserTrip();
                    fooUserTrip.setTime(getRandomDateTime().toDate());
                    fooUserTrip.setId(FooUtils.generateUUID());
                    fooUserTrip.setUserId(fooUserId);
                    fooUserTrip.setName(randomUserName);
                    fooUserTrip.setMiles(RandomUtils.nextInt(1000, 10000));
                    session.save(fooUserTrip);
                }
                session.save(fooUser);
            }

            logger.info("generate data finish.");
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null)
                tx.rollback();
            throw e;
        } finally {
            session.close();
            myFactory.close();
        }
    }

    private DateTime getRandomDateTime() {
        int randomYear = RandomUtils.nextInt(2015, 2017);
        int randomMonth = RandomUtils.nextInt(1, 13);
        int randomDay = RandomUtils.nextInt(1, 26);
        int randomHour = RandomUtils.nextInt(0, 24);
        int randomMinute = RandomUtils.nextInt(0, 60);
        int randomSecond = RandomUtils.nextInt(0, 60);
        DateTime randomDateTime = new DateTime().withYear(randomYear)
                .withMonthOfYear(randomMonth).withDayOfMonth(randomDay)
                .withHourOfDay(randomHour).withMinuteOfHour(randomMinute).withSecondOfMinute(randomSecond);
        return randomDateTime;
    }
}
