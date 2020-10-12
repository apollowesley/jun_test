package io.lemur.map.model.baidu.web.direction.walk.unique;

import io.lemur.map.model.baidu.web.direction.DirectionBaseModel;

/**
 * 步行 唯一终端起点
 * @author JueYue
 * @date 2015年1月27日
 */
public class DirectionOfWalkUniqueModel extends DirectionBaseModel {

    /**
     * 
     */
    private static final long                serialVersionUID = 1L;

    private DirectionOfWalkUniqueResultModel result;

    public DirectionOfWalkUniqueResultModel getResult() {
        return result;
    }

    public void setResult(DirectionOfWalkUniqueResultModel result) {
        this.result = result;
    }

}
