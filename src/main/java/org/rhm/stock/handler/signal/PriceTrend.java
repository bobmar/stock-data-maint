package org.rhm.stock.handler.signal;

import java.util.ArrayList;
import java.util.List;

import org.rhm.stock.domain.AveragePrice;
import org.rhm.stock.domain.StockAveragePrice;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.domain.StockStatistic;
import org.rhm.stock.service.AveragePriceService;
import org.rhm.stock.service.SignalService;
import org.rhm.stock.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("priceTrend")
public class PriceTrend implements SignalScanner {
	@Autowired
	private StatisticService statSvc = null;
	@Autowired
	private SignalService signalSvc = null;
	@Autowired
	private AveragePriceService avgPriceSvc = null;
	
	private static final String WEEKLY_CLOSE_STAT = "WKCLSPCT";
	private static final String UP_4_WEEKS_SIGNAL = "4WKUP";
	private static final String UP_5_WEEKS_SIGNAL = "5WKUP";
	private static final String PRICE_UPTREND_SIGNAL = "UPTREND";
	private static final String PRICE_DNTREND_SIGNAL = "DNTREND";
	private Logger logger = LoggerFactory.getLogger(PriceTrend.class);
	
	private void detectConsecutiveUpWeeks(List<StockStatistic> weeklyPriceChgList) {
		int upCnt = 0;
		StockStatistic firstStat = weeklyPriceChgList.get(0);
		StockStatistic stat = null;
		for (int i = 0; i < 25; i+=5) {
			stat = weeklyPriceChgList.get(i);
			if (stat.getStatisticValue().doubleValue() > 0) {
				upCnt++;
			}
			else {
				break;
			}
		}
		if (upCnt == 4) {
			signalSvc.createSignal(new StockSignal(firstStat.getPriceId(), UP_4_WEEKS_SIGNAL, firstStat.getTickerSymbol(), firstStat.getPriceDate()));
			logger.debug("detectConsecutiveUpWeeks - 4 consecutive weeks up");
		}
		else {
			if (upCnt == 5) {
				signalSvc.createSignal(new StockSignal(firstStat.getPriceId(), UP_5_WEEKS_SIGNAL, firstStat.getTickerSymbol(), firstStat.getPriceDate()));
				logger.debug("detectConsecutiveUpWeeks - 5 consecutive weeks up");
			}
		}
	}
	
	private void detectTrend(StockAveragePrice avgPrice, String trendDirection) {
 		boolean trend = false;
 		int avgPriceCnt = 0;
 		double ap10Day = 0, ap50Day = 0, ap200Day = 0; 
		for (AveragePrice ap: avgPrice.getAvgList()) {
			switch (ap.getDaysCnt()) {
			case 10:
				ap10Day = ap.getAvgPrice().doubleValue();
				avgPriceCnt++;
				break;
			case 50:
				ap50Day = ap.getAvgPrice().doubleValue();
				avgPriceCnt++;
				break;
			case 200:
				ap200Day = ap.getAvgPrice().doubleValue();
				avgPriceCnt++;
				break;
			}
		}
		if (avgPriceCnt == 3) {
			logger.debug("detectTrend - 10 day=" + ap10Day + "; 50 day=" + ap50Day + "; 200 day=" + ap200Day);
			if (trendDirection.equals(PRICE_UPTREND_SIGNAL)) {
 				if (ap10Day > ap50Day && ap50Day > ap200Day) {
 					trend = true;
 				}
 				else {
 					trend = false;
 				}
			}
			else {
 				if (ap10Day < ap50Day && ap50Day < ap200Day) {
 					trend = true;
 				}
 				else {
 					trend = false;
 				}
			}
		}
 		if (trend) {
 			signalSvc.createSignal(new StockSignal(avgPrice.getPriceId(), trendDirection, avgPrice.getTickerSymbol(), avgPrice.getPriceDate()));
 			logger.debug("detectTrend - created signal " + trendDirection + " for " + avgPrice.getPriceId());
 		}
	}
	
	@Override
	public void scan(String tickerSymbol) {
		List<StockStatistic> wkPrcChgList = statSvc.retrieveStatList(tickerSymbol, WEEKLY_CLOSE_STAT);
		while (wkPrcChgList.size() > 25) {
			this.detectConsecutiveUpWeeks(wkPrcChgList.subList(0, 25));
			wkPrcChgList.remove(0);
		}
		List<StockAveragePrice> avgPriceList = avgPriceSvc.findAvgPriceList(tickerSymbol);
		for (StockAveragePrice avgPrice: avgPriceList) {
			this.detectTrend(avgPrice, PRICE_UPTREND_SIGNAL);
			this.detectTrend(avgPrice, PRICE_DNTREND_SIGNAL);
		}
	}

	
}