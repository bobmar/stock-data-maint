package org.rhm.stock.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

public class StockSignal {
	@Id
	private String signalId = null;
	private String signalType = null;
	private String priceId = null;
	private String tickerSymbol = null;
	private Date priceDate = null;
	private BigDecimal closePrice = null;
	private BigDecimal openPrice = null;
	private BigDecimal lowPrice = null;
	private BigDecimal highPrice = null;
	private Long volume = null;
	
	public StockSignal() {
		
	}
	public StockSignal(StockPrice price, String signalType) {
		this.priceId = price.getPriceId();
		this.tickerSymbol = price.getTickerSymbol();
		this.priceDate = price.getPriceDate();
		this.closePrice = price.getClosePrice();
		this.openPrice = price.getOpenPrice();
		this.lowPrice = price.getLowPrice();
		this.highPrice = price.getHighPrice();
		this.volume = price.getVolume();
		this.signalType = signalType;
		this.signalId = price.getPriceId() + ":" + signalType;
	}
	public String getSignalType() {
		return signalType;
	}
	public void setSignalType(String signalType) {
		this.signalType = signalType;
	}
	public String getSignalId() {
		return signalId;
	}
	public void setSignalId(String signalId) {
		this.signalId = signalId;
	}
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
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
	public BigDecimal getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(BigDecimal closePrice) {
		this.closePrice = closePrice;
	}
	public BigDecimal getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(BigDecimal openPrice) {
		this.openPrice = openPrice;
	}
	public BigDecimal getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}
	public BigDecimal getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}
	public Long getVolume() {
		return volume;
	}
	public void setVolume(Long volume) {
		this.volume = volume;
	}
}
