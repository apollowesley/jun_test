package com.pflm.modules.wechat.entity;
import java.io.Serializable;

/**
 * 微信自定义菜单
 * @author qinxuewu
 * @version 1.00
 * @time 16/11/2018下午 2:55
 */
public class WxMenuEntity implements Serializable {
    private Long id;
    private String name;
    private String createTime;
    private String updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
