package org.rhm.stock.domain;

import java.math.BigDecimal;

public class AveragePrice {
	private Integer daysCnt = null;
	private Integer avgVolume = null;
	private BigDecimal avgPrice = null;
	private BigDecimal avgHighLowRange = null;
	private BigDecimal avgOpenCloseRange = null;
	public Integer getDaysCnt() {
		return daysCnt;
	}
	public void setDaysCnt(Integer daysCnt) {
		this.daysCnt = daysCnt;
	}
	public Integer getAvgVolume() {
		return avgVolume;
	}
	public void setAvgVolume(Integer avgVolume) {
		this.avgVolume = avgVolume;
	}
	public BigDecimal getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}
	public BigDecimal getAvgHighLowRange() {
		return avgHighLowRange;
	}
	public void setAvgHighLowRange(BigDecimal avgHighLowRange) {
		this.avgHighLowRange = avgHighLowRange;
	}
	public BigDecimal getAvgOpenCloseRange() {
		return avgOpenCloseRange;
	}
	public void setAvgOpenCloseRange(BigDecimal avgOpenCloseRange) {
		this.avgOpenCloseRange = avgOpenCloseRange;
	}
}
