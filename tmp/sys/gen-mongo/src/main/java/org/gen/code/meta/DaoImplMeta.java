package org.gen.code.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/4.
 */
public class DaoImplMeta {

    private String codecPackage;
    private String daoPackage;
    private String entityName;
    private String entitySimpleName;
    private String queryModelName = "";
    private String querySimpleName = "";

    private List<QueryMeta> querys = new ArrayList<>();
    private List<QueryMeta> nullQuerys = new ArrayList<>();

    public String getCodecPackage() {
        return codecPackage;
    }

    public void setCodecPackage(String codecPackage) {
        this.codecPackage = codecPackage;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntitySimpleName() {
        return entitySimpleName;
    }

    public void setEntitySimpleName(String entitySimpleName) {
        this.entitySimpleName = entitySimpleName;
    }

    public String getQueryModelName() {
        return queryModelName;
    }

    public void setQueryModelName(String queryModelName) {
        this.queryModelName = queryModelName;
    }

    public String getQuerySimpleName() {
        return querySimpleName;
    }

    public void setQuerySimpleName(String querySimpleName) {
        this.querySimpleName = querySimpleName;
    }

    public List<QueryMeta> getQuerys() {
        return querys;
    }

    public void setQuerys(List<QueryMeta> querys) {
        this.querys = querys;
    }

    public List<QueryMeta> getNullQuerys() {

        return nullQuerys;
    }

    public void setNullQuerys(List<QueryMeta> nullQuerys) {
        this.nullQuerys = nullQuerys;
    }
}
