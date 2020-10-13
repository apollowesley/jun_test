package com.wangxin.service.simple;

import org.springframework.data.domain.Page;

import com.wangxin.entity.simple.Item;
import com.wangxin.solr.ItemDocument;

public interface ItemService {

    Page<ItemDocument> searchForPage(String keywords, int pageNum);

    boolean deleteItemById(String id);

    ItemDocument findItemById(String id);

    boolean editItem(Item item);

    boolean addItem(Item item);

    boolean syncItem(String syncType);

}
