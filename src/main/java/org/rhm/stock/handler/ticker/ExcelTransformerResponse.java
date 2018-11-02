package org.rhm.stock.handler.ticker;

import java.util.ArrayList;
import java.util.List;

import org.rhm.stock.domain.IbdStatistic;

public class ExcelTransformerResponse {
	List<String> tickerSymbols = new ArrayList<String>();
	List<IbdStatistic> ibdStatList = new ArrayList<IbdStatistic>();
	public List<String> getTickerSymbols() {
		return tickerSymbols;
	}
	public void setTickerSymbols(List<String> tickerSymbols) {
		this.tickerSymbols = tickerSymbols;
	}
	public List<IbdStatistic> getIbdStatList() {
		return ibdStatList;
	}
	public void setIbdStatList(List<IbdStatistic> ibdStatList) {
		this.ibdStatList = ibdStatList;
	}

}
