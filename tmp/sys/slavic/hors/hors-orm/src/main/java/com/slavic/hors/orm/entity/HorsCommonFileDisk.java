package com.slavic.hors.orm.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (HorsCommonFileDisk)实体类
 *
 * @author makejava
 * @since 2020-04-03 14:07:49
 */
@Data
public class HorsCommonFileDisk implements Serializable {
    private static final long serialVersionUID = -53806923984577137L;
    /**
    * 主键ID
    */
    private Long id;
    /**
    * 文件类型: PORTAL_AVATAR 后台用户头像
    */
    private String fileType;
    /**
    * 文件名
    */
    private String fileName;
    /**
    * 文件内容 BASE64形式
    */
    private String fileContent;
    /**
    * 文件状态: 0正常 -1删除
    */
    private Integer fileStatus;
    /**
    * 文件上传时间
    */
    private Date createTime;

}