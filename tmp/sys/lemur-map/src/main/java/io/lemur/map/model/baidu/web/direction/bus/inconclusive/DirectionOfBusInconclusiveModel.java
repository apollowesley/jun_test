package io.lemur.map.model.baidu.web.direction.bus.inconclusive;

import io.lemur.map.model.baidu.web.direction.DirectionBaseModel;

/**
 * 公交—起/终点模糊检索返回值说明
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionOfBusInconclusiveModel extends DirectionBaseModel {

    /**
     * 
     */
    private static final long                     serialVersionUID = 1L;

    private DirectionOfBusInconclusiveResultModel result;

    public DirectionOfBusInconclusiveResultModel getResult() {
        return result;
    }

    public void setResult(DirectionOfBusInconclusiveResultModel result) {
        this.result = result;
    }

}
