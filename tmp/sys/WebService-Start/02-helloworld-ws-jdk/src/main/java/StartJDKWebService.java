import cn.kiwipeach.ws.api.impl.HelloWorldWSImpl;

import javax.xml.ws.Endpoint;

/**
 * Create Date: 2017/11/12
 * Description: 启动Webservice服务
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class StartJDKWebService {
    public static void main(String[] args) {
        String address = "http://localhost:8080/jdkHello";
        Endpoint endpoint = Endpoint.publish(address, new HelloWorldWSImpl());
        //添加拦截器
        //服务端的入拦截器
        System.out.println("发布成功...."+address+"?wsdl");
    }
}
