package cn.backflow.admin.entity;

public class VersionUser {

    private Integer id;
    private Integer versionId;
    private Integer userId;
    private Integer type;

    public VersionUser() {

    }

    public VersionUser(Integer versionId, Integer userId, Integer type) {
        this.versionId = versionId;
        this.userId = userId;
        this.type = type;
    }


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVersionId(Integer value) {
        this.versionId = value;
    }

    public Integer getVersionId() {
        return this.versionId;
    }

    public void setUserId(Integer value) {
        this.userId = value;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setType(Integer value) {
        this.type = value;
    }

    public Integer getType() {
        return this.type;
    }

}