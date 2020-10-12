package io.lemur.map.model.amap.place;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-31 16:44
 * @version V1.0   
 */
public class AmapPlacePoisModel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *  
     **/
    private String            id;
    /**
     *  地点名称
     **/
    private String            name;
    /**
     *  地点类型
     **/
    private String            type;
    /**
     *  类型编码
     **/
    private String            typecode;
    /**
     *  地址
     **/
    private Object            address;
    /**
     *  经纬度
     **/
    private String            location;

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
    public String getTypecode() {
        return this.typecode;
    }

    /**
     *方法: 设置
     *@param: typecode  
     */
    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    /**
     *方法: 取得
     *@return: String  
     */
    public Object getAddress() {
        return this.address;
    }

    public String getAddressStr() {
        if (this.address instanceof String) {
            return this.address.toString();
        }
        return "";
    }

    /**
     *方法: 设置
     *@param: address  
     */
    public void setAddress(Object address) {
        this.address = address;
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
