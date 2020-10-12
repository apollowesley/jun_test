package com.iotechn.iot.executor.api;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-08
 * Time: 下午5:13
 */
public interface ExecutionService {

    public String addClass(String content,Long userId);

    public String reloadClass(String content,String secret,Long userId);

}
