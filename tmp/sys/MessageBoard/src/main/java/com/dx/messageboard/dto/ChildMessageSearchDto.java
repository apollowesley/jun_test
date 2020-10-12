package com.dx.messageboard.dto;

/**
 * 留言回复数据传输类
 * Create by zhoushiyu
 */
public class ChildMessageSearchDto {

    private int parentId;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
