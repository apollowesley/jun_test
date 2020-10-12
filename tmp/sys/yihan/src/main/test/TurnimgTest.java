import mapper.TurnimgMapper;
import mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Turnimg;
import pojo.User;

import java.util.List;


public class TurnimgTest {
    private ApplicationContext context;
    private TurnimgMapper mapper;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("classpath:applicationContext-dao.xml");
        mapper = (TurnimgMapper) context.getBean("turnimgMapper");//注意首字母小写
    }

    @Test
    public void Test1() {
        List<Turnimg> list = mapper.getAllImg();
        System.out.println(list);
    }

    @Test
    public void Test2() {
        Turnimg turnimg = mapper.getTurnimgById(2);
        System.out.println(turnimg);
    }

    @Test
    public void Test3() {
        Turnimg turnimg = new Turnimg();
        turnimg.setId(5);
        turnimg.setText("123123213");
        turnimg.setLevel(111111);
        turnimg.setSrc("qwer");
        turnimg.setUsed(0);
        mapper.editTurnimg(turnimg);
    }
    @Test
    public void Test4() {
        Turnimg turnimg = new Turnimg();
//        turnimg.setId(5);
        turnimg.setText("123123213");
        turnimg.setLevel(111111);
        turnimg.setSrc("qwer");
        turnimg.setUsed(0);
        mapper.addTurnimg(turnimg);
    }
    @Test
    public void Test5() {

        mapper.DelTurnimgById(6);
    }

}
