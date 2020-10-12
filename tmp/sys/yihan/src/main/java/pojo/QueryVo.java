package pojo;

public class QueryVo {
    private Integer id;
    private String name;
    private Integer type;
    private Integer currentPage = 1;
    private Integer total;
    private Integer limitPage = 8;
    private Integer limitLine = 4;

    @Override
    public String toString() {
        return "QueryVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", currentPage=" + currentPage +
                ", total=" + total +
                ", limitPage=" + limitPage +
                ", limitLine=" + limitLine +
                '}';
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getLimitPage() {
        return limitPage;
    }

    public void setLimitPage(Integer limitPage) {
        this.limitPage = limitPage;
    }

    public Integer getLimitLine() {
        return limitLine;
    }

    public void setLimitLine(Integer limitLine) {
        this.limitLine = limitLine;
    }
}

