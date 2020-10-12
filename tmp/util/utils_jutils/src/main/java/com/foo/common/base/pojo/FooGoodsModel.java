package com.foo.common.base.pojo;


public class FooGoodsModel {
	// 销售排名
	private String sellsRank;
	// 商品条码
	private String goodsLabel;
	// 商品名称
	private String goodsName;
	// 商品单位
	private String goodsUnit;
	// 商品进价
	private String goodsBid;
	// 商品售价
	private String goodsPrice;
	// 商品销量
	private String goodsSales;

	public String getSellsRank() {
		return sellsRank;
	}

	public void setSellsRank(String sellsRank) {
		this.sellsRank = sellsRank;
	}

	public String getGoodsLabel() {
		return goodsLabel;
	}

	public void setGoodsLabel(String goodsLabel) {
		this.goodsLabel = goodsLabel;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public String getGoodsBid() {
		return goodsBid;
	}

	public void setGoodsBid(String goodsBid) {
		this.goodsBid = goodsBid;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsSales() {
		return goodsSales;
	}

	public void setGoodsSales(String goodsSales) {
		this.goodsSales = goodsSales;
	}

	@Override
	public String toString() {
		return getSellsRank() + " " + getGoodsLabel() + " " + getGoodsName()
				+ " " + getGoodsUnit() + " " + getGoodsBid() + " " + " "
				+ getGoodsPrice() + " " + getGoodsSales();
	}
}
