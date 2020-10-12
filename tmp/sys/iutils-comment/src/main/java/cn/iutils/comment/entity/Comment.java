package cn.iutils.comment.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 评论表
* @author iutils.cn
* @version 1.0
*/
public class Comment extends DataEntity<Comment>{

    private static final long serialVersionUID = 1L;

    private String parentId="0";//父类编号
    private String parentIds="0/";//父类编号集合
    private String channel;//渠道
    private String contentId;//内容编号
    private String content;//评论内容
    private int report = 0;//举报
    private String ip;//IP

    private int upCount;//赞
    private int downCount;//踩

    public Comment() {
        super();
    }
    public Comment(String id){
        super(id);
    }

    public String getParentId(){
        return parentId;
    }

    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public String getParentIds(){
        return parentIds;
    }

    public void setParentIds(String parentIds){
        this.parentIds = parentIds;
    }

    public String getChannel(){
        return channel;
    }

    public void setChannel(String channel){
        this.channel = channel;
    }

    public String getContentId(){
        return contentId;
    }

    public void setContentId(String contentId){
        this.contentId = contentId;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
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
