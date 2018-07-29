package org.rhm.stock.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class StockSignal {
	@Id
	private String signalId = null;
	private String signalType = null;
	private StockPrice price = null;
	
	public StockSignal() {
		
	}
	public StockSignal(StockPrice price, String signalType) {
		this.price = price;
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
	public StockPrice getPrice() {
		return price;
	}
	public void setPrice(StockPrice price) {
		this.price = price;
	}
}
