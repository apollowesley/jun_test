package pojo;

public class Item {
    private Integer id;
    private String src;
    private String name;
    private String type1;
    private Integer type;
    private String author;
    private String date;
    private String text;
    private Integer level;
    private Integer width=1;
    private Integer height=1;
    private Integer width1;
    private Integer height1;

    @Override
    public String toString() {
        return "\n\tItem{" +
                "id=" + id +
                ", src='" + src + '\'' +
                ", name='" + name + '\'' +
                ", type1='" + type1 + '\'' +
                ", type=" + type +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                ", level=" + level +
                ", width=" + width +
                ", height=" + height +
                ", width1=" + width1 +
                ", height1=" + height1 +
                '}';
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth1() {
        return width1;
    }

    public void setWidth1(Integer width1) {
        this.width1 = width1;
    }

    public Integer getHeight1() {
        return height1;
    }

    public void setHeight1(Integer height1) {
        this.height1 = height1;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
