package com.zhengjieblog.pocket.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018-7-18
 */
@Table(name = "pocket")
@Entity
@Getter
@Setter
public class Pocket {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "pocket")
    private List<PocketDetail> pocketDetails;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer day;

    @Column(nullable = false)
    private String weekDay;

    private Double outlay=0D;

    private Double income=0D;

    private Double balance=0D;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;
}
