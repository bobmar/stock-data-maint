package org.rhm.stock.handler.stat;

import java.math.BigDecimal;
import java.util.ArrayList;
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
@Qualifier("momentum")
public class Momentum implements StatisticCalculator {
	@Autowired
	private StatisticService statSvc = null;
	@Autowired
	private AveragePriceService avgPriceSvc = null;

	private static final String STAT_Z_SCORE = "ZSCORE";
	private static final String STAT_TR_MOM = "TRMOM";
	private Logger logger = LoggerFactory.getLogger(Momentum.class);
	
	private void calcTotalReturnMomentum(List<StockPrice> priceList) {
		StockPrice todayPrice = null, oldPrice = null;
		todayPrice = priceList.get(0);
		double trMomentum = 0.0;
		if (priceList.size() >= 50) {
			oldPrice = priceList.get(49);
			if (todayPrice != null && oldPrice != null) {
				trMomentum = ((todayPrice.getClosePrice().doubleValue() / oldPrice.getClosePrice().doubleValue()) - 1);
				statSvc.createStatistic(
					new StockStatistic(todayPrice.getPriceId(), STAT_TR_MOM, BigDecimal.valueOf(trMomentum), todayPrice.getTickerSymbol(), todayPrice.getPriceDate()));
			}
		}
	}
	
	private void calcZScore(List<StockPrice> priceList) {
		StockAveragePrice avgPrice = null;
		AveragePrice avg50Day = null;
		StockStatistic stdDev = null;
		double zScore = 0.0;
		for (StockPrice price: priceList) {
			avgPrice = avgPriceSvc.findAvgPrice(price.getPriceId());
			if (avgPrice != null) {
				for (AveragePrice avg: avgPrice.getAvgList()) {
					if (avg.getDaysCnt() == 50) {
						avg50Day = avg;
						break;
					}
				}
				stdDev = statSvc.retrieveStat(price.getTickerSymbol(), StdDeviation.STD_DEV_10WK, price.getPriceDate());
				if (avg50Day != null && (stdDev != null && stdDev.getStatisticValue().doubleValue() > 0)) {
					logger.info("calcZScore - priceID=" + price.getPriceId() + "50-Day avg price=" + avg50Day.getAvgPrice() + "; closing price=" + price.getClosePrice() + "; std dev=" + stdDev.getStatisticValue());
					zScore = ((price.getClosePrice().doubleValue() - avg50Day.getAvgPrice().doubleValue())
						/stdDev.getStatisticValue().doubleValue());
					statSvc.createStatistic(
						new StockStatistic(price.getPriceId(), STAT_Z_SCORE, BigDecimal.valueOf(zScore), price.getTickerSymbol(), price.getPriceDate()));
				}
			}
			else {
				logger.warn("calcZScore - average price record for " + price.getPriceId() + " is not found");
			}
		}
	}
	
	@Override
	public void calculate(List<StockPrice> priceList) {
		List<StockPrice> workList = new ArrayList<StockPrice>();
		this.calcZScore(priceList);
		workList.addAll(priceList);
		while (workList.size() > 50) {
			this.calcTotalReturnMomentum(workList);
			workList.remove(0);
		}

	}

}
