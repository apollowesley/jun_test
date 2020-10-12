package com.ilvyou.data.lhf;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/16.
 */
@Data
public class ScrAnswerEntity {
    private Long id;
    private Long actid;
    private String actname;
    private String question;
    private String answer;
    private Timestamp createdon;
    private String createdname;
    private Long createdby;
    private String createdip;
    private String lastedname;
    private Timestamp lastedon;
    private Long lastedby;
    private String lastedip;
}
