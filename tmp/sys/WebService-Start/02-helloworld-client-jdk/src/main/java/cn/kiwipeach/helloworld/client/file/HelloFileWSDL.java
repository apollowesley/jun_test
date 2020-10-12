package cn.kiwipeach.helloworld.client.file;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2017/11/12
 **/
public class HelloFileWSDL {

    public static void main(String[] argv) {
        HelloWorldWSImplService factory = new HelloWorldWSImplService();
        HelloWorldWSImpl helloWorldWSImplPort = factory.getHelloWorldWSImplPort();
        System.out.println(helloWorldWSImplPort.sayHello("萤火虫"));
    }
}
