package com.wangxin.solr;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Component;


@Component
public interface CustomItemDocumentRepository extends SolrCrudRepository<ItemDocument, String> {

    Page<ItemDocument> findAll(Pageable page);// 所以商品分页查询

    Page<ItemDocument> findByGoodsName(String goodsName, Pageable page);// 查询商品名

    Page<ItemDocument> findByKeywords(String keywords, Pageable page);// 关键字查询

    Page<ItemDocument> findByKeywordsIn(Collection<String> keywords, Pageable page);// 多条件关键字查询以"In"结尾

}
