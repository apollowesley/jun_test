package com.example.bank.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 地理位置城市信息表
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_dict_citys", schema = "t_dict_citys")
public class Citys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "province_id", nullable = false)
    private long province_id;

    @Column(name = "city_name", nullable = false)
    private String city_name;
}
