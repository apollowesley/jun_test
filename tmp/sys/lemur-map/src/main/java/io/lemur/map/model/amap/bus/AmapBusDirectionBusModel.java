package io.lemur.map.model.amap.bus;

import java.util.List;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-29 13:01
 * @version V1.0   
 */
public class AmapBusDirectionBusModel implements java.io.Serializable {

    private static final long                   serialVersionUID = 1L;
    /**
     *  
     **/
    private List<AmapBusDirectionBuslinesModel> buslines;

    /**
     *方法: 取得
     *@return: List<AmapBusDirectionBuslinesModel>  
     */
    public List<AmapBusDirectionBuslinesModel> getBuslines() {
        return this.buslines;
    }

    /**
     *方法: 设置
     *@param: buslines  
     */
    public void setBuslines(List<AmapBusDirectionBuslinesModel> buslines) {
        this.buslines = buslines;
    }
}
