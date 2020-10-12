package io.lemur.map.model.amap.bus;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-29 13:01
 * @version V1.0   
 */
public class AmapBusDirectionModel implements java.io.Serializable {

    private static final long          serialVersionUID = 1L;
    /**
     *  
     **/
    private String                     status;
    /**
     *  
     **/
    private String                     info;
    /**
     *  
     **/
    private String                     count;
    /**
     *  
     **/
    private AmapBusDirectionRouteModel route;

    /**
     *方法: 取得
     *@return: String  
     */
    public String getStatus() {
        return this.status;
    }

    /**
     *方法: 设置
     *@param: status  
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getInfo() {
        return this.info;
    }

    /**
     *方法: 设置
     *@param: info  
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public String getCount() {
        return this.count;
    }

    /**
     *方法: 设置
     *@param: count  
     */
    public void setCount(String count) {
        this.count = count;
    }

    /**
     *方法: 取得
     *@return: AmapBusDirectionRouteModel  
     */
    public AmapBusDirectionRouteModel getRoute() {
        return this.route;
    }

    /**
     *方法: 设置
     *@param: route  
     */
    public void setRoute(AmapBusDirectionRouteModel route) {
        this.route = route;
    }
}
