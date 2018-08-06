package org.rhm.stock.batch;

import java.util.Date;
import java.util.List;

import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockTicker;
import org.rhm.stock.service.PriceService;
import org.rhm.stock.service.TickerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("priceLoader")
public class PriceLoaderJob implements BatchJob {
	@Autowired
	private TickerService tickerSvc = null;
	@Autowired
	private PriceService priceSvc = null;
	private Logger logger = LoggerFactory.getLogger(PriceLoaderJob.class);
	
	private boolean processTicker(String tickerSymbol) {
		boolean success = false;
		List<StockPrice> priceList = priceSvc.retrieveSourcePrices(tickerSymbol, 365);
		logger.debug("processTicker - found " + priceList.size() + " prices for " + tickerSymbol);
		if (priceList != null && priceList.size() > 0) {
			if (priceSvc.saveStockPrice(priceList) != null) {
				success = true;
				logger.debug("processTicker - saved " + priceList.size() + " prices");
			}
		}
		return success;
	}
	
	private int processTickers(List<StockTicker> tickerList) {
		int status = 100;
		int tickerCnt = 0;
		for (StockTicker ticker: tickerList) {
			if (this.processTicker(ticker.getTickerSymbol())) {
				tickerCnt++;
			}
		}
		if (tickerCnt == tickerList.size()) {
			status = 0;
		}
		return status;
	}
	
	@Override
	public BatchStatus run() {
		BatchStatus status = new BatchStatus(this.getClass());
		List<StockTicker> tickerList = tickerSvc.retrieveTickerList();
		logger.debug("run - processing " + tickerList.size() + " tickers");
		if (this.processTickers(tickerList) == 0) {
			status.setCompletionMsg("Prices loaded successfully - " + tickerList.size() + " tickers processed");
			status.setSuccess(true);
		}
		else {
			status.setCompletionMsg("Price loading failed");
			status.setSuccess(false);
		}
		status.setFinishDate(new Date());
		return status;
	}

}
