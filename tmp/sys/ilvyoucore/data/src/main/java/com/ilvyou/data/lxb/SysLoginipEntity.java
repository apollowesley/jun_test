package com.ilvyou.data.lxb;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by admin on 2016/11/7.
 */
@Data
public class SysLoginipEntity {
    private long pkid;
    private long userid;
    private String username;
    private String realname;
    private String loginip;
    private String hostname;
    private Timestamp createdon;
    private String createdip;

}
