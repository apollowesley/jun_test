package cn.kiwipeach.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;

/**
 * Create Date: 2017/11/13
 * Description: 自定义的CXF拦截器
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class CustomCXFServerInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    public CustomCXFServerInterceptor() {
        //协议初始化前
        super(Phase.PRE_PROTOCOL);
        System.out.println("自定义拦截器初始化.....");
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        Header header = message.getHeader(new QName("kiwipeach"));
        if(header!=null) {
            Element atguiguEle = (Element) header.getObject();
            String name = atguiguEle.getElementsByTagName("name").item(0).getTextContent();
            String password = atguiguEle.getElementsByTagName("password").item(0).getTextContent();
            if("buruliu".equals(name) && "123456".equals(password)) {
                System.out.println("Server 通过拦截器....");
                return;
            }
        }
        //不能通过
        System.out.println("Server 没有通过拦截器....");
        throw new Fault(new RuntimeException("请求需要一个正确的用户名和密码!"));
    }
    /**
     <Envelope>
         <head>
             <kiwipeach>
                 <name>buruliu</name>
                 <password>123456</password>
            </kiwipeach>
         <head>
         <Body>
             <sayHello>
             <arg0>BOB</arg0>
             <sayHello>
         </Body>
     </Envelope>
     */
}
