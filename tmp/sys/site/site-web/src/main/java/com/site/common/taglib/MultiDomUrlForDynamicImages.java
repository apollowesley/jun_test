package com.site.common.taglib;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class MultiDomUrlForDynamicImages implements TemplateMethodModelEx {

    private String host;
    private String imgHostTag;
    private String imgUrlStarNum;
    private String imgHostNumber;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getImgHostTag() {
        return imgHostTag;
    }

    public void setImgHostTag(String imgHostTag) {
        this.imgHostTag = imgHostTag;
    }

    public String getImgUrlStarNum() {
        return imgUrlStarNum;
    }

    public void setImgUrlStarNum(String imgUrlStarNum) {
        this.imgUrlStarNum = imgUrlStarNum;
    }

    public String getImgHostNumber() {
        return imgHostNumber;
    }

    public void setImgHostNumber(String imgHostNumber) {
        this.imgHostNumber = imgHostNumber;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (CollectionUtils.isNotEmpty(arguments)) {
            return arguments.get(0);
        }
        return null;
    }

}
