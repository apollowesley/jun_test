package com.kld.order.api;

import com.kld.common.framework.dto.ResultMsg;
import com.kld.order.dto.Order;

import java.math.BigDecimal;

/**
 * Created by Dan on 2016/3/16.
 */
public interface IOrderService {

    /**
     * 创建订单、锁库存
     * @param order 订单实体对象
     * @return
     */
    public ResultMsg createOrder(Order order);

    /**
     * 确认订单、支付订单
     * @param orderId 订单ID
     * @param payType 支付方式
     * @return
     */
    public ResultMsg confirmOrder(BigDecimal orderId, Integer payType);

    /**
     * （积分商城）确认订单，调用卡核心支付接口
     * @param orderId 订单ID
     * @param payType 支付方式
     * @param cardNo 卡号
     * @return
     */
    public ResultMsg confirmOrderByCardCore(BigDecimal orderId, Integer payType,String cardNo);

    /**
     * 补单
     * @param order 补单实体
     * @return
     */
    public ResultMsg reOrder(Order order);

    /**
     * 获取订单详情，包括商品信息等。
     * @param orderId 订单ID
     * @return
     */
    public Order getOrderDetail(BigDecimal orderId);

    /**
     * 根据用户ID获取订单列表
     * @param userId
     * @return
     */
    public Order getOrdersByUserId(String userId);
}
