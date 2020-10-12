package cn.iutils.guestbook.entity.enums;

/**
 * 留言分类
 * @author cc
 */
public enum GuestBookEnum {

    advise("咨询"),suggest("建议"), complain("投诉"),other("其他");

    private final String info;

    private GuestBookEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
