package com.zhengjieblog.pocket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

/**
 * @author 郑杰
 * @date 2018-7-18
 */
@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @Column(unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 30,nullable = false)
    private String openid;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(nullable = false)
    private Boolean enabled;

    @JsonIgnore
    @Column(unique = true,length = 60,nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Pocket> pockets;
}
