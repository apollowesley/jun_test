package com.site.biz.handler;

public abstract class DefaultJobHandler {

    public abstract String getClassName();

    public abstract void addService();

    /**
     * 处理业务
     * 
     * @param message
     * @return
     */
    public abstract void execute();

}
