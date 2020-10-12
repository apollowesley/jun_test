package pojo;

public class Detail {
    private Integer id;
    private Integer itemid;
    private Integer type1;
    private Integer level;
    private String type2;
    private String item1;
    private String item2;

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", itemid=" + itemid +
                ", type1=" + type1 +
                ", level=" + level +
                ", type2='" + type2 + '\'' +
                ", item1='" + item1 + '\'' +
                ", item2='" + item2 + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public Integer getType1() {
        return type1;
    }

    public void setType1(Integer type1) {
        this.type1 = type1;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }
}
