package org.rhm.stock.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

public class StockStatistic {
	@Id
	private String priceId = null;
	private String statisticType = null;
	private BigDecimal statisticValue = null;
	private String tickerSymbol = null;
	private Date priceDate = null;
	
	public StockStatistic() {
		
	}
	
	public StockStatistic(String priceId, String statisticType, BigDecimal statisticValue) {
		this.priceId = priceId;
		this.statisticType = statisticType;
		this.statisticValue = statisticValue;
	}

	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}
	public String getStatisticType() {
		return statisticType;
	}
	public void setStatisticType(String statisticType) {
		this.statisticType = statisticType;
	}
	public BigDecimal getStatisticValue() {
		return statisticValue;
	}
	public void setStatisticValue(BigDecimal statisticValue) {
		this.statisticValue = statisticValue;
	}
	public String getTickerSymbol() {
		return tickerSymbol;
	}
	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}
	public Date getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}
}
