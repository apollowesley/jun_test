package com.meiriyouke.easycode.test;

import org.junit.Test;

import com.meiriyouke.easycode.utils.NameUtils;

/**
 * Created with IntelliJ IDEA.
 * User: liyd
 * Date: 13-12-6
 * Time: 下午5:34
 */
public class NameUtilsTest {

    @Test
    public void getCamelName() {

        String name = NameUtils.getCamelName("____USER_INFO");
        System.out.println(name);

    }

}
