package com.site.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class EcartoonUtil {
    /**
     * 把各个参数转为json字符串
     * 
     * @param orderId
     *            订单号
     * @param orderAmount
     *            订单总额
     * @param cardNum
     *            卡片数量
     * @param cards
     *            卡片列表
     * @return json字符串
     */
    public static String covertJson(String orderId, String orderAmount, int cardNum, List<CardInfo> cards) {
        StringBuffer sp = new StringBuffer();
        JSONObject json = new JSONObject();
        json.put("orderId", orderId);
        JSONObject json1 = new JSONObject();
        json1.put("orderAmount", orderAmount);
        JSONObject json2 = new JSONObject();
        json2.put("cardNum", cardNum);
        JSONObject json3 = new JSONObject();
        json3.put("cardList", JacksonUtil.toJson(cards));
        sp.append("[").append(json.toString()).append(",").append(json1.toString()).append(",").append(json2.toString()).append(",").append(json3.toString()).append("]");
        String gdadBeanJson = sp.toString();
        return gdadBeanJson;
    }

    /**
     * E卡通参数的输入及封装返回
     * 
     * @param merchantNo：商户号，服务器提供
     * @param userId
     *            ：操作员代码
     * @param orderId
     *            订单号
     * @param orderAmount
     *            订单总额
     * @param cardNum
     *            卡片数量
     * @param cards
     *            卡片列表
     * @return E卡通的支付参数封装map
     */
    public static Map<String, String> ecartoonInt(String merchantNo, String userId, String orderId, String orderAmount, int cardNum, List<CardInfo> cards) {
        Map<String, String> parameterMap = new HashMap<String, String>();
        String gdadBeanJson = EcartoonUtil.covertJson(orderId, orderAmount, cardNum, cards);
        parameterMap.put("merchantNo", merchantNo);
        parameterMap.put("userId", userId);
        parameterMap.put("gdadBeanJson", gdadBeanJson);
        return parameterMap;
    }
}
