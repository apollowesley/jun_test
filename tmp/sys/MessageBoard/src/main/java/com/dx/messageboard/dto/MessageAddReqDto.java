package com.dx.messageboard.dto;

/**
 * 留言请求数据传输类
 * Create by zhoushiyu
 */
public class MessageAddReqDto {

    private String title;

    private String context;

    private int userId;

    private String userName;

    private Integer parentId;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "MessageReqDto{" +
                "title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", userId=" + userId +
                ", username='" + userName + '\'' +
                '}';
    }
}
