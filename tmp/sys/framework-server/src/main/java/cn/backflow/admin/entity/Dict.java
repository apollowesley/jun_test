package cn.backflow.admin.entity;

import cn.backflow.admin.entity.base.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class Dict extends BaseEntity {
    public static final String TABLE_ALIAS = "数据字典";

    @NotBlank(message = "字典编码不能为空")
    @Length(min = 2, max = 32, message = "字典编码长度应为2到32之间")
    private String code;

    @Length(max = 256)
    private String description;

    private String key;

    private String value;

    private String comment;

    private int priority;

    public Dict() { }

    public Dict(String code, String description, String key, String value, String comment, int priority) {
        setDescription(description);
        setPriority(priority);
        setComment(comment);
        setValue(value);
        setCode(code);
        setKey(key);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int value) {
        this.priority = value;
    }
}