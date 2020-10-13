package com.wangxin.service.news;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.wangxin.service.simple.ItemService;
import com.wangxin.solr.ItemDocument;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml", "classpath:/spring/applicationContext-dao.xml", "classpath:/spring/applicationContext-solr.xml" })
public class ItemServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ItemServiceTest.class);

    @Autowired
    private ItemService itemService;

    @Test
    public void testSearchForPage() {
        try {
            Page<ItemDocument> page = itemService.searchForPage("商品", 1);
            log.info("{}", JSON.toJSONString(page));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
