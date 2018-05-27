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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Qualifier("priceBreakout")
public class PriceBreakout implements SignalScanner {
	@Autowired
	private PriceService priceSvc = null;
	@Autowired
	private StatisticService statSvc = null;
	@Autowired
	private SignalService signalSvc = null;
	private static final String FOUR_WK_HIGH = "HIPR4WK";
	private static final String FOUR_WK_BRK_SIG = "4WKBRK";
	private Logger logger = LoggerFactory.getLogger(PriceBreakout.class);
	
	private StockStatistic findStat(String statId, List<StockStatistic> statList) {
		StockStatistic stockStat = null;
		for (int i = 0; i < statList.size(); i++) {
			if (statList.get(i).getStatId().equals(statId)) {
				if (i < (statList.size() - 1)) {
					stockStat = statList.get(i + 1);
					logger.debug("findStat - compare to stat " + stockStat.getPriceId() + "/" + stockStat.getStatisticValue());
				}
				break;
			}
		}
		return stockStat;
	}
	
	private void evaluatePrice(StockPrice price, List<StockStatistic> statList) {
		String statId = price.getPriceId() + ":" + FOUR_WK_HIGH;
		StockStatistic stat = this.findStat(statId, statList);
		if (stat != null) {
			logger.debug("evaluatePrice - OHL prices=" + price.getOpenPrice().doubleValue() 
					+ "|" + price.getHighPrice().doubleValue() 
					+ "|" + price.getLowPrice().doubleValue() 
					+ "; 4 week high=" + stat.getStatisticValue() + " [" + stat.getStatId() + "]");
			if (price.getHighPrice().doubleValue() > stat.getStatisticValue().doubleValue()) {
				signalSvc.createSignal(new StockSignal(price.getPriceId(), FOUR_WK_BRK_SIG, price.getTickerSymbol(), price.getPriceDate()));
				logger.debug("evaluatePrice - create new signal for " + price.getPriceId());
			}
		}
		else {
			logger.debug("evaluatePrice - statistic not found for " + statId);
		}
	}
	
	@Override
	public void scan(String tickerSymbol) {
		List<StockStatistic> statList = statSvc.retrieveStatList(tickerSymbol, FOUR_WK_HIGH);
		logger.debug("scan - found " + statList.size() + " statistics for " + tickerSymbol);
		List<StockPrice> priceList = priceSvc.retrievePrices(tickerSymbol);
		for (StockPrice price: priceList) {
			this.evaluatePrice(price, statList);
		}
	}

}
