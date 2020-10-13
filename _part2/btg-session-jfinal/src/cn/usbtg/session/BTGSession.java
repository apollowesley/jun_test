package cn.usbtg.session;

import java.util.Iterator;

/**
 * btg session
 */
public interface BTGSession {

    /**
     * 获取session id唯一标识
     *
     * @return session id
     */
    public String getId();

    /**
     * 获取创建时间，UTC时间,也就是从1970年1月1日8时0分0秒开始偏移的秒数
     *
     * @return
     */
    public long getCreationTime();

    /**
     * 获取最后激活时间，UTC时间,也就是从1970年1月1日8时0分0秒开始偏移的秒数
     *
     * @return
     */
    public long getLastAccessedTime();

    /**
     * 设置最后激活时间，UTC时间,也就是从1970年1月1日8时0分0秒开始偏移的秒数
     *
     * @return
     */
    public void setLastAccessedTime(long lastAccessedTime);

    /**
     * 设置session最大空闲时间，秒
     *
     * @param maxInactiveInterval 最大空闲时间
     */
    public void setMaxInactiveInterval(int maxInactiveInterval);

    /**
     * 获取session最大空闲时间
     *
     * @return
     */
    public int getMaxInactiveInterval();

    /**
     * 在session中存入值
     *
     * @param key 存入值key
     * @param obj 存入对象
     */
    public void setAttribute(String key, Object obj);

    /**
     * 获取session中存入的值
     *
     * @param key 存入值key
     * @return
     */
    public Object getAttribute(String key);

    /**
     * 从session中移除存入的值
     *
     * @param key 存入值key
     */
    public void removeAttribute(String key);

    /**
     * 获取session中存入的值名称key
     *
     * @return
     */
    public Iterator<String> getAttributeNames();

    /**
     * 使session失效
     */
    public void invalidate();

    /**
     * 是否失效
     *
     * @return
     */
    public boolean isInvalidate();

}
