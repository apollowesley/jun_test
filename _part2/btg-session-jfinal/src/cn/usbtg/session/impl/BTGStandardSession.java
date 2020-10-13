package cn.usbtg.session.impl;

import cn.usbtg.session.BTGSession;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * BTGSession标准实现类
 */
public class BTGStandardSession implements BTGSession, Serializable {

    private static final long serialVersionUID = 1L;

    private String sessionId; //唯一标识id
    private long creationTime; //创建时间
    private long lastAccessedTime; //最后激活时间
    private int maxInactiveInterval; //最大空闲时间，即session的失效时间
    private Map<String, Object> attributes; //session存放值的容器

    /**
     * 构造方法，初始化session并设置默认值
     *
     * @param sessionId
     */
    public BTGStandardSession(String sessionId, int maxInactiveInterval) {
        this.sessionId = sessionId;
        this.creationTime = System.currentTimeMillis();
        this.lastAccessedTime = System.currentTimeMillis();
        this.maxInactiveInterval = maxInactiveInterval;
        attributes = new Hashtable<String, Object>();
    }

    @Override
    public String getId() {
        return this.sessionId;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    @Override
    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    @Override
    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    @Override
    public int getMaxInactiveInterval() {
        return this.maxInactiveInterval;
    }

    @Override
    public void invalidate() {
        this.maxInactiveInterval = 0;
    }

    @Override
    public boolean isInvalidate() {
        return this.lastAccessedTime + this.maxInactiveInterval < System.currentTimeMillis();
    }

    @Override
    public void setAttribute(String key, Object obj) {
        this.attributes.put(key, obj);
    }

    @Override
    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    @Override
    public void removeAttribute(String key) {
        this.attributes.remove(key);
    }

    @Override
    public Iterator<String> getAttributeNames() {
        return this.attributes.keySet().iterator();
    }

}
