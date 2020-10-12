import cn.kiwipeach.cxf.client.interceptor.CustomCXFClientInterceptor;
import cn.kiwipeach.helloworld.client.entity.HelloWorldWS;
import cn.kiwipeach.helloworld.client.entity.HelloWorldWSImplService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2017/11/13
 **/
public class cxfHelloWorld {
    public static void main(String[] argv) {
        HelloWorldWS helloWorldWSImplPort = new HelloWorldWSImplService().getHelloWorldWSImplPort();
        //发送请求的客户端对象
        Client client = ClientProxy.getClient(helloWorldWSImplPort);
        //客户端的自定义出拦截器
        List<Interceptor<? extends Message>> outInterceptors = client.getOutInterceptors();
        outInterceptors.add(new CustomCXFClientInterceptor("buruliu", "123456"));
        String kiWiPeach = helloWorldWSImplPort.sayHello("KiWiPeach CXF DEMO");
        System.out.println(kiWiPeach);
    }
}
