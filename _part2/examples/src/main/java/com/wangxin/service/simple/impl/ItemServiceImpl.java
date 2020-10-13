package com.wangxin.service.simple.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wangxin.common.exception.BusinessException;
import com.wangxin.common.framework.key.FactoryAboutKey;
import com.wangxin.common.framework.key.table.MasterTablesEnum;
import com.wangxin.entity.simple.Item;
import com.wangxin.mapper.simple.ItemMapper;
import com.wangxin.service.simple.ItemService;
import com.wangxin.solr.CustomItemDocumentRepository;
import com.wangxin.solr.ItemDefinition;
import com.wangxin.solr.ItemDocument;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private CustomItemDocumentRepository customItemDocumentRepository;

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Page<ItemDocument> searchForPage(String keywords, int pageNum) {
        Sort st = new Sort(Sort.Direction.DESC, ItemDefinition.CREATETIME);// 排序
        Pageable page = new PageRequest(pageNum, 10, st);// 每页显示10条

        Page<ItemDocument> p = null;
        if (StringUtils.isNotBlank(keywords)) {
            String[] searchTerms = StringUtils.split(keywords, " ");// 空格切割，多关键字查询
            List<String> result = new ArrayList<String>(searchTerms.length);
            for (String term : searchTerms) {
                if (StringUtils.isNotEmpty(term))
                    result.add(ClientUtils.escapeQueryChars(term));
            }
            log.debug("# keywords={}", JSON.toJSONString(result));
            p = customItemDocumentRepository.findByKeywordsIn(result, page);
        } else {
            // p = customItemDocumentRepository.findByGoodsName(keywords, page);
            p = customItemDocumentRepository.findAll(page);
        }

        log.debug("\r\t\r\t {} \r\t", JSON.toJSONString(p));
        return p;
    }

    @Override
    public boolean deleteItemById(String id) {
        int flag = itemMapper.delete(id);
        if (flag == 1) {
            customItemDocumentRepository.delete(id);
            return true;
        }
        return false;
    }

    @Override
    public ItemDocument findItemById(String id) {
        return customItemDocumentRepository.findOne(id);
    }

    @Override
    public boolean editItem(Item item) {
        int flag = itemMapper.update(item);
        if (flag == 1) {
            // customItemDocumentRepository.save(items);// TODO 目前spring data solr有 bug
            try {
                solrClient.add(item.getSolrInputDocument());
                solrClient.commit();
                return true;
            } catch (SolrServerException | IOException e) {
                log.error("# solr更新失败 , error message={}", e.getLocalizedMessage());
                throw new BusinessException("solr更新失败");
            }
        }
        return false;
    }

    public ItemDocument toItemDocument(Item item) {
        ItemDocument doc = new ItemDocument();
        doc.setId(item.getId());
        doc.setGoodsName(item.getGoodsName());
        doc.setBrandName(item.getBrandName());
        doc.setWord(item.getWord());
        doc.setGkey(item.getGkey());
        doc.setCreateTime(item.getCreateTime());
        return doc;
    }

    @Override
    public boolean addItem(Item item) {
        item.setId(FactoryAboutKey.getPkByMasterDB(MasterTablesEnum.T_NEWS));
        log.debug("# item={}", JSON.toJSONString(item));
        int flag = itemMapper.insert(item);
        if (flag == 1) {
            // customItemDocumentRepository.save(items);// TODO 目前spring data solr有 bug
            try {
                solrClient.add(item.getSolrInputDocument());
                solrClient.commit();
                return true;
            } catch (SolrServerException | IOException e) {
                log.error("# solr更新失败 , error message={}", e.getLocalizedMessage());
                throw new BusinessException("solr更新失败");
            }
        }
        return false;
    }

    @Override
    public boolean syncItem(String syncType) {
        List<Item> items = itemMapper.findAll();
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (Item item : items) {
            docs.add(item.getSolrInputDocument());
        }
        if (StringUtils.equals(syncType, "all")) {
            customItemDocumentRepository.deleteAll();
        }
        // customItemDocumentRepository.save(items);// TODO 目前spring data solr有 bug
        try {
            solrClient.add(docs);
            solrClient.commit();
            return true;
        } catch (SolrServerException | IOException e) {
            log.error("# solr同步失败 , error message={}", e.getLocalizedMessage());
            return false;
        }
    }

}
