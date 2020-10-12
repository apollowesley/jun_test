package io.lemur.map.model.baidu.web.direction.walk.inconclusive;

import io.lemur.map.model.baidu.web.direction.DirectionBaseModel;

/**
 * 步行—起/终点模糊检索返回值说明
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionOfWalkInconclusiveModel extends DirectionBaseModel {

    /**
     * 
     */
    private static final long                      serialVersionUID = 1L;

    private DirectionOfWalkInconclusiveResultModel result;

    public DirectionOfWalkInconclusiveResultModel getResult() {
        return result;
    }

    public void setResult(DirectionOfWalkInconclusiveResultModel result) {
        this.result = result;
    }

}
