package com.iotechn.iot.executor.model;

import com.iotechn.iot.executor.dev.ICache;
import com.iotechn.iot.executor.dev.IDeviceLogger;
import com.iotechn.iot.executor.dev.IMailSender;
import okhttp3.OkHttpClient;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-11-03
 * Time: 下午7:26
 */
public class InvokeContext implements Serializable {

    private InvokerInfoModel invokerInfoModel;

    private ICache cache;

    private IMailSender mailSender;

    private OkHttpClient okHttpClient;

    private IDeviceLogger deviceLogger;

    /**
     * 用户传入的参数
     */
    private String args[];

    public InvokerInfoModel getInvokerInfoModel() {
        return invokerInfoModel;
    }

    public void setInvokerInfoModel(InvokerInfoModel invokerInfoModel) {
        this.invokerInfoModel = invokerInfoModel;
    }

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }

    public IMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(IMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public IDeviceLogger getDeviceLogger() {
        return deviceLogger;
    }

    public void setDeviceLogger(IDeviceLogger deviceLogger) {
        this.deviceLogger = deviceLogger;
    }
}
