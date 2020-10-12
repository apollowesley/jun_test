import mapper.SiteMapper;
import mapper.TurnimgMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Turnimg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SiteTest {
    private ApplicationContext context;
    private SiteMapper mapper;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("classpath:applicationContext-dao.xml");
        mapper = (SiteMapper) context.getBean("siteMapper");//注意首字母小写
    }

    @Test
    public void Test1() {
        List<Map<String, String>> list = mapper.getAll();
        Map<String, String> map = new HashMap<String, String>();
        for (Map<String, String> item : list) {
            map.put(item.get("item"), item.get("info"));
        }
        System.out.println(map);
    }

    @Test
    public void Test2() {
        mapper.writeSite("title", "12321");
//        System.out.println(map);
    }


}
