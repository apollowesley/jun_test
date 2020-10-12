package com.iotechn.iot.executor.sys.demo

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.iotechn.iot.executor.dev.IDeviceLogger
import com.iotechn.iot.executor.model.InvokeContext
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created with IntelliJ IDEA.
 * Description: 本地测试通过后，将 package 以下的内容复制到executor content中就可以了
 * User: rize
 * Date: 2018-09-27
 * Time: 下午1:36
 */
class Main {

    /**
     * 返回从 格林时间 1970-01-01 00:00:00 到 现在的秒数
     * @param args
     * @return
     */
    public Long getGmt(InvokeContext invokeContext){
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 通过IP获取地区代码
     * @param invokeContext
     * @return
     */
    public Integer getLocation(InvokeContext invokeContext) {
        if (invokeContext.getInvokerInfoModel() != null && !"".equals(invokeContext.getInvokerInfoModel().getInvokerIp())) {
            OkHttpClient okHttpClient = invokeContext.getOkHttpClient();
            //调用高的地图 ip 定位Api https://lbs.amap.com/api/webservice/guide/api/ipconfig/
            String json = okHttpClient.newCall(new Request.Builder()
                    .url("https://restapi.amap.com/v3/ip?key=ea220c9f2dddd3cdebc82175a0db4acc&ip="+invokeContext.getInvokerInfoModel().getInvokerIp())
                     .build()).execute().body().string();
            return JSONObject.parseObject(json).getInteger("adcode");
        }
        return -1;
    }

    /**
     * 发送报告给目标邮箱 args[0] = 接收邮箱， args[1] = 标题 args[2] = 内容
     * @param invokeContext
     * @return
     */
    public Boolean sendReport(InvokeContext invokeContext) {
        return invokeContext.getMailSender().sendTo(invokeContext.getArgs()[0],invokeContext.getArgs()[1],invokeContext.getArgs()[2],1000l*60*60);
    }

    /**
     * 获取IP定位的天气
     * @param invokeContext
     * @return
     */
    public Integer getWeather(InvokeContext invokeContext) {
        int adcode = getLocation(invokeContext);
        if (adcode == -1) {
            return -1;
        }
        //去高德地图开放平台查询天气
        try {
            OkHttpClient okHttpClient = invokeContext.getOkHttpClient();
            String json = okHttpClient.newCall(new Request.Builder()
                    .url("https://restapi.amap.com/v3/weather/weatherInfo?key=ea220c9f2dddd3cdebc82175a0db4acc&city=" + adcode).build()).execute().body().string();
            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONArray lives = jsonObject.getJSONArray("lives");
            if (lives != null && lives.size() > 0) {
                JSONObject item = lives.getJSONObject(0);
                switch (item.getString("weather")) {
                    case "小雨":
                        return 1;
                    case "晴":
                        return 2;
                    case "阴":
                        return 3;
                    case "多云":
                        return 4;
                    case "雾":
                        return 5;
                }
            }
        } catch (Exception e) {

        }
        return -1;
    }

    /**
     * 记录开门时间
     * @param invokeContext
     * @return
     */
    public Long logTime(InvokeContext invokeContext) {
        IDeviceLogger logger = invokeContext.getDeviceLogger();
        logger.log(invokeContext.getInvokerInfoModel().getSecretKey(),"open_door","");
        return System.currentTimeMillis();
    }

}
