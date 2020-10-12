package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class BaseSequenceEntity {
    private String tablename;
    private String columnname;
    private String formats;
    private Integer sequencetype;
    private Integer datalength;
    private String lastsequence;
    private Timestamp updatetimestamp;
    private String remark;
}
