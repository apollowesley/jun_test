package io.lemur.map.model.amap.bus;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-29 13:01
 * @version V1.0   
 */
public class AmapBusDirectionRouteModel implements java.io.Serializable {

    private static final long                   serialVersionUID = 1L;
    /**
     *  
     **/
    private String                              origin;
    /**
     *  
     **/
    private String                              destination;
    /**
     *  
     **/
    private Object                              distance;
    /**
     *  
     **/
    @JsonProperty("taxi_cost")
    private Object                              taxiCost;
    /**
     *  
     **/
    private List<AmapBusDirectionTransitsModel> transits;

    /**
     *方法: 取得
     *@return: String  
     */
    public String getOrigin() {
        return this.origin;
    }

    /**
     *方法: 设置
     *@param: origin  
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getDestination() {
        return this.destination;
    }

    /**
     *方法: 设置
     *@param: destination  
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public Object getDistance() {
        if (this.distance instanceof String)
            return String.valueOf(this.distance);
        return "";
    }

    /**
     *方法: 设置
     *@param: distance  
     */
    public void setDistance(Object distance) {
        this.distance = distance;
    }

    public String getDistanceStr() {
        if (this.distance instanceof String)
            return String.valueOf(this.distance);
        return "";
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public Object getTaxiCost() {
        if (this.taxiCost instanceof String)
            return String.valueOf(this.taxiCost);
        return "0";
    }

    /**
     *方法: 设置
     *@param: taxiCost  
     */
    public void setTaxiCost(Object taxiCost) {
        this.taxiCost = taxiCost;
    }
    
    public String getTaxiCostStr() {
        if (this.taxiCost instanceof String)
            return String.valueOf(this.taxiCost);
        return "";
    }

    /**
     *方法: 取得
     *@return: List<AmapBusDirectionTransitsModel>  
     */
    public List<AmapBusDirectionTransitsModel> getTransits() {
        return this.transits;
    }

    /**
     *方法: 设置
     *@param: transits  
     */
    public void setTransits(List<AmapBusDirectionTransitsModel> transits) {
        this.transits = transits;
    }
}
