package com.foo.common.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "foo_user_trip")
public class FooUserTrip {
    @Id
    @Column(name = "id", length = 36)
    @javax.persistence.GeneratedValue(generator = "self")
    @org.hibernate.annotations.GenericGenerator(name = "self",
            strategy = "assigned")
    private String id;

    @Column(nullable = false, length = 36)
    private String userId = "";

    @Column(nullable = true)
    private Date time = new Date();

    @Column(nullable = true, length = 50)
    private String name = "";


    @Column(nullable = true)
    private int miles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }
}
