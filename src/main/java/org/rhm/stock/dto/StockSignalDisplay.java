package org.rhm.stock.dto;

import org.rhm.stock.domain.StockSignal;

public class StockSignalDisplay extends StockSignal {

	private Boolean multiList = false;

	public StockSignalDisplay(StockSignal signal) {
		super(signal);
	}
	
	public Boolean getMultiList() {
		return multiList;
	}

	public void setMultiList(Boolean multiList) {
		this.multiList = multiList;
	}
	
	
}
