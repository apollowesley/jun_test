package com.laycms.util;

import org.jsoup.Jsoup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtils {

	 /** 
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 
     * 此方法中前三位格式有： 
     * 13+任意数 
     * 15+除4的任意数 
     * 18+除1和4的任意数 
     * 17+除9的任意数 
     * 147 
     */  
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {  
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";  
        Pattern p = Pattern.compile(regExp);  
        Matcher m = p.matcher(str);  
        return m.matches();  
    }  

    public static String htmlToString(String html){
        if(org.apache.commons.lang.StringUtils.isEmpty(html)){
            return null;
        }
        html = html.replace("<div>","").replace("</div>","")
                .replace("<p>","").replace("</p>","")
                .replaceAll("<p.*>","").replace("</span>","")
                .replaceAll("<span.*>","");
        return html;
    }

    public static void main(String[] args) {

        String html = "<p style='font-size:100px'>这是很好的哦</p><br/><span></span><p>我是没有样式的p元素</p>";
        System.out.println(htmlToString(html));

    }

}
