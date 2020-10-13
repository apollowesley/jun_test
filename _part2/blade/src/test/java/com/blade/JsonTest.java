package com.blade;

import com.blade.kit.JsonKit;
import com.blade.model.TestBean;
import org.junit.Test;

/**
 * @author biezhi
 *         2017/6/6
 */
public class JsonTest {

    @Test
    public void test1() throws Exception {
        TestBean testBean = new TestBean();
        testBean.setAge(20);
        testBean.setName("jack");
        testBean.setPrice(2.31D);
        testBean.setSex(false);
        testBean.setOtherList(new String[]{"a", "b"});

        String text = JsonKit.toString(testBean);
        System.out.println(text);

        TestBean bean = JsonKit.formJson(text, TestBean.class);
        System.out.println(bean);
    }

}

