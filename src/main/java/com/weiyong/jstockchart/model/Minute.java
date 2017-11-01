package com.weiyong.jstockchart.model;

import java.math.BigDecimal;

public class Minute {

	/**
	 * 时间
	 */
	private String datetime;

	/**
	 * 现价
	 */
	private BigDecimal price;

	/**
	 * 成交额
	 */
	private Integer amount;

	/**
	 * 成交量
	 */
	private Integer volume;

	/**
	 * 均价
	 */
	private BigDecimal avePrice;

	/**
	 * 前收盘价
	 */
	private BigDecimal lastClose;

	/**
	 * 最高价
	 */
	private BigDecimal high;

	/**
	 * 最低价
	 */
	private BigDecimal low;
	
	/**
	 * 价格倍数
	 */
	private BigDecimal priceWeight;
	

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public BigDecimal getAvePrice() {
		return avePrice;
	}

	public void setAvePrice(BigDecimal avePrice) {
		this.avePrice = avePrice;
	}

	public BigDecimal getLastClose() {
		return lastClose;
	}

	public void setLastClose(BigDecimal lastClose) {
		this.lastClose = lastClose;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getPriceWeight() {
		return priceWeight;
	}

	public void setPriceWeight(BigDecimal priceWeight) {
		this.priceWeight = priceWeight;
	}

}
