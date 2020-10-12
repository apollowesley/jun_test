package model;

import java.util.Map;

/**
 * Created by liuyonghui on 2017-05-31.
 */
public class FiledModel {

    private String comment;
    private String name;
    private String type;
    //引用类
    Map importClass;
    public FiledModel(String comment, String name, String type,Map importClass) {
        this.comment = comment;
        this.name = name;
        this.type = type;
        this.importClass =importClass;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map getImportClass() {
        return importClass;
    }
}
