package com.kld.order.api;

import com.kld.common.framework.dto.ResultMsg;
import com.kld.order.dto.Cart;

import java.math.BigDecimal;

/**
 * Created by Dan on 2016/3/16.
 */
public interface ICartService {

    /**
     * 添加商品到购物车
     * @param cart
     *
     * @return
     */
    public ResultMsg addToCart(Cart cart);

    /**
     * 修改购物商品数量
     * @param productId 商品ID
     * @param num 商品数量
     * @param userId 用户ID
     * @return
     */
    public ResultMsg updateProductNum(BigDecimal productId, Integer num, String userId);

    /**
     * 删除商品
     * @param productId 商品ID
     * @param userId 用户ID
     * @return
     */
    public ResultMsg deleteProduct(BigDecimal productId, String userId);

}
