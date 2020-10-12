package io.lemur.map.model.amap.bus;

import java.util.Map;

import io.lemur.common.util.json.JSONUtil;

/**   
 * @Description:  
 * @author Lemur
 * @date 2015-01-29 13:01
 * @version V1.0   
 */
public class AmapBusDirectionSegmentsModel implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *  
     **/
    private Object            walking;
    /**
     *  
     **/
    private Object            bus;

    /**
     *方法: 取得
     *@return: AmapBusDirectionWalkingModel  
     */
    public AmapBusDirectionWalkingModel getWalking() {
        if (walking instanceof Map)
            return JSONUtil.parseJson(JSONUtil.toJson(walking), AmapBusDirectionWalkingModel.class);
        return null;
    }

    /**
     *方法: 设置
     *@param: walking  
     */
    public void setWalking(Object walking) {
        this.walking = walking;
    }

    /**
     *方法: 取得
     *@return: AmapBusDirectionBusModel  
     */
    public AmapBusDirectionBusModel getBus() {
        if (bus instanceof Map)
            return JSONUtil.parseJson(JSONUtil.toJson(bus), AmapBusDirectionBusModel.class);
        return null;
    }

    /**
     *方法: 设置
     *@param: bus  
     */
    public void setBus(Object bus) {
        this.bus = bus;
    }

}
