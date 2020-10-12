package cn.backflow.admin.entity;

public class VersionChannel {

    private Integer id;
    private Integer versionId;
    private String code;

    public VersionChannel() {
    }

    public VersionChannel(Integer versionId, String code) {
        this.versionId = versionId;
        this.code = code;
    }

    public Integer getId() {
        return id;
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}