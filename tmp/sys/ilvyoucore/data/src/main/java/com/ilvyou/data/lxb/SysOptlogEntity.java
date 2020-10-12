package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class SysOptlogEntity {
    private long pkid;
    private long userid;
    private String username;
    private String realname;
    private String opttag;
    private int opttype;
    private String desc;
    private String hostname;
    private Timestamp createdon;
    private String createdip;
}
