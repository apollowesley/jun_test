package cn.iutils.guestbook.entity;

import cn.iutils.guestbook.entity.enums.GuestBookEnum;
import cn.iutils.sys.entity.DataEntity;

/**
* 留言板
* @author iutils.cn
* @version 1.0
*/
public class Guestbook extends DataEntity<Guestbook>{

    private static final long serialVersionUID = 1L;

    private String channel;//渠道
    private GuestBookEnum type=GuestBookEnum.advise;//留言分类
    private String content;//留言内容
    private String name;//姓名
    private String email;//邮箱
    private String phone;//电话
    private int report = 0;//举报
    private String ip;//IP
    private String reContent;//回复内容

    private int upCount;//赞
    private int downCount;//踩

    public Guestbook() {
        super();
    }
    public Guestbook(String id){
        super(id);
    }

    public String getChannel(){
        return channel;
    }

    public void setChannel(String channel){
        this.channel = channel;
    }

    public GuestBookEnum getType() {
        return type;
    }

    public void setType(GuestBookEnum type) {
        this.type = type;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public String getIp(){
        return ip;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public String getReContent(){
        return reContent;
    }

    public void setReContent(String reContent){
        this.reContent = reContent;
    }

    public int getUpCount() {
        return upCount;
    }

    public void setUpCount(int upCount) {
        this.upCount = upCount;
    }

    public int getDownCount() {
        return downCount;
    }

    public void setDownCount(int downCount) {
        this.downCount = downCount;
    }
}
