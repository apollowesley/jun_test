package io.lemur.map.model.baidu.web.placesuggestion;

import java.util.List;

import io.lemur.map.model.baidu.web.base.BaiduBaseModel;

/**
 * Place suggestion API 是一套以HTTP形式提供的匹配用户输入关键字辅助信息、提示接口，可返回json或xml格式的一组建议词条的数据。
 * 
                        参数名称                是否必须                 默认值        格式                                                                               备注
        q(query)    是                           无            上地、天安、中关、shanghai           输入建议关键字（支持拼音）
        region      是                           无           全国、北京市、131、江苏省等                                 所属城市/区域名称或代号
        output      否                         xml     json、xml                    返回数据格式，可选json、xml两种
        ak          是                            无              E4805d16520de693a3fe707cdc962045    开发者访问密钥，必选。
        sn          否                           无                用户的权限签名
        timestamp   否                           无                设置sn后该值必选
 * @author JueYue
 * @date 2015年1月26日
 */
public class PlaceSuggestionModel extends BaiduBaseModel {

    /**
     * 
     */
    private static final long                serialVersionUID = 1L;

    private List<PlaceSuggestionDetailModel> result;

    public List<PlaceSuggestionDetailModel> getResult() {
        return result;
    }

    public void setResult(List<PlaceSuggestionDetailModel> result) {
        this.result = result;
    }

}
