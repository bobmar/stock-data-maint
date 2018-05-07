package org.rhm.stock.handler.stat;

import java.math.BigDecimal;
import java.util.List;

import org.rhm.stock.domain.AveragePrice;
import org.rhm.stock.domain.StockAveragePrice;
import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockStatistic;
import org.rhm.stock.service.AveragePriceService;
import org.rhm.stock.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("dailyPriceVsAvg")
public class DailyPriceVsAvg implements StatisticCalculator {

	private static final String DLY_PRC_VS_50_DAY_AVG = "DYPRCV50A";
	private static final String DLY_PRC_VS_200_DAY_AVG = "DYPRCV200A";

	@Autowired
	private AveragePriceService avgSvc = null;
	@Autowired
	private StatisticService statSvc = null;
	
	private List<StockAveragePrice> avgPriceList = null;
	private Logger logger = LoggerFactory.getLogger(DailyPriceVsAvg.class);
	
	private void init(String tickerSymbol) {
		avgPriceList = avgSvc.findAvgPriceList(tickerSymbol);
		logger.debug("init - found " + avgPriceList.size() + " for " + tickerSymbol );
	}
	
	private StockAveragePrice findStockAvgPrice(String priceId) {
		StockAveragePrice sap = null;
		for (StockAveragePrice stockAvgPrice: this.avgPriceList) {
			if (stockAvgPrice.getPriceId().equals(priceId)) {
				sap = stockAvgPrice;
				logger.debug("findStockAvgPrice - found average price for " + priceId);
				break;
			}
		}
		return sap;
	}
	
	private AveragePrice findAvgPrice(String priceId, int days) {
		StockAveragePrice avg = findStockAvgPrice(priceId);
		AveragePrice avgPrice = null;
		if (avg != null) {
			for (AveragePrice ap: avg.getAvgList()) {
				if (ap.getDaysCnt().intValue() == days) {
					avgPrice = ap;
					break;
				}
			}
		}
		else {
			logger.debug("findAvgPrice - average price not found for " + priceId);
		}
		return avgPrice;
	}
	
	private void calcPriceVsAvg(StockPrice price, int days, String statType) {
		AveragePrice avgPrice = this.findAvgPrice(price.getPriceId(), days);
		if (avgPrice != null) {
			logger.debug("calcPriceVsAvg - average price " + avgPrice.getDaysCnt() + ":" + avgPrice.getAvgPrice());
			double priceVsAvg = (price.getClosePrice().doubleValue() / avgPrice.getAvgPrice().doubleValue());
			logger.debug("calcPriceVsAvg - " + price.getPriceId() + " price vs. " + days + " day average=" + priceVsAvg);
			statSvc.createStatistic(new StockStatistic(price.getPriceId(), statType, BigDecimal.valueOf(priceVsAvg), price.getTickerSymbol(), price.getPriceDate()));
		}
		else {
			logger.debug("calcPriceVsAvg - unable to find " + days + " average price for " + price.getPriceId());
		}
	}
	
	@Override
	public void calculate(List<StockPrice> priceList) {
		StockPrice firstPrice = priceList.get(0);
		this.init(firstPrice.getTickerSymbol());
		for (StockPrice price: priceList) {
			this.calcPriceVsAvg(price, 50, DLY_PRC_VS_50_DAY_AVG);
			this.calcPriceVsAvg(price, 200, DLY_PRC_VS_200_DAY_AVG);
		}
	}

}
