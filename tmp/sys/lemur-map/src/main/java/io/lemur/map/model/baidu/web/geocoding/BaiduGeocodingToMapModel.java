package io.lemur.map.model.baidu.web.geocoding;

import io.lemur.map.model.baidu.web.base.BaiduBaseModel;

/**
 * Geocoding API包括地址解析
        地理编码：即地址解析，由详细到街道的结构化地址得到百度经纬度信息，且支持名胜古迹、标志性建筑名称直接解析返回百度经纬度。
        例如：“北京市海淀区中关村南大街27号”地址解析的结果是“lng:116.31985,lat:39.959836”，
        “百度大厦”地址解析的结果是“lng:116.30815,lat:40.056885”
        
        
                            参数                   是否必须         默认值                格式举例                                                                                        含   义
        output      否                    xml       json或xml                         输出格式为json或者xml
        ak          是                   无                    E4805d16520de693a3fe707cdc962045    用户申请注册的key，自v2开始参数修改为“ak”，之前版本参数为“key”
        sn          否                   无                   若用户所用ak的校验方式为sn校验时该参数必须。 （sn生成算法）
        callback    否                   无               callback=showLocation(JavaScript函数名)    将json格式的返回值通过callback函数返回以实现jsonp功能
         address    是               无                           北京市海淀区上地十街10号                                                       根据指定地址进行坐标的反定向解析
                                                                                                                                                                                                                        该参数是地理编码的必填项，可以输入三种样式的值，分别是： 
                                                                        •标准的地址信息，如北京市海淀区上地十街十号; 
                                                                        •名胜古迹、标志性建筑物，如天安门，百度大厦; 
                                                                        • 支持 “*路与*路交叉口”描述方式，如北一环路和阜阳路的交叉路口 
                                                                                                                                                                                                                            注意：后两种方式并不总是有返回结果，只有当地址库中存在该地址描述时才有返回。

             city        否         “北京市”    “广州市”                           地址所在的城市名
                                                                                                                                                                                                                    该参数是可选项，用于指定上述地址所在的城市，当多个城市都有上述地址时，该参数起到过滤作用。


 * @author JueYue
 * @date 2015年1月26日
 */
public class BaiduGeocodingToMapModel extends BaiduBaseModel {

    /**
     * 
     */
    private static final long         serialVersionUID = 1L;

    private BaiduGeocodingToMapDetailModel result;

    public BaiduGeocodingToMapDetailModel getResult() {
        return result;
    }

    public void setResult(BaiduGeocodingToMapDetailModel result) {
        this.result = result;
    }

}
