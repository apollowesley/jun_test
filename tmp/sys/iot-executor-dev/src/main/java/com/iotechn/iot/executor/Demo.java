package com.iotechn.iot.executor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-08-28
 * Time: 下午6:00
 */
public class Demo {
    private static final String[] EMPTY_ARGS = new String[]{};
    /**
     * 直接调用测试(推荐)
     * @param inputArgs
     * @throws Exception
     */
    public static void main(String inputArgs[]) throws Exception {
        /*测试的时候打开这个夹注，引下包


        //真实环境下不会是mock实现的 ， 如果后面有其他接口添加，都会放入dev包下。
        InvokeContext invokeContext = new InvokeContext();
        //测试邮箱的时候会用
        String[] args = new String[]{"your@domain.com","title","content"};
        invokeContext.setArgs(args);
        invokeContext.setOkHttpClient(new OkHttpClient());
        invokeContext.setCache(new CacheMockImpl());
        invokeContext.setMailSender(new MailSenderMockImpl());
        InvokerInfoModel invokerInfoModel = new InvokerInfoModel();
        invokerInfoModel.setInvokerId("mock");
        invokerInfoModel.setInvokerIp("139.199.212.134");
        invokeContext.setInvokerInfoModel(invokerInfoModel);

        //注：此处只是在开发阶段，写的测试实现类，在真正的环境中，是会真正实现，而且注入真实的数据

        Main main = new Main();
        System.out.println(main.getGmt(invokeContext));
        System.out.println(main.getLocation(invokeContext));

        //测试完成后，将Main里面的所有内容(第一行package不用复制)复制到 代码提交区即可在服务端运行。通过网关的接口，就可以通过硬件调用服务器方法了。

        */
    }


}
