package org.rhm.stock.handler.stat;

import java.util.List;

import org.rhm.stock.domain.StockPrice;

public class DailyPriceVsAvg implements StatisticCalculator {

	private static final String DLY_PRC_VS_50_DAY_AVG = "DYPRCV50A";
	private static final String DLY_PRC_VS_200_DAY_AVG = "DYPRCV200A";
	
	@Override
	public void calculate(List<StockPrice> priceList) {
		// TODO Auto-generated method stub

	}

}
