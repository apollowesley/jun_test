package com.iotechn.iot.executor.dev;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-11-03
 * Time: 下午7:26
 */
public interface IMailSender {

    /**
     * @param address 接收地址
     * @param title   标题
     * @param content 内容
     * @param period  发送至少间隔周期 单位ms 内容相同且标题相同的邮件，最低发送周期
     * @return
     */
    public boolean sendTo(String address, String title, String content, Long period);
}
