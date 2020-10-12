package io.lemur.map.model.amap.bus;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-29 13:01
 * @version V1.0   
 */
public class AmapBusDirectionTransitsModel implements java.io.Serializable {

    private static final long                   serialVersionUID = 1L;
    /**
     *  
     **/
    private Object                              cost;
    /**
     *  
     **/
    private Object                              duration;
    /**
     *  
     **/
    private String                              nightflag;
    /**
     *  
     **/
    @JsonProperty("walking_distance")
    private String                              walkingDistance;
    /**
     *  
     **/
    private String                              missed;
    /**
     *  
     **/
    private List<AmapBusDirectionSegmentsModel> segments;

    /**
     *方法: 取得
     *@return: String  
     */
    public String getCost() {
        return (this.cost instanceof String) ? String.valueOf(this.cost) : "0";
    }

    /**
     *方法: 设置
     *@param: cost  
     */
    public void setCost(Object cost) {
        this.cost = cost;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public Object getDuration() {
        if (this.duration instanceof String)
            return String.valueOf(this.duration);
        return "";
    }

    public String getDurationStr() {
        if (this.duration instanceof String)
            return String.valueOf(this.duration);
        return "";
    }

    /**
     *方法: 设置
     *@param: duration  
     */
    public void setDuration(Object duration) {
        this.duration = duration;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getNightflag() {
        return this.nightflag;
    }

    /**
     *方法: 设置
     *@param: nightflag  
     */
    public void setNightflag(String nightflag) {
        this.nightflag = nightflag;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getWalkingDistance() {
        return this.walkingDistance;
    }

    /**
     *方法: 设置
     *@param: walkingDistance  
     */
    public void setWalkingDistance(String walkingDistance) {
        this.walkingDistance = walkingDistance;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getMissed() {
        return this.missed;
    }

    /**
     *方法: 设置
     *@param: missed  
     */
    public void setMissed(String missed) {
        this.missed = missed;
    }

    /**
     *方法: 取得
     *@return: List<AmapBusDirectionSegmentsModel>  
     */
    public List<AmapBusDirectionSegmentsModel> getSegments() {
        return this.segments;
    }

    /**
     *方法: 设置
     *@param: segments  
     */
    public void setSegments(List<AmapBusDirectionSegmentsModel> segments) {
        this.segments = segments;
    }
}
