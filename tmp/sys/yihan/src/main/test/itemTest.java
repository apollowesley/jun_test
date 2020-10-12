import mapper.ItemMapper;
import mapper.SiteMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Detail;
import pojo.Item;
import pojo.QueryVo;
import pojo.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class itemTest {
    private ApplicationContext context;
    private ItemMapper mapper;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext("classpath:applicationContext-dao.xml");
        mapper = (ItemMapper) context.getBean("itemMapper");//注意首字母小写
    }


    @Test
    public void Test2() {
        System.out.println(mapper.getAllType());
    }

    @Test
    public void Test3() {
        Type type = new Type();
        type.setId(3);
        type.setItem("sdfgd13213123fsg");
        type.setLevel(87);
        mapper.editType(type);
    }

    @Test
    public void Test4() {
        Type type = new Type();
//        type.setId(3);
        type.setItem("sdfgd13213123fsg");
        type.setLevel(87);
        mapper.addType(type);
    }

    @Test
    public void Test5() {

        mapper.delTypeById(3);
    }

    @Test
    public void Test6() {

        System.out.println(mapper.getAllItem());
    }

    @Test
    public void Tes6() {

        System.out.println(mapper.getItemSize(new QueryVo()));
    }

    @Test
    public void Test7() {
        QueryVo vo = new QueryVo();
        vo.setCurrentPage(1);
        Integer start = (vo.getCurrentPage() - 1) * vo.getLimitPage();
        mapper.getItemSize(vo);
        List<Item> list = mapper.getItemList(vo, start);
        list = des(list);
        System.out.println(list);
    }

    private List<Item> des(List<Item> list) {
        Integer max = 1136;
        double k = 0.0;
        for (Item item : list) {
            k += (double) item.getWidth() / (double) item.getHeight();
        }
        System.out.println(k);
        Integer height = new Double(max / k).intValue();
        System.out.println(height);
        for (Item item : list) {
            double k1 = (double) item.getWidth() / (double) item.getHeight();
            item.setWidth1(new Double(k1 * height).intValue());
            item.setHeight1(height);
        }
        return list;
    }


    @Test
    public void Test8() {
        Detail detail = new Detail();
        detail.setId(5);
        detail.setItem1("单机前往百度1");
        detail.setItem2("www.baidu.com1");
        detail.setLevel(5);
        mapper.editDetail(detail);

    }

    @Test
    public void Test9() {
        Detail detail = new Detail();
        detail.setId(4);
        detail.setItemid(3);
        detail.setType1(4);
        detail.setType2("链接");
        detail.setItem1("单机前往百度1");
        detail.setItem2("www.baidu.com1");
        detail.setLevel(80);
        mapper.addDetail(detail);

    }

    @Test
    public void Test10() {
        System.out.println(mapper.getItemById(3));

    }
}
