package cn.kiwipeach.helloworld.client.entity;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author liuburu
 * @create 2017/11/12
 **/
public class EntityWSDL {
    public static void main(String[] argv) {
        HelloWorldWSImplService factory = new HelloWorldWSImplService();
        HelloWorldWSImpl helloWorldWSImplPort = factory.getHelloWorldWSImplPort();
        String sayHello = helloWorldWSImplPort.sayHello("萤火虫");
        User kakaluote = helloWorldWSImplPort.getUser("kakaluote");
        List<User> listUser = helloWorldWSImplPort.getListUser();
        System.out.println(sayHello);
        System.out.println(kakaluote);
        System.out.println(listUser);
    }
}
