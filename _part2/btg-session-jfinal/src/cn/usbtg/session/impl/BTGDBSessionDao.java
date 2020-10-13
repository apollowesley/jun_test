package cn.usbtg.session.impl;

import cn.usbtg.session.BTGSession;
import cn.usbtg.session.BTGSessionDao;
import cn.usbtg.session.util.EmptyUtil;
import com.jfinal.plugin.activerecord.Record;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 基于db的session存储实现
 */
public class BTGDBSessionDao implements BTGSessionDao {

    protected static final Logger log = Logger.getLogger(BTGDBSessionDao.class);

    private BTGDBSessionDaoConfig config;

    public BTGDBSessionDao() {
        this.config = new BTGDBSessionDaoConfig();
    }

    public BTGDBSessionDao(BTGDBSessionDaoConfig sessionDBConfig) {
        this.config = sessionDBConfig;
    }

    @Override
    public void saveSession(BTGSession session) {
        Record sessionRecord = new Record();
        sessionRecord.set(this.config.getSessionIdColumnName(), session.getId());
        sessionRecord.set(this.config.getSessionObjColumnName(), session);
        sessionRecord.set(this.config.getSessionLastActiveTimeColumnName(), System.currentTimeMillis());
        this.config.getDbPro().save(this.config.getTableName(), sessionRecord);
    }

    @Override
    public void deleteSession(String sessionId) {
        this.config.getDbPro().deleteById(this.config.getTableName(), this.config.getSessionIdColumnName(), sessionId);
    }

    @Override
    public BTGSession getSession(String sessionId) {
        BTGSession session = null;

        Record sessionRecord = this.config.getDbPro().findById(this.config.getTableName(), this.config.getSessionIdColumnName(), sessionId);
        if (EmptyUtil.isEmpty(sessionRecord)) return null;
        byte[] sessionBytes = sessionRecord.getBytes(this.config.getSessionObjColumnName());
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(sessionBytes));
            session = (BTGSession) objectInputStream.readObject();
        } catch (IOException e) {
            log.error(this, e);
        } catch (ClassNotFoundException e) {
            log.error(this, e);
        }
        session.setLastAccessedTime(sessionRecord.getLong(this.config.getSessionLastActiveTimeColumnName()));

        return session;
    }

    @Override
    public void refreshSession(final BTGSession session) {
        Record sessionRecord = new Record();
        sessionRecord.set(this.config.getSessionIdColumnName(), session.getId());
        sessionRecord.set(this.config.getSessionLastActiveTimeColumnName(), System.currentTimeMillis());
        this.config.getDbPro().update(this.config.getTableName(), this.config.getSessionIdColumnName(), sessionRecord);
    }

}
