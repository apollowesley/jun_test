package cn.usbtg.session;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * session管理器接口
 */
public interface BTGSessionContext extends Serializable {

    /**
     * 启用二级缓存
     */
    public void enableCache();

    /**
     * 禁用二级缓存
     */
    public void disableCache();

    /**
     * 添加一个session
     *
     * @param session BTGSession
     */
    public void addSession(BTGSession session);

    /**
     * 根据session id移除一个session
     *
     * @param sessionId String
     */
    public void removeSession(String sessionId);

    /**
     * 根据session id获取一个session
     *
     * @param sessionId String
     * @return String
     */
    public BTGSession getSession(String sessionId);

    /**
     * 获取一个新的session
     *
     * @return BTGSession
     */
    public BTGSession getNewSession();

    /**
     * 刷新一个session
     *
     * @param session BTGSession
     */
    public void refreshSession(BTGSession session);

    /**
     * 激活session
     *
     * @param session
     */
    public void active(BTGSession session);

    /**
     * 获取所有session
     *
     * @return Hashtable<String, BTGSession>
     */
    public Hashtable<String, BTGSession> getSessions();

    /**
     * 清理超时的session
     */
    public void clearTimeout();

    /**
     * session清理算法机制：
     * 1、设置清理周期，默认为30分钟；
     * 2、非绝对清理，当触发了addSession、removeSession、refreshSession、getSession、getNewSession、active时触发清理机制；
     * 3、通过调用clearTimeout手动清理；
     */
    public void checkSessionTimeout();

    /**
     * 清空session容器
     */
    public void clear();

    /**
     * 获取session id名称key
     *
     * @return String
     */
    public String getSessionIdKey();

    /**
     * 设置session id名称key
     *
     * @param sessionIdKey String
     */
    public void setSessionIdKey(String sessionIdKey);

    /**
     * 获取session超时时间
     *
     * @return int
     */
    public int getSessionTimeoutMillis();

    /**
     * 设置session超时时间
     *
     * @param sessionTimeout long
     */
    public void setSessionTimeoutMillis(int sessionTimeout);

    /**
     * 获取过期session清理机制触发周期(毫秒)
     *
     * @return int
     */
    public int getMaxClearTimeoutMillis();

    /**
     * 设置过期session清理机制触发周期(毫秒)
     *
     * @param maxClearTimeoutMillis int
     */
    public void setMaxClearTimeoutMillis(int maxClearTimeoutMillis);

    /**
     * 获取sessionId生成器
     *
     * @return BTGSessionIdGenerator
     */
    public BTGSessionIdGenerator getSessionIdGenerator();

    /**
     * 设置sessionId生成器
     *
     * @param sessionIdGenerator BTGSessionIdGenerator
     */
    public void setSessionIdGenerator(BTGSessionIdGenerator sessionIdGenerator);

    /**
     * 获取session存储器
     *
     * @return BTGSessionDao
     */
    public BTGSessionDao getSessionDao();

    /**
     * 设置session存储器
     *
     * @param sessionDao BTGSessionDao
     */
    public void setSessionDao(BTGSessionDao sessionDao);

    /**
     * 获取是否启用二级缓存
     *
     * @return boolean
     */
    public boolean isUseCache();

    /**
     * 设置是否启用二级缓存
     */
    public void setUseCache(boolean useCache);
}