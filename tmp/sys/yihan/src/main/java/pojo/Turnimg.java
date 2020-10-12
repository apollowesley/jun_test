package pojo;

public class Turnimg {
    private Integer id;
    private String src;
    private String text;
    private Integer used;
    private Integer level;
    private String href;

    @Override
    public String toString() {
        return "Turnimg{" +
                "id=" + id +
                ", src='" + src + '\'' +
                ", text='" + text + '\'' +
                ", used=" + used +
                ", level=" + level +
                ", href='" + href + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
