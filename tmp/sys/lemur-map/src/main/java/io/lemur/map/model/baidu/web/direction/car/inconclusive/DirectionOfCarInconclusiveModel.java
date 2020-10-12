package io.lemur.map.model.baidu.web.direction.car.inconclusive;

import io.lemur.map.model.baidu.web.direction.DirectionBaseModel;

/**
 * 驾车—起/终点模糊检索返回值说明
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionOfCarInconclusiveModel extends DirectionBaseModel {

    /**
     * 
     */
    private static final long                     serialVersionUID = 1L;

    private DirectionOfCarInconclusiveResultModel result;

    public DirectionOfCarInconclusiveResultModel getResult() {
        return result;
    }

    public void setResult(DirectionOfCarInconclusiveResultModel result) {
        this.result = result;
    }

}
