package cn.kiwipeach.client.interceptor;

import com.sun.org.apache.xml.internal.utils.DOMHelper;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * Create Date: 2017/11/13
 * Description: 自定义CXF客户端拦截器（给请求saop添加header信息）
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class CustomCXFClientInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    private String name;
    private String password;

    public CustomCXFClientInterceptor(String name, String password) {
        super(Phase.PRE_PROTOCOL);//准备协议化时拦截
        this.name = name;
        this.password = password;
    }


    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        List<Header> headers = message.getHeaders();
		/*
		 <kiwipeach>
			<name>buruliu</name>
			<password>123456</password>
		</kiwipeach>
		 */
        Document document = DOMHelper.createDocument();
        Element rootEle = document.createElement("kiwipeach");
        Element nameELe = document.createElement("name");
        nameELe.setTextContent(name);
        rootEle.appendChild(nameELe);
        Element passwordELe = document.createElement("password");
        passwordELe.setTextContent(password);
        rootEle.appendChild(passwordELe);

        headers.add(new Header(new QName("kiwipeach"), rootEle));
        System.out.println("client handleMessage()....");
    }
}
