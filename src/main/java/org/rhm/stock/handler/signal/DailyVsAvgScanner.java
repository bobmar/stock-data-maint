package org.rhm.stock.handler.signal;

import java.util.List;

import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.domain.StockStatistic;
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
	private static final String SIGNAL_DLYV50X = "DYV50X";
	private static final String STAT_DYV50 = "DYPRCV50A";
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
					logger.debug("processStats - created " + signalType + " signal for " + signal.getPriceId());
				}
			}
		}
	}
	
	@Override
	public void scan(String tickerSymbol) {
		List<StockStatistic> statList = statSvc.retrieveStatList(tickerSymbol, STAT_DYV50);
		logger.info("scan - found " + statList.size() + " stats for " + tickerSymbol);
		while (statList.size() > 4) {
			this.evaluateCrossUp(statList.subList(0, 4), SIGNAL_DLYV50X);
			statList.remove(0);
		}
	}

}
