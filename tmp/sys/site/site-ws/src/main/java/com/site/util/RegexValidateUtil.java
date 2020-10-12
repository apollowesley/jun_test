/**  
 * Project Name:xjhws  
 * File Name:RegexValidateUtil.java  
 * Package Name:com.xjh.api.util  
 * Date:2015年4月22日上午10:55:19  
 * Copyright (c) 2015, xjh.com All Rights Reserved.  
 *  
 */
package com.site.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description (正则相关验证工具类) <br/>
 * Date: 2015年4月22日 上午10:55:19 <br/>
 * 
 * @since JDK 1.6
 */
public class RegexValidateUtil {

    /**
     * 
     * matchPhone:(验证手机).
     * 
     * 
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 
     * checkEmail:(验证邮箱).
     * 
     * 
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
