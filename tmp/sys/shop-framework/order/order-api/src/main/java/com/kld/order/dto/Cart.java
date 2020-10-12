package com.kld.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Dan on 2016/3/16.
 */
public class Cart  implements Serializable {

    private BigDecimal productId;

    private BigDecimal num;

    private String userId;

    private BigDecimal isDel;

}
