
package cn.kiwipeach.client.second;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SecondPortType", targetNamespace = "http//:www.kiwipeach.cn/secondService")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SecondPortType {


    /**
     * 
     * @param paramMsg
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "echoResult", targetNamespace = "")
    @RequestWrapper(localName = "echoMsgMethod", targetNamespace = "http//:www.kiwipeach.cn/secondService", className = "cn.kiwipeach.client.second.EchoMsgMethod")
    @ResponseWrapper(localName = "echoMsgMethodResponse", targetNamespace = "http//:www.kiwipeach.cn/secondService", className = "cn.kiwipeach.client.second.EchoMsgMethodResponse")
    public String echoMsgMethod(
        @WebParam(name = "paramMsg", targetNamespace = "")
        String paramMsg);

}