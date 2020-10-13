package com.wangxin.solr;

import java.io.Serializable;
import java.util.Date;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = ItemDocument.SOLR_COLLECTION)
public class ItemDocument implements ItemDefinition, Serializable {

    private static final long serialVersionUID = -3664270470198813947L;

    @Value("${solr.collection}")
    public final static String SOLR_COLLECTION = "";

    private @Id @Indexed String id;

    private @Indexed(GOODSNAME) String goodsName;
    private @Indexed(QUERY_GOODSNAME) String _goodsName;

    private @Indexed(BRANDNAME) String brandName;
    private @Indexed(QUERY_BRANDNAME) String _brandName;

    private @Indexed(WORD) String word;
    private @Indexed(QUERY_WORD) String _word;

    private @Indexed(GKEY) String gkey;
    private @Indexed(QUERY_GKEY) String _gkey;

    private @Indexed(CREATETIME) Date createTime;

    private @Indexed(KEYWORDS) String keywords;

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

    public String get_goodsName() {
        return _goodsName;
    }

    public void set_goodsName(String _goodsName) {
        this._goodsName = _goodsName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String get_brandName() {
        return _brandName;
    }

    public void set_brandName(String _brandName) {
        this._brandName = _brandName;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String get_word() {
        return _word;
    }

    public void set_word(String _word) {
        this._word = _word;
    }

    public String getGkey() {
        return gkey;
    }

    public void setGkey(String gkey) {
        this.gkey = gkey;
    }

    public String get_gkey() {
        return _gkey;
    }

    public void set_gkey(String _gkey) {
        this._gkey = _gkey;
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
        d.addField(ID, id);
        d.addField(GOODSNAME, goodsName);
        d.addField(BRANDNAME, brandName);
        d.addField(WORD, word);
        d.addField(GKEY, gkey);
        d.addField(CREATETIME, createTime);
        return d;
    }

    @Override
    public String toString() {
        return "ItemDocument [id=" + id + ", goodsName=" + goodsName + ", _goodsName=" + _goodsName + ", brandName=" + brandName + ", _brandName=" + _brandName + ", word=" + word + ", _word=" + _word + ", gkey=" + gkey + ", _gkey=" + _gkey + ", createTime=" + createTime + ", keywords=" + keywords + "]";
    }

}
