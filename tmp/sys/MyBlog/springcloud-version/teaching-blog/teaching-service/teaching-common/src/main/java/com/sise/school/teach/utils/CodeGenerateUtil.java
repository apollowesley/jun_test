package com.sise.school.teach.utils;

import java.util.Random;

/**
 * @author idea
 * @data 2018/10/21
 */
public class CodeGenerateUtil{

    public static String codeGenerate(String prefix){
        Random random=new Random();
        String temp = "";
        for (int i = 0; i < 15; i++) {
            temp += (char) ('a' + random.nextInt(25));
        }
        return prefix+"_code_"+temp;
    }

    public static String buildRandNumber(int len){
        StringBuffer stringBuffer=new StringBuffer();
        for(int i=0;i<len;i++){
           Random random=new Random();
           int number=random.nextInt(10);
            stringBuffer.append(number);
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(buildRandNumber(4));
    }

}
