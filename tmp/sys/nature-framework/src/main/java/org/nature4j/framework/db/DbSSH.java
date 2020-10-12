package org.nature4j.framework.db;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class DbSSH {
    private static Logger LOGGER = LoggerFactory.getLogger(DbSSH.class);
    private static String[] dbs = ConfigHelper.getDb();
    static List<Object> sessions = new ArrayList<>();
    public static void init() {
        for(String db:dbs){
            conSsh(db);
        }
    }

    private static void conSsh(String db) {
        if (StringUtil.isNotBank(ConfigHelper.getDbSSHHost(db))) {
            Session session ;
            String dbSShUser = ConfigHelper.getDbSSHUser(db);
            String dbSShHost = ConfigHelper.getDbSSHHost(db);
            String dbSShRemoteHost = ConfigHelper.getDbSSHRemoteHost(db);
            int dbSShRemotePort = ConfigHelper.getDbSSHRemotePort(db);
            String dbSSHPassword = ConfigHelper.getDbSSHPassword(db);
            int dbSSHLocalPort = ConfigHelper.getDbSSHLocalPort(db);
            int dbSSHPort = ConfigHelper.getDbSSHPort(db);
            int dbSSHTimeOut = ConfigHelper.getDbSSHTimeOut(db);
            JSch jsch = new JSch();
            try {
                session = jsch.getSession(dbSShUser, dbSShHost, dbSSHPort);
                session.setPassword(dbSSHPassword);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setTimeout(dbSSHTimeOut);
                session.connect();
                session.setPortForwardingL(dbSSHLocalPort, dbSShRemoteHost, dbSShRemotePort);
                sessions.add(session);
            } catch (JSchException e) {
                LOGGER.error(e.getMessage());
                throw new RuntimeException(e);
            }
            LOGGER.info("DbSSH:dbSShHost={},dbSShRemotePort={},dbSShUser={},dbSSHLocalPort={}",dbSShHost,dbSShRemotePort,dbSShUser,dbSSHLocalPort);
        }
    }

    public static void destroy(){
        sessions.forEach(session -> {
            if (((Session)session).isConnected()){
                ((Session)session).disconnect();
            }
        });
    }
}
