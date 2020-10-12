package db.model;

import java.util.Map;

/**
 * Created by eason on 2017/8/23.
 */
public class TableFiledModel {
    //字段名称
    String  filedName;
    //字段类型
    String  filedType;
    //字段注释
    String  filedComment;
    //引用类
    Map importClass;

    public TableFiledModel(String filedName, String filedType, String filedComment,Map importClass) {
        this.filedName = filedName;
        this.filedType = filedType;
        this.filedComment = filedComment;
        this.importClass =importClass;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getFiledType() {
        return filedType;
    }

    public void setFiledType(String filedType) {
        this.filedType = filedType;
    }

    public String getFiledComment() {
        return filedComment;
    }

    public void setFiledComment(String filedComment) {
        this.filedComment = filedComment;
    }

    public Map getImportClass() {
        return importClass;
    }

    public void setImportClass(Map importClass) {
        this.importClass = importClass;
    }
}
