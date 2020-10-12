package com.luoqy.speedy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DealStrSub {  
    /** 
     * 正则表达式匹配两个指定字符串中间的内容 
     * @param soap 
     * @return 
     */  
    public static String getSubUtil(String soap,String rgex){  
        String list = "";  
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式  
        Matcher m = pattern.matcher(soap);  
        	 while (m.find()) {  
                 int i = 1;  
                 list+=m.group(i)+"||";  
                 i++;  
             } 
        	 if(list.contains("||")){
        		 list=list.substring(0, list.toString().length()-2);
        	 }
        	 return list; 
    }  
      
    /** 
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样 
     * @param soap 
     * @param rgex 
     * @return 
     */  
    public static String getSubUtilSimple(String soap,String rgex){  
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式  
        Matcher m = pattern.matcher(soap);  
        while(m.find()){  
            return m.group(1);  
        }  
        return "";  
    }  
      
    /** 
     * 测试 
     * @param args 
     */  
    public static void main(String[] args) {  
        String str = "<span>{message,123456,1}</span><p>{测试一下}</p>";  
        String rgex = "\\{(.*?)\\}";  
        String list=getSubUtil(str,rgex);
        String[] StrList=list.split("\\|\\|");
        str=str.replaceAll(rgex, "123456");
        System.out.println(str);
        System.out.println(StrList[1]);
        System.out.println(getSubUtilSimple(str, rgex)=="");  
    }    
}  