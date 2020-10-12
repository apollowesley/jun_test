/*
 * Copyright 2018 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.demo.freemarker.function;

import freemarker.template.SimpleSequence;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Create Date: 2018/02/24
 * Description: freemarker排序自定义函数
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class NumberSortTemplate implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        //freemarker.template.SimpleSequence 为freemarker引擎自带的类型，需要转换成目标java类型
        SimpleSequence arg0 = (SimpleSequence)arguments.get(0);
        List<BigDecimal> list = arg0.toList();
        //Comparator接口
        Collections.sort(list,new Comparator<BigDecimal>(){
            @Override
            public int compare(BigDecimal o1, BigDecimal o2) {
                return o1.intValue() - o2.intValue();  //升序
            }
        });
        return list;
    }
}
