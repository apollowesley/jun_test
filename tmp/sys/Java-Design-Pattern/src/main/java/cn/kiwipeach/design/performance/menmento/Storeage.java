/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.menmento;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Create Date: 2017/11/23
 * Description: 备份仓库
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class Storeage {

    private static Map<Integer, Object> listMenmento = new LinkedHashMap<>();

    public static void save2Store(Object obj) throws CloneNotSupportedException {
        //需要克隆对象进行保存，不然徒劳
        if(obj instanceof Student){
            Student copyStu = ((Student) obj).deepCopy();
            listMenmento.put(obj.hashCode(), copyStu);
        }
    }

    /**
     * 恢复原来对象的属性值和引用
     * @param object 需要恢复对象
     * @return 返回恢复后的对象
     * @throws CloneNotSupportedException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object restoreObj(Object object) throws CloneNotSupportedException, InvocationTargetException, IllegalAccessException {
        Object storeObj = listMenmento.get(object.hashCode());
        BeanUtils.copyProperties(storeObj,object);
        return object;
    }
}
