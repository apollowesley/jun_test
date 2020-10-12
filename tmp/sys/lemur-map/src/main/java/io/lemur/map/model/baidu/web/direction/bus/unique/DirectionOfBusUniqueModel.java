package io.lemur.map.model.baidu.web.direction.bus.unique;

import io.lemur.map.model.baidu.web.direction.DirectionBaseModel;

/**
 * 公交 唯一终端起点
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionOfBusUniqueModel extends DirectionBaseModel {

    /**
     * 
     */
    private static final long               serialVersionUID = 1L;

    private DirectionOfBusUniqueResultModel result;

    public DirectionOfBusUniqueResultModel getResult() {
        return result;
    }

    public void setResult(DirectionOfBusUniqueResultModel result) {
        this.result = result;
    }

}
