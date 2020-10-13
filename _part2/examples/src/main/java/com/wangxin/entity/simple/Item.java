package com.wangxin.entity.simple;

import java.io.Serializable;
import java.util.Date;

import org.apache.solr.common.SolrInputDocument;

import com.wangxin.solr.ItemDefinition;

public class Item implements Serializable {

    private static final long serialVersionUID = -3938555491314951580L;

    private String id;

    private String goodsName;

    private String brandName;

    private String word;

    private String gkey;

    private Date createTime;

    private String keywords;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getGkey() {
        return gkey;
    }

    public void setGkey(String gkey) {
        this.gkey = gkey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public SolrInputDocument getSolrInputDocument() {
        SolrInputDocument d = new SolrInputDocument();
        d.addField(ItemDefinition.ID, id);
        d.addField(ItemDefinition.GOODSNAME, goodsName);
        d.addField(ItemDefinition.BRANDNAME, brandName);
        d.addField(ItemDefinition.WORD, word);
        d.addField(ItemDefinition.GKEY, gkey);
        d.addField(ItemDefinition.CREATETIME, createTime);
        return d;
    }

}
