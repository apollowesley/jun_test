package pojo;

public class Img {
    private Integer id;
    private String name;
    private Integer width;
    private Integer height;

    @Override
    public String toString() {
        return "Img{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    public Img(String name, Integer width, Integer height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
