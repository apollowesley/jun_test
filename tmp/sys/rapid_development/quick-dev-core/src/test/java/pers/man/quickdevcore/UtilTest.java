package pers.man.quickdevcore;

import org.junit.jupiter.api.Test;
import pers.man.quickdevcommon.util.StringUtils;
import pers.man.quickdevcore.system.entity.menu.Menu;
import pers.man.quickdevcore.system.entity.menu.MenuVO;
import pers.man.quickdevcore.system.util.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-12 12:52
 * @project quick-dev
 */
public class UtilTest {
    @Test
    public void test(){
        Menu menu = new Menu();
        String uuId = StringUtils.getUUId();
        menu.setId(uuId);
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        menu.setTitle("设置");

        MenuVO menuVO = new MenuVO();
        menuVO = (MenuVO) BeanUtils.convertToVo(menu, MenuVO.class);
        System.out.println(menuVO);
    }
}
