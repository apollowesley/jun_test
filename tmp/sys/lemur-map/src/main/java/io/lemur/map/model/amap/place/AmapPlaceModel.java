package io.lemur.map.model.amap.place;

import java.util.List;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-31 16:44
 * @version V1.0   
 */
public class AmapPlaceModel implements java.io.Serializable {

    private static final long        serialVersionUID = 1L;
    /**
     *  状态
     **/
    private String                   status;
    /**
     *  总数
     **/
    private String                   count;
    /**
     *  信息
     **/
    private String                   info;
    /**
     *  
     **/
    private List<AmapPlacePoisModel> pois;

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
     *@return: List<AmapPlacePoisModel>  
     */
    public List<AmapPlacePoisModel> getPois() {
        return this.pois;
    }

    /**
     *方法: 设置
     *@param: pois  
     */
    public void setPois(List<AmapPlacePoisModel> pois) {
        this.pois = pois;
    }
}
