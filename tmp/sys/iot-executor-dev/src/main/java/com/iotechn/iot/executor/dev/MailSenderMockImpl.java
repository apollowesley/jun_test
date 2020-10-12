package com.iotechn.iot.executor.dev;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-11-03
 * Time: 下午11:12
 */
public class MailSenderMockImpl implements IMailSender {

    //在发到线上去后，这个接口会插入真正的邮件发送组建。到时候就真的能收到了。
    public boolean sendTo(String address, String title, String content, Long period) {
        System.out.println("假装发送邮件成功了！ address=" + address + ";title=" + title + ";content=" + content);
        return true;
    }
}
