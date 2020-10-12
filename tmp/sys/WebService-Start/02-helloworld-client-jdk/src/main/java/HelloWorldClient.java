import cn.kiwipeach.helloword.client.url.HelloWorldWSImpl;
import cn.kiwipeach.helloword.client.url.HelloWorldWSImplService;

public class HelloWorldClient {
    public static void main(String[] argv) {
        HelloWorldWSImplService helloWorldFactory = new HelloWorldWSImplService();
        HelloWorldWSImpl helloWorldWSImplPort = helloWorldFactory.getHelloWorldWSImplPort();
        String backInfo = helloWorldWSImplPort.sayHello("世界你好");
        System.out.println(backInfo);
    }
}
