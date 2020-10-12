/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.print.test;

/**
 * Create Date: 2017/11/25 
 * Description: 打印星星
 * @author kiwipeach [1099501218@qq.com]
 */
public class PrintStar {
    public static void main(String[] args) {
        printLine(8);

    }
    public static String printLine(int lineN){
        StringBuffer resultStar = new StringBuffer();
        for(int i=0;i<lineN-1;i++){
            resultStar.append(" ");
        }
        for(int i=0;i<lineN-1;i++){
            resultStar.append("*");
        }

        return "";
    }
}
