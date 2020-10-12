package pers.man.quickdevcore;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.man.quickdevcommon.util.StringUtils;
import pers.man.quickdevcore.system.entity.menu.Menu;
import pers.man.quickdevcore.system.persistence.menu.MenuMapper;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuickDevCoreApplicationTests {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    void contextLoads() {

//        ArrayList<Menu> child = new ArrayList<>();
//        child.add(menu1);
//        child.add(menu2);
//        child.add(menu3);

        Menu menu = new Menu();
        String uuId = StringUtils.getUUId();
        menu.setId(uuId);
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        menu.setTitle("沙雕");
        menuMapper.save(menu);
//
//        Menu menu1 = new Menu();
//        menu1.setId(StringUtils.getUUId());
//        menu1.setCreateTime(LocalDateTime.now());
//        menu1.setUpdateTime(LocalDateTime.now());
//        menu1.setTitle("菜单管理");
//        menu1.setParentMenuId(uuId);
//        menuMapper.save(menu1);
    }
    @Test
    public void test(){

    }
}
