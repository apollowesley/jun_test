package pojo;

public class Type {
    private Integer id;
    private String item;
    private Integer level;
    private String src;

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", level=" + level +
                ", src='" + src + '\'' +
                '}';
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getLevel() {
        return level;
    }
}
