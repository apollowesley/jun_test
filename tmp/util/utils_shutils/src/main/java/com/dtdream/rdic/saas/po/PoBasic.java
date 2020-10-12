package com.dtdream.rdic.saas.po;
import javax.persistence.*;

/**
 * Created by Ozz8 on 2015/06/15.
 */
@MappedSuperclass
public abstract class PoBasic {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    long id;
    @Column(name = "version", columnDefinition = "bigint NOT NULL COMMENT '最后更新时间'")
    long version;
    @Column(name = "isdel", columnDefinition = "integer NOT NULL default 0")
    long isdel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getIsdel() {
        return isdel;
    }

    public void setIsdel(long isdel) {
        this.isdel = isdel;
    }
}
