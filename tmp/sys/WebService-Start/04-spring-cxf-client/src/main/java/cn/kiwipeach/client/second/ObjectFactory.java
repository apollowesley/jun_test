
package cn.kiwipeach.client.second;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.kiwipeach.client.second package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EchoMsgMethod_QNAME = new QName("http//:www.kiwipeach.cn/secondService", "echoMsgMethod");
    private final static QName _EchoMsgMethodResponse_QNAME = new QName("http//:www.kiwipeach.cn/secondService", "echoMsgMethodResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.kiwipeach.client.second
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EchoMsgMethod }
     * 
     */
    public EchoMsgMethod createEchoMsgMethod() {
        return new EchoMsgMethod();
    }

    /**
     * Create an instance of {@link EchoMsgMethodResponse }
     * 
     */
    public EchoMsgMethodResponse createEchoMsgMethodResponse() {
        return new EchoMsgMethodResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoMsgMethod }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http//:www.kiwipeach.cn/secondService", name = "echoMsgMethod")
    public JAXBElement<EchoMsgMethod> createEchoMsgMethod(EchoMsgMethod value) {
        return new JAXBElement<EchoMsgMethod>(_EchoMsgMethod_QNAME, EchoMsgMethod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoMsgMethodResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http//:www.kiwipeach.cn/secondService", name = "echoMsgMethodResponse")
    public JAXBElement<EchoMsgMethodResponse> createEchoMsgMethodResponse(EchoMsgMethodResponse value) {
        return new JAXBElement<EchoMsgMethodResponse>(_EchoMsgMethodResponse_QNAME, EchoMsgMethodResponse.class, null, value);
    }

}
