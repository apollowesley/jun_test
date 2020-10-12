package io.lemur.map.model.amap.bus;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-29 13:01
 * @version V1.0   
 */
public class AmapBusDirectionBuslinesModel implements java.io.Serializable {

    private static final long                   serialVersionUID = 1L;
    /**
     *  
     **/
    @JsonProperty("departure_stop")
    private AmapBusDirectionDepartureStopModel  departureStop;
    /**
     *  
     **/
    @JsonProperty("arrival_stop")
    private AmapBusDirectionArrivalStopModel    arrivalStop;
    /**
     *  
     **/
    private String                              name;
    /**
     *  
     **/
    private String                              id;
    /**
     *  
     **/
    private String                              type;
    /**
     *  
     **/
    private String                              distance;
    /**
     *  
     **/
    private String                              duration;
    /**
     *  
     **/
    private Object                              polyline;
    /**
     *  
     **/
    @JsonProperty("start_time")
    private String                              startTime;
    /**
     *  
     **/
    @JsonProperty("end_time")
    private String                              endTime;
    /**
     *  
     **/
    @JsonProperty("via_num")
    private String                              viaNum;
    /**
     *  
     **/
    @JsonProperty("via_stops")
    private List<AmapBusDirectionViaStopsModel> viaStops;

    /**
     *方法: 取得
     *@return: AmapBusDirectionDepartureStopModel  
     */
    public AmapBusDirectionDepartureStopModel getDepartureStop() {
        return this.departureStop;
    }

    /**
     *方法: 设置
     *@param: departureStop  
     */
    public void setDepartureStop(AmapBusDirectionDepartureStopModel departureStop) {
        this.departureStop = departureStop;
    }

    /**
     *方法: 取得
     *@return: AmapBusDirectionArrivalStopModel  
     */
    public AmapBusDirectionArrivalStopModel getArrivalStop() {
        return this.arrivalStop;
    }

    /**
     *方法: 设置
     *@param: arrivalStop  
     */
    public void setArrivalStop(AmapBusDirectionArrivalStopModel arrivalStop) {
        this.arrivalStop = arrivalStop;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getName() {
        return this.name;
    }

    /**
     *方法: 设置
     *@param: name  
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getId() {
        return this.id;
    }

    /**
     *方法: 设置
     *@param: id  
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getType() {
        return this.type;
    }

    /**
     *方法: 设置
     *@param: type  
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getDistance() {
        return this.distance;
    }

    /**
     *方法: 设置
     *@param: distance  
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getDuration() {
        return this.duration;
    }

    /**
     *方法: 设置
     *@param: duration  
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public Object getPolyline() {
        if (this.polyline instanceof String)
            return String.valueOf(this.polyline);
        return "";
    }

    public String getPolylineStr() {
        if (this.polyline instanceof String)
            return String.valueOf(this.polyline);
        return "";
    }

    /**
     *方法: 设置
     *@param: polyline  
     */
    public void setPolyline(Object polyline) {
        this.polyline = polyline;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     *方法: 设置
     *@param: startTime  
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     *方法: 设置
     *@param: endTime  
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getViaNum() {
        return this.viaNum;
    }

    /**
     *方法: 设置
     *@param: viaNum  
     */
    public void setViaNum(String viaNum) {
        this.viaNum = viaNum;
    }

    /**
     *方法: 取得
     *@return: List<AmapBusDirectionViaStopsModel>  
     */
    public List<AmapBusDirectionViaStopsModel> getViaStops() {
        return this.viaStops;
    }

    /**
     *方法: 设置
     *@param: viaStops  
     */
    public void setViaStops(List<AmapBusDirectionViaStopsModel> viaStops) {
        this.viaStops = viaStops;
    }
}
