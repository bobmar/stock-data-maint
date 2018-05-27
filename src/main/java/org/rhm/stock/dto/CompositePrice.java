package org.rhm.stock.dto;

import java.util.List;

import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.domain.StockStatistic;

public class CompositePrice {
	private String priceId = null;
	private String tickerSymbol = null;
	private StockPrice price = null;
	private List<StockStatistic> statList = null;
	private List<StockSignal> signalList = null;
	
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
	public StockPrice getPrice() {
		return price;
	}
	public void setPrice(StockPrice price) {
		this.price = price;
	}
	public List<StockStatistic> getStatList() {
		return statList;
	}
	public void setStatList(List<StockStatistic> statList) {
		this.statList = statList;
	}
	public List<StockSignal> getSignalList() {
		return signalList;
	}
	public void setSignalList(List<StockSignal> signalList) {
		this.signalList = signalList;
	}
}
