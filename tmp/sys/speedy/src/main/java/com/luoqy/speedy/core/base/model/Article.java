package com.luoqy.speedy.core.base.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 文章系统
 * </p>
 *
 * @author qingfeng
 * @since 2019-09-07
 */
@TableName("speedy_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 副标题
     */
    private String subhead;
    /**
     * 类型（是否列表）
     */
    private Integer type;
    /**
     * 内容
     */
    private String content;
    private Date createtime;
    /**
     * 创建人
     */
    private Integer createuserid;
    private Date updatetime;
    private Integer updateuserid;
    /**
     * 推荐
     */
    private Integer stick;
    /**
     * 栏目类型（自定义）
     */
    private String columntype;
    /**
     * 首页显示
     */
    private Integer home;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(Integer createuserid) {
        this.createuserid = createuserid;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getUpdateuserid() {
        return updateuserid;
    }

    public void setUpdateuserid(Integer updateuserid) {
        this.updateuserid = updateuserid;
    }

    public Integer getStick() {
        return stick;
    }

    public void setStick(Integer stick) {
        this.stick = stick;
    }

    public String getColumntype() {
        return columntype;
    }

    public void setColumntype(String columntype) {
        this.columntype = columntype;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    @Override
    public String toString() {
        return "Article{" +
        ", id=" + id +
        ", title=" + title +
        ", subhead=" + subhead +
        ", type=" + type +
        ", content=" + content +
        ", createtime=" + createtime +
        ", createuserid=" + createuserid +
        ", updatetime=" + updatetime +
        ", updateuserid=" + updateuserid +
        ", stick=" + stick +
        ", columntype=" + columntype +
        ", home=" + home +
        "}";
    }
}
