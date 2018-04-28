package org.rhm.stock.domain;

import java.util.List;

import org.springframework.data.annotation.Id;

public class StockAveragePrice {
	@Id
	private String priceId = null;
	private List<AveragePrice> avgList = null;
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}
	public List<AveragePrice> getAvgList() {
		return avgList;
	}
	public void setAvgList(List<AveragePrice> avgList) {
		this.avgList = avgList;
	}
	
	public AveragePrice getAvgPrice10Day() {
		return this.findAvgPrice(10);
	}
	
	public AveragePrice getAvgPrice50Day() {
		return this.findAvgPrice(50);
	}
	
	public AveragePrice getAvgPrice200Day() {
		return this.findAvgPrice(200);
	}
	
	private AveragePrice findAvgPrice(Integer days) {
		AveragePrice avgPrice = null;
		if (avgList != null) {
			for (AveragePrice avg: avgList) {
				if (avg.getDaysCnt().intValue() == days.intValue()) {
					avgPrice = avg;
				}
			}
		}
		return avgPrice;
	}
}
