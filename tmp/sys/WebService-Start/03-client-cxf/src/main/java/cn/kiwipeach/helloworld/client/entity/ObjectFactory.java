
package cn.kiwipeach.helloworld.client.entity;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.kiwipeach.helloworld.client.entity package. 
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

    private final static QName _GetUser_QNAME = new QName("http://api.ws.kiwipeach.cn/", "getUser");
    private final static QName _GetMapUserResponse_QNAME = new QName("http://api.ws.kiwipeach.cn/", "getMapUserResponse");
    private final static QName _SayHello_QNAME = new QName("http://api.ws.kiwipeach.cn/", "sayHello");
    private final static QName _GetListUser_QNAME = new QName("http://api.ws.kiwipeach.cn/", "getListUser");
    private final static QName _GetUserResponse_QNAME = new QName("http://api.ws.kiwipeach.cn/", "getUserResponse");
    private final static QName _GetMapUser_QNAME = new QName("http://api.ws.kiwipeach.cn/", "getMapUser");
    private final static QName _GetListUserResponse_QNAME = new QName("http://api.ws.kiwipeach.cn/", "getListUserResponse");
    private final static QName _SayHelloResponse_QNAME = new QName("http://api.ws.kiwipeach.cn/", "sayHelloResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.kiwipeach.helloworld.client.entity
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMapUserResponse }
     * 
     */
    public GetMapUserResponse createGetMapUserResponse() {
        return new GetMapUserResponse();
    }

    /**
     * Create an instance of {@link GetMapUserResponse.Return }
     * 
     */
    public GetMapUserResponse.Return createGetMapUserResponseReturn() {
        return new GetMapUserResponse.Return();
    }

    /**
     * Create an instance of {@link GetListUser }
     * 
     */
    public GetListUser createGetListUser() {
        return new GetListUser();
    }

    /**
     * Create an instance of {@link GetUserResponse }
     * 
     */
    public GetUserResponse createGetUserResponse() {
        return new GetUserResponse();
    }

    /**
     * Create an instance of {@link SayHello }
     * 
     */
    public SayHello createSayHello() {
        return new SayHello();
    }

    /**
     * Create an instance of {@link GetUser }
     * 
     */
    public GetUser createGetUser() {
        return new GetUser();
    }

    /**
     * Create an instance of {@link SayHelloResponse }
     * 
     */
    public SayHelloResponse createSayHelloResponse() {
        return new SayHelloResponse();
    }

    /**
     * Create an instance of {@link GetMapUser }
     * 
     */
    public GetMapUser createGetMapUser() {
        return new GetMapUser();
    }

    /**
     * Create an instance of {@link GetListUserResponse }
     * 
     */
    public GetListUserResponse createGetListUserResponse() {
        return new GetListUserResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link GetMapUserResponse.Return.Entry }
     * 
     */
    public GetMapUserResponse.Return.Entry createGetMapUserResponseReturnEntry() {
        return new GetMapUserResponse.Return.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.ws.kiwipeach.cn/", name = "getUser")
    public JAXBElement<GetUser> createGetUser(GetUser value) {
        return new JAXBElement<GetUser>(_GetUser_QNAME, GetUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMapUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.ws.kiwipeach.cn/", name = "getMapUserResponse")
    public JAXBElement<GetMapUserResponse> createGetMapUserResponse(GetMapUserResponse value) {
        return new JAXBElement<GetMapUserResponse>(_GetMapUserResponse_QNAME, GetMapUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.ws.kiwipeach.cn/", name = "sayHello")
    public JAXBElement<SayHello> createSayHello(SayHello value) {
        return new JAXBElement<SayHello>(_SayHello_QNAME, SayHello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.ws.kiwipeach.cn/", name = "getListUser")
    public JAXBElement<GetListUser> createGetListUser(GetListUser value) {
        return new JAXBElement<GetListUser>(_GetListUser_QNAME, GetListUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.ws.kiwipeach.cn/", name = "getUserResponse")
    public JAXBElement<GetUserResponse> createGetUserResponse(GetUserResponse value) {
        return new JAXBElement<GetUserResponse>(_GetUserResponse_QNAME, GetUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMapUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.ws.kiwipeach.cn/", name = "getMapUser")
    public JAXBElement<GetMapUser> createGetMapUser(GetMapUser value) {
        return new JAXBElement<GetMapUser>(_GetMapUser_QNAME, GetMapUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.ws.kiwipeach.cn/", name = "getListUserResponse")
    public JAXBElement<GetListUserResponse> createGetListUserResponse(GetListUserResponse value) {
        return new JAXBElement<GetListUserResponse>(_GetListUserResponse_QNAME, GetListUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.ws.kiwipeach.cn/", name = "sayHelloResponse")
    public JAXBElement<SayHelloResponse> createSayHelloResponse(SayHelloResponse value) {
        return new JAXBElement<SayHelloResponse>(_SayHelloResponse_QNAME, SayHelloResponse.class, null, value);
    }

}
