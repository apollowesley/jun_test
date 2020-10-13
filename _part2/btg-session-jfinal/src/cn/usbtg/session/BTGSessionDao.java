package cn.usbtg.session;

/**
 * session存储管理器
 */
public interface BTGSessionDao {

    /**
     * 保存一个session
     *
     * @param session
     * @return
     */
    public void saveSession(BTGSession session);

    /**
     * 删除一个session
     *
     * @param sessionId
     */
    public void deleteSession(String sessionId);

    /**
     * 根据session id获取一个session
     *
     * @param sessionId
     * @return
     */
    public BTGSession getSession(String sessionId);

    /**
     * 根据session id刷新一个session
     *
     * @param session
     * @return
     */
    public void refreshSession(BTGSession session);

}
