package com.itmuch.gen.db.entity.impl;

import java.util.List;

import com.itmuch.gen.db.entity.Field;
import com.itmuch.gen.db.entity.Model;

public class EntityModel extends Model {
    private Boolean serializable;
    // 类中的属性
    private List<Field> fields;

    public List<Field> getFields() {
        return this.fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Boolean getSerializable() {
        return this.serializable;
    }

    public void setSerializable(Boolean serializable) {
        this.serializable = serializable;
    }

    @Override
    public String toString() {
        return "Model [serializable=" + this.serializable + "]";
    }

}
