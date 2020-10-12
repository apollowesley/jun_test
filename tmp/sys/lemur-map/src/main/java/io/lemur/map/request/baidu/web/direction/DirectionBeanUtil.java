package io.lemur.map.request.baidu.web.direction;

import io.lemur.common.util.json.JSONUtil;
import io.lemur.map.model.baidu.web.direction.DirectionBaseModel;

/**
 * 导航对象转换类
 * @author JueYue
 * @date 2015年1月27日
 */
public final class DirectionBeanUtil {

    private DirectionBeanUtil() {

    }

    /**
     * 是否准确的
     * @param json
     * @return
     */
    public static final boolean isInconclusive(String json) {
        DirectionBaseModel model = JSONUtil.parseJson(json, DirectionBaseModel.class);
        return model.getType() == 2;
    }

}
