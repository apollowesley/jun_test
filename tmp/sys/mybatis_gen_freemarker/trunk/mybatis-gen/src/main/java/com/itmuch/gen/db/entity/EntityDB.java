package com.itmuch.gen.db.entity;

public class EntityDB {

    private String columnName;
    private String columnType;
    private String comment;

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return this.columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "EntityDB [columnName=" + this.columnName + ", columnType=" + this.columnType + ", comment=" + this.comment + "]";
    }

}
