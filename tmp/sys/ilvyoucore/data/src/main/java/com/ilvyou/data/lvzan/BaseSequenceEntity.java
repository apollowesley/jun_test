package com.ilvyou.data.lvzan;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created by GuanYuCai on 16/9/16.
 */
@Data
public class BaseSequenceEntity {
    private String tablename;
    private String columnname;
    private String formats;
    private Short sequencetype;
    private Integer datalength;
    private Long lastsequence;
    private Timestamp updatetimestamp;
    private String remark;
}
