package org.gen.code.config;

/**
 * Created by Administrator on 2015/8/3.
 */
public class Config {

    private String entityPackage;
    private String daoPackage;
    private String codecPackage;
    private String queryModelPackage;
    private String daoImplPackage;


    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
        this.daoImplPackage = this.daoPackage+".mongo";
    }

    public String getCodecPackage() {
        return codecPackage;
    }

    public void setCodecPackage(String codecPackage) {
        this.codecPackage = codecPackage;
    }

    public String getQueryModelPackage() {
        return queryModelPackage;
    }

    public void setQueryModelPackage(String queryModelPackage) {
        this.queryModelPackage = queryModelPackage;
    }

    public String getDaoImplPackage() {
        return daoImplPackage;
    }

    public void setDaoImplPackage(String daoImplPackage) {
        this.daoImplPackage = daoImplPackage;
    }
}
