package com.example.bank.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 地理位置省份信息表
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_dict_provinces", schema = "t_dict_provinces")
public class Provinces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "provinces_name", nullable = false)
    private String provinces_name;//省份


}
