package io.lemur.map.model.amap.bus;

import java.util.List;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-29 13:01
 * @version V1.0   
 */
public class AmapBusDirectionWalkingModel implements java.io.Serializable {

    private static final long                serialVersionUID = 1L;
    /**
     *  
     **/
    private String                           origin;
    /**
     *  
     **/
    private String                           destination;
    /**
     *  
     **/
    private String                           distance;
    /**
     *  
     **/
    private String                           duration;
    /**
     *  
     **/
    private List<AmapBusDirectionStepsModel> steps;

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
     *@return: List<AmapBusDirectionStepsModel>  
     */
    public List<AmapBusDirectionStepsModel> getSteps() {
        return this.steps;
    }

    /**
     *方法: 设置
     *@param: steps  
     */
    public void setSteps(List<AmapBusDirectionStepsModel> steps) {
        this.steps = steps;
    }
}
