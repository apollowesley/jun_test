/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.config;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * @author tengda
 */
public class Utils {

    public static boolean isSimpleProperty(Class clazz) {
        return isSimpleValueType(clazz) || (clazz.isArray() && isSimpleValueType(clazz.getComponentType()));
    }

    public static boolean isSimpleValueType(Class clazz) {
        if (Number.class.isAssignableFrom(clazz)) {
            return true;
        }
        if (Boolean.class.isAssignableFrom(clazz)) {
            return true;
        }
        if (CharSequence.class.isAssignableFrom(clazz)) {
            return true;
        }

        if (!clazz.getName().contains(".")) {
            return true;
        }
        return false;

    }

   


}
