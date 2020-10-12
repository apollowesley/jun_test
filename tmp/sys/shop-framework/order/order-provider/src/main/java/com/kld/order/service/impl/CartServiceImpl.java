package com.kld.order.service.impl;

import com.kld.common.framework.dto.ResultMsg;
import com.kld.order.api.ICartService;
import com.kld.order.dao.CartDao;
import com.kld.order.dto.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by Dan on 2016/3/16.
 */
public class CartServiceImpl implements ICartService {

    @Autowired
    CartDao cartDao;

    @Override
    public ResultMsg addToCart(Cart cart) {
        return null;
    }

    @Override
    public ResultMsg updateProductNum(BigDecimal productId, Integer num, String userId) {
        return null;
    }

    @Override
    public ResultMsg deleteProduct(BigDecimal productId, String userId) {
        return null;
    }
}
