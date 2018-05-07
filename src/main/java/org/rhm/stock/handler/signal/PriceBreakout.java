package org.rhm.stock.handler.signal;

import java.util.List;

import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.domain.StockStatistic;
import org.rhm.stock.service.PriceService;
import org.rhm.stock.service.SignalService;
import org.rhm.stock.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;

public class PriceBreakout implements SignalScanner {
	@Autowired
	private PriceService priceSvc = null;
	@Autowired
	private StatisticService statSvc = null;
	@Autowired
	private SignalService signalSvc = null;
	private static final String FOUR_WK_HIGH = "HIPR4WK";
	private static final String FOUR_WK_BRK_SIG = "4WKBRK";
	private StockStatistic findStat(String statId, List<StockStatistic> statList) {
		StockStatistic stockStat = null;
		for (StockStatistic stat: statList) {
			if (stat.getStatId().equals(statId)) {
				stockStat = stat;
				break;
			}
		}
		return stockStat;
	}
	
	private void evaluatePrice(StockPrice price, List<StockStatistic> statList) {
		String statId = price.getPriceId() + ":" + FOUR_WK_HIGH;
		StockStatistic stat = this.findStat(statId, statList);
		if (price.getHighPrice().doubleValue() > stat.getStatisticValue().doubleValue()) {
			signalSvc.createSignal(new StockSignal(price.getPriceId(), FOUR_WK_BRK_SIG, price.getTickerSymbol()));
		}
	}
	
	@Override
	public void scan(String tickerSymbol) {
		List<StockStatistic> statList = statSvc.retrieveStatList(tickerSymbol, FOUR_WK_HIGH);
		List<StockPrice> priceList = priceSvc.retrievePrices(tickerSymbol);
		for (StockPrice price: priceList) {
			this.evaluatePrice(price, statList);
		}
	}

}
