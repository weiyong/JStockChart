package com.weiyong.jstockchart.model;

import java.math.BigDecimal;

public class Quote {


	private String code;

	private String name;

	private BigDecimal price;

	private BigDecimal open;
	
	private BigDecimal close;

	private BigDecimal lastclose;

	private BigDecimal preClose;

	private BigDecimal low;

	private BigDecimal high;

	private BigDecimal upDown;

	private BigDecimal priceweight;
	
	private int type;

	private String datetime;

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getLastclose() {
		return lastclose;
	}

	public void setLastclose(BigDecimal lastclose) {
		this.lastclose = lastclose;
	}

	public BigDecimal getPreClose() {
		return preClose;
	}

	public void setPreClose(BigDecimal preClose) {
		this.preClose = preClose;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getUpDown() {
		return upDown;
	}

	public void setUpDown(BigDecimal upDown) {
		this.upDown = upDown;
	}
	
	public int getPrecision() {
		return this.type == 3 ? 100 : 0x2710;
	}

	public double getUpDownRate() {
		return this.open.compareTo(BigDecimal.ZERO) == 0 ? 0.0
				: ((this.price.subtract(this.open)).divide(this.open, 6, BigDecimal.ROUND_HALF_EVEN).doubleValue()) * 100;
	}

	public BigDecimal getPriceweight() {
		return priceweight;
	}

	public void setPriceweight(BigDecimal priceweight) {
		this.priceweight = priceweight;
	}

	public double getHighLowRate() {
		return this.low.compareTo(BigDecimal.ZERO) == 0 ? 0.0
				: ((this.high.subtract(this.low)).divide(this.low, 6, BigDecimal.ROUND_HALF_EVEN).doubleValue()) * 100;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	
	@Override
	public String toString() {
		return "Quote [code=" + code + ", name=" + name + ", price=" + price + ", open=" + open + ", close=" + close
				+ ", lastclose=" + lastclose + ", preClose=" + preClose + ", low=" + low + ", high=" + high
				+ ", upDown=" + upDown + ", priceweight=" + priceweight + ", type=" + type + ", datetime=" + datetime
				+ "]";
	}

	
}
