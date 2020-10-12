import cn.kiwipeach.interceptor.CustomCXFServerInterceptor;
import cn.kiwipeach.ws.api.impl.HelloWorldWSImpl;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.message.Message;

import javax.xml.ws.Endpoint;
import java.util.List;

/**
 * Create Date: 2017/11/12
 * Description: 启动Webservice服务
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class StartCXFWebService {
    public static void main(String[] args) {
        String address = "http://localhost:8082/cxfHello";
        Endpoint endpoint = Endpoint.publish(address, new HelloWorldWSImpl());
        EndpointImpl endpointImpl = (EndpointImpl) endpoint;
        //服务端的日志入拦截器
        List<Interceptor<? extends Message>> inInterceptors = endpointImpl.getInInterceptors();
        inInterceptors.add(new LoggingInInterceptor());
        //服务端的日志出拦截器
        List<Interceptor<? extends Message>> outInterceptors = endpointImpl.getOutInterceptors();
        outInterceptors.add(new LoggingOutInterceptor());
        //服务端自定义拦截器
        inInterceptors.add(new CustomCXFServerInterceptor());

        System.out.println("发布成功...." + address + "?wsdl");
    }
    /*
        1）请求报文
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:api="http://api.ws.kiwipeach.cn/">
           <soapenv:Header/>
           <soapenv:Body>
              <api:getUser>
                 <!--Optional:-->
                 <arg0>kakaluote</arg0>
              </api:getUser>
           </soapenv:Body>
        </soapenv:Envelope>

        2）相应报文
        <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
           <soap:Body>
              <ns2:getUserResponse xmlns:ns2="http://api.ws.kiwipeach.cn/">
                 <return>
                    <username>kakaluote</username>
                    <userpasswd>lbr123</userpasswd>
                 </return>
              </ns2:getUserResponse>
           </soap:Body>
        </soap:Envelope>

        3）带有Header的请求报文信息
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:api="http://api.ws.kiwipeach.cn/">
                <soapenv:Header>
                    <atguigu>
                         <name>xfzhang</name>
                         <password>123456</password>
                    </atguigu>
                <soapenv:Header>
               <soapenv:Body>
                  <api:getListUser/>
               </soapenv:Body>
        </soapenv:Envelope>
    * */
}
