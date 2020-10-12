package com.foo.common.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "foo_user")
public class FooUser {
    @Id
    @Column(name = "id", length = 36)
    @javax.persistence.GeneratedValue(generator = "self")
    @org.hibernate.annotations.GenericGenerator(name = "self",
            strategy = "assigned")
    private String id;

    @Column(nullable = true)
    private Date time = new Date();

    @Column(nullable = true, length = 50)
    private String name = "";

    @Column(nullable = true, columnDefinition = "INT default 18")
    private int age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
