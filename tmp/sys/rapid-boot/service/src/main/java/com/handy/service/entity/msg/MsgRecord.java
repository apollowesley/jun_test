package com.handy.service.entity.msg;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/28 13:57
 */
@Data
@Accessors(chain = true)
public class MsgRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long category;
    private String categoryName;
    private String mobile;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date sentTime;
    private Boolean result;
    private String resultDesc;
    private Date createTime;
}
