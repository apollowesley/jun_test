/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.create.builder.facade;

import cn.kiwipeach.design.create.builder.entity.Product;

/**
 * Create Date: 2017/11/25 
 * Description: 变形记刚构建器
 * @author kiwipeach [1099501218@qq.com]
 */
public interface Builder {

     void buildHead(Product product);

     void buildBody(Product product);

     void buildFoot(Product product);

}
