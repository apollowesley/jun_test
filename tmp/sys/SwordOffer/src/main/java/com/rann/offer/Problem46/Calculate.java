package com.rann.offer.Problem46;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem46
 * 求1+2+...+n，不能使用乘除法，循环等
 * 利用反射找到函数名递归得到
 *
 * @author lemonjing
 */
public class Calculate {

    private int terminal(int n) {
        return 0;
    }

    public int sum(int n) throws Exception {
        List<Boolean> list = new ArrayList<>();
        list.add(false);
        list.add(true);
        // getDeclaredMethods能拿到所有方法（不包括继承），getMethods只能拿到public方法（包括继承的类或接口的方法）
        Method[] methods = this.getClass().getDeclaredMethods();
        int index = list.indexOf(n == 0);
        return (n--) + (Integer) methods[index].invoke(this, n);
    }
}
