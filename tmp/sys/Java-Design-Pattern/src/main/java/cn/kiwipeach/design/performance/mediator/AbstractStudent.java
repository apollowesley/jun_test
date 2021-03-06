/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.mediator;

/**
 * Create Date: 2017/11/25 
 * Description: 抽象学生类
 * @author kiwipeach [1099501218@qq.com]
 */
public class AbstractStudent {
    /**
     * 不直接持有对象，而是持有终结者对象
     */
    private Medicator medicator;

    public Medicator getMedicator() {
        return medicator;
    }

    public void setMedicator(Medicator medicator) {
        this.medicator = medicator;
    }
}
