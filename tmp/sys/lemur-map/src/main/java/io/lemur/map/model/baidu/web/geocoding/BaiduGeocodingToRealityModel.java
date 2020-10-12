package io.lemur.map.model.baidu.web.geocoding;


import io.lemur.map.model.baidu.web.base.BaiduBaseModel;

/**
 * Geocoding API逆地址解析
       逆地理编码，即逆地址解析，由百度经纬度信息得到结构化地址信息。
       例如：“lat:31.325152,lng:120.558957”逆地址解析的结果是“江苏省苏州市虎丘区塔园路318号”。
        
        
                            参数                   是否必须         默认值                格式举例                                                                                        含   义
        output      否                    xml       json或xml                         输出格式为json或者xml
        ak          是                   无                    E4805d16520de693a3fe707cdc962045    用户申请注册的key，自v2开始参数修改为“ak”，之前版本参数为“key”
        sn          否                   无                   若用户所用ak的校验方式为sn校验时该参数必须。 （sn生成算法）
        callback    否                   无               callback=showLocation(JavaScript函数名)    将json格式的返回值通过callback函数返回以实现jsonp功能
     coordtype      否                    bd09ll bd09ll 百度经纬度坐标                                                           坐标的类型，目前支持的坐标类型包括：bd09ll（百度经纬度坐标）、gcj02ll（国测局经纬度坐标）、wgs84ll（ GPS经纬度）
        location    是                无                      38.76623,116.43213 lat<纬度>,lng<经度>根据经纬度坐标获取地址
        pois        否               0           0                               是否显示指定位置周边的poi，0为不显示，1为显示。当值为1时，显示周边100米内的poi。
 * @author JueYue
 * @date 2015年1月26日
 */
public class BaiduGeocodingToRealityModel extends BaiduBaseModel {

    /**
     * 
     */
    private static final long             serialVersionUID = 1L;

    private BaiduGeocodingToRealityDetailModel result;

    public BaiduGeocodingToRealityDetailModel getResult() {
        return result;
    }

    public void setResult(BaiduGeocodingToRealityDetailModel result) {
        this.result = result;
    }

}
