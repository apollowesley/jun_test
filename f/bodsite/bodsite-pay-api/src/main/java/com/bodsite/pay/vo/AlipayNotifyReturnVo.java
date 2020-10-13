package com.bodsite.pay.vo;

/**
 * 支付宝异步回调结果
* @author bod
* @date 2017年1月4日 下午3:46:34 
*
 */
public class AlipayNotifyReturnVo extends AlipayReturnVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*private String is_success;//成功标识(表示接口调用是否成功，并不表明业务处理结果。)
	private String sign_type;//签名方式
	private String sign;//签名
	private String out_trade_no;//商户网站唯一订单号
	private String subject;//商品名称
	private String payment_type;//支付类型
	private String exterface;//接口名称
	private String trade_no;//支付宝交易号
	private String trade_status;//交易状态
	private String notify_id;//通知校验ID
	private String notify_time;//通知时间
	private String notify_type;//通知类型
	private String seller_email;//卖家支付宝账号
	private String buyer_email;//买家支付宝账号
	private String seller_id;//卖家支付宝账户号
	private String buyer_id;//买家支付宝账户号
	private String total_fee;//交易金额
	private String body;//商品描述
	private String extra_common_param;//公用回传参数
	*/
	private String gmt_create;//交易创建时间
	private String gmt_payment;//交易付款时间
	private String gmt_close;//交易关闭时间
	private String refund_status;//退款状态
	private String gmt_refund;//退款时间

	private String price;//商品单价
	private String quantity;//购买数量
	private String discount;//折扣
	private String is_total_fee_adjust;//是否调整总价
	private String use_coupon;//是否使用红包买家
	private String business_scene;//是否扫码支付
	public String getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}
	public String getGmt_payment() {
		return gmt_payment;
	}
	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	public String getGmt_close() {
		return gmt_close;
	}
	public void setGmt_close(String gmt_close) {
		this.gmt_close = gmt_close;
	}
	public String getRefund_status() {
		return refund_status;
	}
	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}
	public String getGmt_refund() {
		return gmt_refund;
	}
	public void setGmt_refund(String gmt_refund) {
		this.gmt_refund = gmt_refund;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getIs_total_fee_adjust() {
		return is_total_fee_adjust;
	}
	public void setIs_total_fee_adjust(String is_total_fee_adjust) {
		this.is_total_fee_adjust = is_total_fee_adjust;
	}
	public String getUse_coupon() {
		return use_coupon;
	}
	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}
	public String getBusiness_scene() {
		return business_scene;
	}
	public void setBusiness_scene(String business_scene) {
		this.business_scene = business_scene;
	}

}
