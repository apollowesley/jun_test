package cn.usbtg.session;

/**
 * session管理器接口
 */
public interface BTGSessionManager {

    /**
     * 添加一个session
     *
     * @param session
     */
    public void addSession(BTGSession session);

    /**
     * 移除一个session
     *
     * @param sessionId
     */
    public void removeSession(String sessionId);

    /**
     * 根据session id获取一个session
     *
     * @param sessionId id
     * @return
     */
    public BTGSession getSession(String sessionId);

    /**
     * 根据session id获取一个session
     *
     * @return
     */
    public BTGSession getNewSession();

    /**
     * 根据session id刷新一个session
     *
     * @param session
     * @return
     */
    public void refreshSession(BTGSession session);

    /**
     * 获取sessionId名称key
     *
     * @return
     */
    public String getSessionIdKey();

    /**
     * 设置sessionId名称key
     *
     * @param sessionIdKey
     */
    public void setSessionIdKey(String sessionIdKey);

    /**
     * 获取session超时时间
     *
     * @return
     */
    public int getSessionTimeout();

    /**
     * 设置session超时时间
     *
     * @param sessionTimeout
     */
    public void setSessionTimeout(int sessionTimeout);

    /**
     * 获取sessionId生成器
     *
     * @return
     */
    public BTGSessionIdGenerator getSessionIdGenerator();

    /**
     * 设置sessionId生成器
     *
     * @param sessionIdGenerator
     */
    public void setSessionIdGenerator(BTGSessionIdGenerator sessionIdGenerator);

    /**
     * 获取session存储器
     *
     * @return
     */
    public BTGSessionDao getSessionDao();

    /**
     * 设置session存储器
     *
     * @param sessionDao
     */
    public void setSessionDao(BTGSessionDao sessionDao);

}
