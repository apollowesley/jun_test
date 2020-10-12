
package cn.kiwipeach.client.second;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SecondWSImplService", targetNamespace = "http://impl.api.ws.kiwipeach.cn/", wsdlLocation = "http://192.168.1.107:8080/secondWS?wsdl")
public class SecondWSImplService
    extends Service
{

    private final static URL SECONDWSIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException SECONDWSIMPLSERVICE_EXCEPTION;
    private final static QName SECONDWSIMPLSERVICE_QNAME = new QName("http://impl.api.ws.kiwipeach.cn/", "SecondWSImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.1.107:8080/secondWS?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SECONDWSIMPLSERVICE_WSDL_LOCATION = url;
        SECONDWSIMPLSERVICE_EXCEPTION = e;
    }

    public SecondWSImplService() {
        super(__getWsdlLocation(), SECONDWSIMPLSERVICE_QNAME);
    }

    public SecondWSImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SECONDWSIMPLSERVICE_QNAME, features);
    }

    public SecondWSImplService(URL wsdlLocation) {
        super(wsdlLocation, SECONDWSIMPLSERVICE_QNAME);
    }

    public SecondWSImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SECONDWSIMPLSERVICE_QNAME, features);
    }

    public SecondWSImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SecondWSImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SecondPortType
     */
    @WebEndpoint(name = "SecondWSImplPort")
    public SecondPortType getSecondWSImplPort() {
        return super.getPort(new QName("http://impl.api.ws.kiwipeach.cn/", "SecondWSImplPort"), SecondPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SecondPortType
     */
    @WebEndpoint(name = "SecondWSImplPort")
    public SecondPortType getSecondWSImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://impl.api.ws.kiwipeach.cn/", "SecondWSImplPort"), SecondPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SECONDWSIMPLSERVICE_EXCEPTION!= null) {
            throw SECONDWSIMPLSERVICE_EXCEPTION;
        }
        return SECONDWSIMPLSERVICE_WSDL_LOCATION;
    }

}