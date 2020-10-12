package com.gitee.lopssh.plugin.mybatis.auditlog.interceptor.handler;

public interface ISQLHandler
{
    void preHandle();

    void postHandle();
}
