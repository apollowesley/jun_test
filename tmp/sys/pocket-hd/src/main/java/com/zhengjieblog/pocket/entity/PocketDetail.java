package com.zhengjieblog.pocket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
public class PocketDetail {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Double money;

    @Column(nullable = false)
    //类别 交通 or 餐饮
    private String type;

    @Column(nullable = false)
    //支出 or 收入
    private String pocketType;

    @Column(name = "pay_method")
    //付款方式
    private String payMethod;

    //备注
    private String remark;

    //新增时间
    private Date time;

    private String house;

    @ManyToOne
    @JoinColumn(name = "pocket_id")
    @JsonIgnore
    private Pocket pocket;
}
