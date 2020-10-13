package boot.spring.po;

import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

@Excel("测试实体")
public class TestVo {
    @ExcelField(value = "编号", width = 30)
    private Integer id;

    @ExcelField(value = "唯一标识")
    private String uid;

    @ExcelField(value = "名称", maxLength = 32)
    private String name;

    @ExcelField(value = "数值")
    private String num;

    @ExcelField(value = "类型")
    private byte type;

    @ExcelField(value = "创建日期")
    private String createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
