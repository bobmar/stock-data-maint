package org.rhm.stock.handler.stat;

import java.util.List;

import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockStatistic;
import org.rhm.stock.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("highLowPriceCalc")
public class HighLowPrice implements StatisticCalculator {

	private static final String HIGH_PRICE_4WK = "HIPR4WK";
	private static final String HIGH_PRICE_2WK = "HIPR2WK";
	private static final String LOW_PRICE_4WK = "LOPR4WK";
	private static final String LOW_PRICE_2WK = "LOPR2WK";
	private static final int GREATER_THAN = 1;
	private static final int LESS_THAN = -1;
	private Logger logger = LoggerFactory.getLogger(HighLowPrice.class);
	@Autowired
	private StatisticService statSvc = null;

	private void calcHigh(List<StockPrice> priceSubList, String statType) {
		StockPrice highPrice = null;
		for (StockPrice price: priceSubList) {
			if (highPrice == null) {
				highPrice = price;
			}
			else {
				logger.debug("calcHigh - " + statType + " high price=" + highPrice.getHighPrice() + "|current price=" + price.getHighPrice());
				if (price.getHighPrice().compareTo(highPrice.getHighPrice()) == GREATER_THAN) {
					highPrice = price;
					logger.debug("calcHigh - replaced the high price");
				}
			}
		}
		statSvc.createStatistic(new StockStatistic(highPrice.getPriceId(), statType, highPrice.getHighPrice(), highPrice.getTickerSymbol(), highPrice.getPriceDate()));
	}
	
	private void calcLow(List<StockPrice> priceSubList, String statType) {
		StockPrice lowPrice = null;
		for (StockPrice price: priceSubList) {
			if (lowPrice == null) {
				lowPrice = price;
			}
			else {
				logger.debug("calcLow - " + statType + " low price=" + lowPrice.getLowPrice() + "|current price=" + price.getLowPrice());
				if (price.getLowPrice().compareTo(lowPrice.getLowPrice()) == LESS_THAN) {
					lowPrice = price;
					logger.debug("calcLow - replaced the low price");
				}
			}
		}
		statSvc.createStatistic(new StockStatistic(lowPrice.getPriceId(), statType, lowPrice.getLowPrice(), lowPrice.getTickerSymbol(), lowPrice.getPriceDate()));
	}
	
	@Override
	public void calculate(List<StockPrice> priceList) {
		while (priceList.size() > 20) {
			this.calcHigh(priceList.subList(0, 19), HIGH_PRICE_4WK);
			this.calcHigh(priceList.subList(0,9), HIGH_PRICE_2WK);
			this.calcLow(priceList.subList(0, 19), LOW_PRICE_4WK);
			this.calcLow(priceList.subList(0,9), LOW_PRICE_2WK);
			priceList.remove(0);
		}
	}

}
