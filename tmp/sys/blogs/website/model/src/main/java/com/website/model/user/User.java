package com.website.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
 * @Author: xiaokai
 * @Description:
 * @Date: $date$
 * @Version: 1.0
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "users", schema = "website", catalog = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer  id;
    @Basic
    @Column(name = "code")
    private  String code;
    @Basic
    @Column(name = "username")
    private  String username;
    @Basic
    @Column(name = "password")
    private  String password;
    @Basic
    @Column(name = "register_time")
    private  String registerTime;
    @Basic
    @Column(name = "imgpath")
    private  String imgpath;
}
