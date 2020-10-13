package cn.jeeweb.tag.test.config;

import cn.jeeweb.beetl.tags.dict.Dict;
import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.beetl.tags.dict.InitDictable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.gzst.gov.cn
 *
 * @version V1.0
 * @package cn.jeeweb.jeeweb.modules.config
 * @title:
 * @description: HTML初始化
 * @author: 王存见
 * @date: 2018/3/3 15:06
 * @copyright: 2017 www.gzst.gov.cn Inc. All rights reserved.
 */

@Configuration
public class DictConfig implements InitDictable{
    @Override
    public Map<String, List<Dict>> initDict() {
        //初始化页面数据字典
        List<Dict> sfList = new ArrayList<>();
        sfList.add(new Dict("是", "1"));
        sfList.add(new Dict("否", "0"));
        Map<String, List<Dict>> dictMap = new HashMap<>();
        dictMap.put("sf",sfList);
        return dictMap;
    }
}
