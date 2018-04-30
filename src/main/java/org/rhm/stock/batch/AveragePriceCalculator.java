package org.rhm.stock.batch;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.rhm.stock.domain.AveragePrice;
import org.rhm.stock.domain.StockAveragePrice;
import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockTicker;
import org.rhm.stock.handler.AvgPriceFactory;
import org.rhm.stock.service.AveragePriceService;
import org.rhm.stock.service.PriceService;
import org.rhm.stock.service.TickerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Qualifier("avgPriceCalc")
@Component
public class AveragePriceCalculator implements BatchJob {
	@Autowired
	private TickerService tickerSvc = null;
	@Autowired
	private PriceService priceSvc = null;
	@Autowired
	private AveragePriceService avgPriceSvc = null;
	private Logger logger = LoggerFactory.getLogger(AveragePriceCalculator.class);
	
	private int processTicker(StockTicker ticker) {
		int errors = 0;
		logger.debug("processTicker - calcualting average prices for " + ticker.getTickerSymbol());
		List<StockPrice> priceList = priceSvc.retrievePrices(ticker.getTickerSymbol());
		while (priceList.size() > 10) {
			if (!this.processPriceList(priceList)) {
				errors++;
			}
			priceList.remove(0);
		}
		return errors;
	}
	
	private boolean processPriceList(List<StockPrice> priceList) {
		boolean success = false;
		int[] days = {10, 50, 200};
		StockPrice firstPrice = priceList.get(0);
		StockAveragePrice avgPrice = new StockAveragePrice();
		avgPrice.setPriceId(firstPrice.getPriceId());
		avgPrice.setTickerSymbol(firstPrice.getTickerSymbol());
		List<AveragePrice> avgList = AvgPriceFactory.calculateAvgPrices(priceList, days);
		avgPrice.setAvgList(avgList);
		if (avgPriceSvc.createAveragePrice(avgPrice) != null) {
			logger.debug("processPriceList - saved average prices for " + avgPrice.getPriceId());
			success = true;
		}
		return success;
	}
	
	private int processTickers(List<StockTicker> tickerList) {
		int success = 100;
		int tickerCnt = 0;
		for (StockTicker ticker: tickerList) {
			if (this.processTicker(ticker) == 0) {
				tickerCnt++;
			}
		}
		if (tickerList.size() == tickerCnt) {
			success = 0;
		}
		return success;
	}
	
	@Async
	@Override
	public CompletableFuture<BatchStatus> run() {
		BatchStatus status = new BatchStatus();
		List<StockTicker> tickerList = tickerSvc.retrieveTickerList();
		this.processTickers(tickerList);
		return CompletableFuture.completedFuture(status);
	}

}
