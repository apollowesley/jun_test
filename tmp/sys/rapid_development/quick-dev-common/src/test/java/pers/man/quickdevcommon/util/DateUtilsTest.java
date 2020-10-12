package pers.man.quickdevcommon.util;

import org.junit.jupiter.api.Test;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-01 20:13
 * @project quick-dev
 */
public class DateUtilsTest {
    @Test
    public void test(){
        String currentTime = DateUtils.getCurrentTimeStr();
        System.out.println(currentTime);
    }
}
