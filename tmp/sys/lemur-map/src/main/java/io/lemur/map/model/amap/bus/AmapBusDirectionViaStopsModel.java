package io.lemur.map.model.amap.bus;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-29 13:01
 * @version V1.0   
 */
public class AmapBusDirectionViaStopsModel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *  
     **/
    private String            name;
    /**
     *  
     **/
    private String            id;
    /**
     *  
     **/
    private String            location;

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
    public String getLocation() {
        return this.location;
    }

    /**
     *方法: 设置
     *@param: location  
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
