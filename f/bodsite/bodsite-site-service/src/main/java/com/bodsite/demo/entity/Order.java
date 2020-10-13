package com.bodsite.demo.entity;

/**
 * 订单
* @author bod
* @date 2017年1月5日 上午10:50:03 
*
 */
public class Order {
	
	private Integer productId;//商品id
	
	private Integer num;//数量
	
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

}
