package cn.kiwipeach.ws.api;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Create Date: 2017/11/14
 * Description: 第二个WS服务(注解使用)
 *
 * @author kiwipeach [1099501218@qq.com]
 */
@WebService(serviceName="SecondServiceName", portName="SecondPortName", name="SecondPortType", targetNamespace="http//:www.kiwipeach.cn/secondService")
public interface SecondWS {

    @WebMethod(operationName = "echoMsgMethod")
    @WebResult(name="echoResult")String echoMsg(@WebParam(name="paramMsg")String msg);
}
