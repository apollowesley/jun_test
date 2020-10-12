package io.lemur.map.model.baidu.web.direction.car.unique;

import io.lemur.map.model.baidu.web.direction.DirectionBaseModel;

/**
 * Car 唯一终端起点
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionOfCarUniqueModel extends DirectionBaseModel {

    /**
     * 
     */
    private static final long               serialVersionUID = 1L;

    private DirectionOfCarUniqueResultModel result;

    public DirectionOfCarUniqueResultModel getResult() {
        return result;
    }

    public void setResult(DirectionOfCarUniqueResultModel result) {
        this.result = result;
    }

}
