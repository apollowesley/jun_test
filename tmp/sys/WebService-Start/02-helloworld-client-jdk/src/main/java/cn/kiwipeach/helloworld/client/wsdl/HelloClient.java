package cn.kiwipeach.helloworld.client.wsdl;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2017/11/12
 **/
public class HelloClient {

    public static void main(String[] argv) {
        HelloWorldWSImplService factory = new HelloWorldWSImplService();
        HelloWorldWSImpl helloWorldWSImplPort = factory.getHelloWorldWSImplPort();
        String backInfo = helloWorldWSImplPort.sayHello("卜铷");
        System.out.println(backInfo);
    }
}
