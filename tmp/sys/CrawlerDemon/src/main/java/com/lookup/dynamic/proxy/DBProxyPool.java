/*
 * developer spirit_demon  @ 2015.
 */

package com.lookup.dynamic.proxy;


import com.lookup.dynamic.buss.sqlmapper.MysqlServiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

@Qualifier
public class DBProxyPool implements ProxyPool {

    private Logger logger = LoggerFactory.getLogger("proxy");

    private final static BlockingQueue<Proxy> proxyQueue = new DelayQueue<Proxy>();
    private final static Map<String, Proxy> allProxy = new ConcurrentHashMap<String, Proxy>();

    private final static String proxyFilePath = "config";
    private final static String proxyFileName = "proxy.txt";

    private final static int reuseInterval = 1500;// ms
    private final static int reviveTime = 2 * 60 * 60 * 1000;// ms
    private final static int saveProxyInterval = 10 * 60 * 1000;// ms

    private static boolean isEnable = false;
    private static boolean validateWhenInit = false;

    @Inject
    private MysqlServiceMapper mysqlServiceMapper;

    public DBProxyPool() {
        //     initProxy();
    }

    public void initProxy() {
        // 需要自实现从数据读取方式  ip地址的格式 是192.168.1.1:80
        List<String> proxyList = mysqlServiceMapper.getProxyList("");
        addProxy(proxyList);
    }

    public void addProxy(List<String> httpProxyList) {
        isEnable = true;
        for (String proxyString : httpProxyList) {
            String[] proxy = proxyString.split(":");
            try {
                if (proxy != null) {

                    if (allProxy.containsKey(proxy[0] + "_" + proxy[1])) {
                        continue;
                    }
                    Proxy item = new Proxy(proxy[0], Integer.valueOf(proxy[1]));
                    if (!validateWhenInit || ProxyUtils.validateProxy(item)) {
                        item.setReuseTimeInterval(reuseInterval);
                        proxyQueue.add(item);
                        allProxy.put(proxy[0] + "_" + proxy[1], item);
                    }
                } else {
                    logger.error("proxy format error:");
                }

            } catch (NumberFormatException e) {
                logger.error("HttpHost init error:", e);
            }

        }
        logger.info("proxy pool size>>>>" + allProxy.size());
    }

    public Proxy getProxyHost() {
        Proxy proxy = null;
        try {
            Long time = System.currentTimeMillis();
            proxy = proxyQueue.take();
            double costTime = (System.currentTimeMillis() - time) / 1000.0;
            if (costTime > reuseInterval) {
                logger.info("get proxy time >>>> " + costTime);
            }
            proxy.setLastBorrowTime(System.currentTimeMillis());
            proxy.borrowNumIncrement(1);
        } catch (InterruptedException e) {
            logger.error("get proxy error", e);
        }
        if (proxy == null) {
            throw new NoSuchElementException();
        }
        return proxy;
    }

    public void returnProxy(Proxy proxy) {
        if (proxy == null) {
            return;
        }
        int statusCode = proxy.getStateCode();
        String host = proxy.getHttpHost();
        switch (statusCode) {
            case Proxy.SUCCESS:
                proxy.setReuseTimeInterval(reuseInterval);
                proxy.setFailedNum(0);
                proxy.recordResponse();
                proxy.successNumIncrement(1);
                break;
            case Proxy.ERROR_403:
                proxy.fail(Proxy.ERROR_403);
                proxy.setReuseTimeInterval(reuseInterval * proxy.getFailedNum());
                logger.warn(host + " >>>> reuseTimeInterval is >>>> " + proxy.getReuseTimeInterval() / 1000.0);
                break;

            case Proxy.ERROR_404:
                proxy.fail(Proxy.ERROR_404);
                proxy.setReuseTimeInterval(reuseInterval * proxy.getFailedNum());
                logger.warn(host + " >>>> reuseTimeInterval is >>>> " + proxy.getReuseTimeInterval() / 1000.0);
                break;
            case Proxy.ERROR_500:
                proxy.fail(Proxy.ERROR_500);
                proxy.setReuseTimeInterval(10 * 60 * 1000 * proxy.getFailedNum());
                logger.warn("this proxy is banned >>>> " + proxy.getHttpHost());
                logger.info(host + " >>>> reuseTimeInterval is >>>> " + proxy.getReuseTimeInterval() / 1000.0);
                break;
            case Proxy.ERROR_502:
                proxy.fail(Proxy.ERROR_502);
                proxy.setReuseTimeInterval(100 * 60 * 1000 * proxy.getFailedNum());
                logger.warn("this proxy is banned >>>> " + proxy.getHttpHost());
                logger.info(host + " >>>> reuseTimeInterval is >>>> " + proxy.getReuseTimeInterval() / 1000.0);
                break;
            default:
                proxy.fail(statusCode);
                break;
        }
        if (proxy.getFailedNum() > 20) {
            proxy.setReuseTimeInterval(reviveTime);
            allProxy.remove(proxy.getHttpHost());
            logger.error("remove proxy >>>> " + proxy.getHttpHost() + ">>>>" + proxy.getStateCode() + " >>>> remain proxy >>>> " + proxyQueue.size());

        }
        if (proxy.getFailedNum() > 0 && proxy.getFailedNum() % 5 == 0) {
            if (!ProxyUtils.validateProxy(proxy)) {
                proxy.setReuseTimeInterval(reviveTime);
                allProxy.remove(proxy.getHttpHost());
                logger.error("remove proxy >>>> " + proxy.getHttpHost() + ">>>>" + proxy.getStateCode() + " >>>> remain proxy >>>> " + proxyQueue.size());
            }
        }

        try {
            proxyQueue.put(proxy);
        } catch (InterruptedException e) {
            logger.warn("proxyQueue return proxy error", e);
        }
    }

    public static String allProxyStatus() {
        String re = "all proxy info >>>> \n";
        for (Entry<String, Proxy> entry : allProxy.entrySet()) {
            re += entry.getValue().toString() + "\n";
        }
        return re;
    }

    public int getIdleNum() {
        return proxyQueue.size();
    }

    public int getReuseInterval() {
        return reuseInterval;
    }


    public void enable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public int getReviveTime() {
        return reviveTime;
    }

    public boolean isValidateWhenInit() {
        return validateWhenInit;
    }

    public void validateWhenInit(boolean validateWhenInit) {
        this.validateWhenInit = validateWhenInit;
    }

    public int getSaveProxyInterval() {
        return saveProxyInterval;
    }


}
