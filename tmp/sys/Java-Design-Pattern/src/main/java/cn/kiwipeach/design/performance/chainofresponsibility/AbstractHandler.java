/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.chainofresponsibility;

import cn.kiwipeach.design.performance.chainofresponsibility.handler.IHandler;

/**
 * Create Date: 2017/11/22 
 * Description: 抽象责任链接口
 * @author kiwipeach [1099501218@qq.com]
 */
public abstract class AbstractHandler implements IHandler {
    private IHandler iHandler;

    public IHandler getiHandler() {
        return iHandler;
    }

    public void setiHandler(IHandler iHandler) {
        this.iHandler = iHandler;
    }

    @Override
    public abstract void operation();
}
