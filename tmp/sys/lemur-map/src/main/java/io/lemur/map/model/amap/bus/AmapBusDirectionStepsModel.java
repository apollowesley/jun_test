package io.lemur.map.model.amap.bus;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-29 13:01
 * @version V1.0   
 */
public class AmapBusDirectionStepsModel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *  
     **/
    private Object            instruction;
    /**
     *  道路
     **/
    private Object            road;
    /**
     *  
     **/
    private String            distance;
    /**
     *  
     **/
    private String            polyline;
    /**
     *  方向
     **/
    private Object            action;

    /**
     *方法: 取得
     *@return: String  
     */
    public Object getInstruction() {
        if (this.instruction instanceof String)
            return String.valueOf(this.instruction);
        return "";
    }

    public Object getInstructionStr() {
        if (this.instruction instanceof String)
            return String.valueOf(this.instruction);
        return "";
    }

    /**
     *方法: 设置
     *@param: instruction  
     */
    public void setInstruction(Object instruction) {
        this.instruction = instruction;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public Object getRoad() {
        if (road instanceof String)
            return String.valueOf(road);
        return "";
    }

    public Object getRoadStr() {
        if (road instanceof String)
            return String.valueOf(road);
        return "";
    }

    /**
     *方法: 设置
     *@param: road  
     */
    public void setRoad(Object road) {
        this.road = road;
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
    public String getPolyline() {
        return this.polyline;
    }

    /**
     *方法: 设置
     *@param: polyline  
     */
    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public Object getAction() {
        if (action instanceof String)
            return String.valueOf(action);
        return "";
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getActionStr() {
        if (action instanceof String)
            return String.valueOf(action);
        return "";
    }

    /**
     *方法: 设置
     *@param: action  
     */
    public void setAction(Object action) {
        this.action = action;
    }

}
