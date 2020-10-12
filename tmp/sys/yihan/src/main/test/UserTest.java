import mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.User;

import java.util.List;


public class UserTest {
    private ApplicationContext context;
    private UserMapper mapper;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("classpath:applicationContext-dao.xml");
        mapper = (UserMapper) context.getBean("userMapper");//注意首字母小写
    }

    @Test
    public void Test2() {
        List<User> list = mapper.getUserByUsername("admin");
        System.out.println(list);
    }

    @Test
    public void Tes3() {
        List<User> list = mapper.getAllUser();
        System.out.println(list);
    }

    @Test
    public void Tes4() {
        User user = mapper.getUserById(2);
        System.out.println(user);
    }

    @Test
    public void Tes5() {
        User user = new User();
        user.setId(3);
        user.setUsername("weqweqwe");
        user.setPassword("24235432423");
        mapper.editUser(user);
    }

    @Test
    public void Tes6() {
        User user = new User();
        user.setUsername("weqweqwe");
        user.setPassword("24235432423");
        mapper.addUser(user);
    }

}
