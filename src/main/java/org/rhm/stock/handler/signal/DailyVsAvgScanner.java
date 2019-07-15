package org.rhm.stock.handler.signal;

import java.util.List;

import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.domain.StockStatistic;
import org.rhm.stock.handler.stat.DailyPriceVsAvg;
import org.rhm.stock.service.PriceService;
import org.rhm.stock.service.SignalService;
import org.rhm.stock.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("dailyVsAvgCrossover")
public class DailyVsAvgScanner implements SignalScanner {
	@Autowired
	private StatisticService statSvc = null;
	@Autowired
	private SignalService signalSvc = null;
	@Autowired
	private PriceService priceSvc = null;
	private static final String SIGNAL_DLYV50X_UP = "DYV50XUP";
	private static final String SIGNAL_DLYV50X_DN = "DYV50XDN";
	private static final String SIGNAL_DLYV200X_UP = "DYV200XUP";
	private static final String SIGNAL_DLYV200X_DN = "DYV200XDN";
	private static final String SIGNAL_20ABV200 = "AVG20ABV200";
	private static final String STAT_DYV50 = "DYPRCV50A";
	private static final String STAT_DYV200 = "DYPRCV200A";
	private Logger logger = LoggerFactory.getLogger(DailyVsAvgScanner.class);
	
	private void evaluateCrossUp(List<StockStatistic> statList, String signalType) {
		StockPrice price = null;
		StockSignal signal = null;
		if (statList.size() == 4) {
			if (statList.get(0).getStatisticValue().doubleValue() > 1 && statList.get(1).getStatisticValue().doubleValue() > 1) {
				if (statList.get(2).getStatisticValue().doubleValue() < 1 && statList.get(3).getStatisticValue().doubleValue() < 1) {
					price = priceSvc.findStockPrice(statList.get(1).getPriceId());
					signal = new StockSignal(price, signalType);
					signalSvc.createSignal(signal);
					logger.debug("evaluateCrossUp - created " + signalType + " signal for " + signal.getPriceId());
				}
			}
		}
	}
	
	private void evaluateCrossDown(List<StockStatistic> statList, String signalType) {
		StockPrice price = null;
		StockSignal signal = null;
		if (statList.size() == 4) {
			if (statList.get(0).getStatisticValue().doubleValue() < 1 && statList.get(1).getStatisticValue().doubleValue() < 1) {
				if (statList.get(2).getStatisticValue().doubleValue() > 1 && statList.get(3).getStatisticValue().doubleValue() > 1) {
					price = priceSvc.findStockPrice(statList.get(1).getPriceId());
					signal = new StockSignal(price, signalType);
					signalSvc.createSignal(signal);
					logger.debug("evaluateCrossDown - created " + signalType + " signal for " + signal.getPriceId());
				}
			}
		}
	}

	private void evaluate20Above200(StockStatistic stat) {
		StockPrice price = null;
		StockSignal signal = null;
		if (stat.getStatisticValue().doubleValue() > 1) {
			price = priceSvc.findStockPrice(stat.getPriceId());
			signal = new StockSignal(price, SIGNAL_20ABV200);
			signalSvc.createSignal(signal);
			logger.debug("evaluate20Above200 - created " + SIGNAL_20ABV200 + " signal for " + signal.getPriceId());
		}
	}
	
	@Override
	public void scan(String tickerSymbol) {
		List<StockStatistic> statList = statSvc.retrieveStatList(tickerSymbol, STAT_DYV50);
		logger.info("scan - found " + statList.size() + " " + STAT_DYV50 + " stats for " + tickerSymbol);
		while (statList.size() > 4) {
			this.evaluateCrossUp(statList.subList(0, 4), SIGNAL_DLYV50X_UP);
			this.evaluateCrossDown(statList.subList(0, 4), SIGNAL_DLYV50X_DN);
			statList.remove(0);
		}
		statList = statSvc.retrieveStatList(tickerSymbol, STAT_DYV200);
		logger.info("scan - found " + statList.size() +  " " + STAT_DYV200 + " stats for " + tickerSymbol);
		while (statList.size() > 4) {
			this.evaluateCrossUp(statList.subList(0, 4), SIGNAL_DLYV200X_UP);
			this.evaluateCrossDown(statList.subList(0, 4), SIGNAL_DLYV200X_DN);
			statList.remove(0);
		}
		statList = statSvc.retrieveStatList(tickerSymbol, DailyPriceVsAvg.AVG_PRC_20_VS_200);
		for (StockStatistic stat: statList) {
			this.evaluate20Above200(stat);
		}
	}

}
