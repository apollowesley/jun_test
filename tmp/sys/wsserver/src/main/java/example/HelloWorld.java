package example;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * Created by Administrator on 2016/4/28.
 */
@WebService()
public class HelloWorld {

    public static void main(String[] argv) {
        Object implementor = new HelloWorld();
        String address = "http://localhost:9000/HelloWorld";
        Endpoint.publish(address, implementor);
    }

    @WebMethod
    public String sayHelloWorldFrom(String from) {
        String result = "Hello, world, from " + from;
        System.out.println(result);
        return result;
    }

}
