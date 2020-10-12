package com.iotechn.iot.executor.dev;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-28
 * Time: 下午5:32
 */
public interface ICache {

    public void set(String key, String value);

    public void inc(String key, long delta);

    public void inc(String key, double delta);

    public String get(String key);
}
