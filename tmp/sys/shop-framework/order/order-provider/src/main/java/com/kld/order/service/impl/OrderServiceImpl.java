package com.kld.order.service.impl;

import com.kld.common.framework.dto.ResultMsg;
import com.kld.order.api.IOrderService;
import com.kld.order.dao.OrderDao;
import com.kld.order.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Created by Dan on 2016/3/16.
 */
public class OrderServiceImpl implements IOrderService{

    @Autowired
    OrderDao orderDao;

    @Override
    public ResultMsg createOrder(Order order) {
        return null;
    }

    @Override
    public ResultMsg confirmOrder(BigDecimal orderId, Integer payType) {
        return null;
    }

    @Override
    public ResultMsg confirmOrderByCardCore(BigDecimal orderId, Integer payType, String cardNo) {
        return null;
    }

    @Override
    public ResultMsg reOrder(Order order) {
        return null;
    }

    @Override
    public Order getOrderDetail(BigDecimal orderId) {
        return null;
    }

    @Override
    public Order getOrdersByUserId(String userId) {
        return null;
    }
}
