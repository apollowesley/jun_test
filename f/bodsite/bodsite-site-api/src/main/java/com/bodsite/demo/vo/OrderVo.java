package com.bodsite.demo.vo;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 订单vo 
* @author bod
* @date 2017年1月5日 上午10:50:38 
*
 */
public class OrderVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull(message="商品不能为空!")
	private Integer productId;//商品id
	@Min(value=1,message="数量不能小于1！")
	private Integer num;//数量
	@NotNull(message="支付类型不能为空!")
	private String payType;//支付类型  PayConstants.PAY_TYPE.xx.name()
	
	private String memo;//备注

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
}
