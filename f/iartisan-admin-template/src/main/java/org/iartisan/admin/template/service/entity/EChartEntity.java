package org.iartisan.admin.template.service.entity;

import java.io.Serializable;

/**
 * <p>
 * charts entity
 *
 * @author King
 * @since 2018/4/10
 */
public class EChartEntity {

    private Serializable name;

    private Serializable value;

    public Serializable getName() {
        return name;
    }

    public void setName(Serializable name) {
        this.name = name;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }
}
