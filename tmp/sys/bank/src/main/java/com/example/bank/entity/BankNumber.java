package com.example.bank.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 联行号实体
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_bank_number", schema = "t_bank_number")
public class BankNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;//

    @Column(name = "bank_id", nullable = false)
    private Long bank_id;//银行名称id

    @Column(name = "province_id", nullable = false)
    private Long province_id;//省份id

    @Column(name = "city_id", nullable = false)
    private Long city_id;//城市id

    @Column(name = "bank_number", nullable = false)
    private Long bank_number;//联行号

    @Column(name = "bank_branch", nullable = false)
    private String bank_branch;//开户支行


}
