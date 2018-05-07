package org.rhm.stock.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class StockSignal {
	@Id
	private String signalId = null;
	private String priceId = null;
	private String signalType = null;
	private Date priceDate = null;
	private String tickerSymbol = null;
	public StockSignal() {
		
	}
	public StockSignal(String priceId, String signalType, String tickerSymbol) {
		this.priceId = priceId;
		this.signalType = signalType;
		this.signalId = priceId + ":" + signalType;
		this.tickerSymbol = tickerSymbol;
	}
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}
	public String getSignalType() {
		return signalType;
	}
	public void setSignalType(String signalType) {
		this.signalType = signalType;
	}
	public Date getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}
	public String getTickerSymbol() {
		return tickerSymbol;
	}
	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}
	public String getSignalId() {
		return signalId;
	}
	public void setSignalId(String signalId) {
		this.signalId = signalId;
	}
}
