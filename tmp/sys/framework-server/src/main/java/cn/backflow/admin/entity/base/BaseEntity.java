package cn.backflow.admin.entity.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 246776097603372293L;

    protected Integer id;

    public Integer getId() {
        return this.id;
    }

    public BaseEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj)
            return false;

        if (this == obj)
            return true;

        if (!(super.getClass().equals(obj.getClass())))
            return false;

        BaseEntity that = (BaseEntity) obj;

        return (null != getId() && getId().equals(that.getId()));
    }

    @Override
    public int hashCode() {
        int hashCode = 17;

        hashCode += ((null == getId()) ? 0 : getId().hashCode() * 31);

        return hashCode;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}