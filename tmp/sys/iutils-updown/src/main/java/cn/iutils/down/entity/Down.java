package cn.iutils.down.entity;

import cn.iutils.sys.entity.DataEntity;

/**
* 反对表
* @author iutils.cn
* @version 1.0
*/
public class Down extends DataEntity<Down>{

    private static final long serialVersionUID = 1L;

    private String channel;//渠道
    private String contentId;//内容编号
    private int hits=0;//点击数
    private String operator="";//操作人

    public Down() {
        super();
    }
    public Down(String id){
        super(id);
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

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
