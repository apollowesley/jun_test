package com.leo.vhr.model;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/1/15
 * @version: 1.0
 */
public class Meta
{
    private Boolean keepAlive;

    private Boolean requireAuth;

    public Boolean getKeepAlive()
    {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive)
    {
        this.keepAlive = keepAlive;
    }

    public Boolean getRequireAuth()
    {
        return requireAuth;
    }

    public void setRequireAuth(Boolean requireAuth)
    {
        this.requireAuth = requireAuth;
    }
}
