package com.example.bank.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 银行实体
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_bank_name", schema = "t_bank_name")
public class Bank implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "bank_name", nullable = false)
    private String bank_name;//银行名称


}
