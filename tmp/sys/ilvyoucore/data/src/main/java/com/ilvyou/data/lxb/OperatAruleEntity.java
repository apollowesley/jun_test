package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class OperatAruleEntity {
    private String rulecode;
    private String actioncode;
    private String tablename;
    private String columnname;
    private int parm1;
    private int parm2;
    private int parm3;
    private String formula;
    private String remark;

}
