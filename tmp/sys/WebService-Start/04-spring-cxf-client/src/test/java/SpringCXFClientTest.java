import cn.kiwipeach.client.first.HelloWorldWS;
import cn.kiwipeach.client.first.User;
import cn.kiwipeach.client.second.SecondPortType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Create Date: 2017/11/14
 * <p>
 *     Description: Spring环境测试CXF客户端
 * </p>
 *
 * @author kiwipeach [1099501218@qq.com]
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml"})
public class SpringCXFClientTest {

    @Autowired
    private HelloWorldWS helloWorldWS;

    @Autowired
    private SecondPortType secondPortType;

    @Test
    public void testfirstWS(){
        System.out.println(helloWorldWS);
        List<User> listUser = helloWorldWS.getListUser();
        System.out.println(listUser.size());
    }

    @Test
    public void testSecondWS(){
        System.out.println(secondPortType);
        String paramMsg = "KiWiPeach";
        String s = secondPortType.echoMsgMethod(paramMsg);
        System.out.println(s);
    }

}
