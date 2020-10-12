package org.gen.code.meta;

/**
 * Created by Administrator on 2015/8/4.
 */
public class DaoMeta {
    private String daoPackage;
    private String entityName;
    private String entitySimpleName;
    private String queryModel = "";
    private String querySimpleModel = "";

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


    public String getQueryModel() {
        return queryModel;
    }

    public void setQueryModel(String queryModel) {
        this.queryModel = queryModel;
    }

    public String getQuerySimpleModel() {
        return querySimpleModel;
    }

    public void setQuerySimpleModel(String querySimpleModel) {
        this.querySimpleModel = querySimpleModel;
    }
}
