/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.interpreter;

/**
 * Create Date: 2017/11/25 
 * Description: 上下文环境对象
 * @author kiwipeach [1099501218@qq.com]
 */
public class Context {
    private Integer number1;
    private Integer number2;

    public Context() {
    }

    public Context(Integer number1, Integer number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }
}
