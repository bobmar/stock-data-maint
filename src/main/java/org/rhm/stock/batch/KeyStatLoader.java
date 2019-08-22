package org.rhm.stock.batch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.rhm.stock.domain.StockTicker;
import org.rhm.stock.domain.YahooKeyStatistic;
import org.rhm.stock.io.YahooDownload;
import org.rhm.stock.io.YahooKeyStatFactory;
import org.rhm.stock.service.BatchStatusService;
import org.rhm.stock.service.KeyStatService;
import org.rhm.stock.service.TickerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("keyStatLoader")
public class KeyStatLoader implements BatchJob {
	@Autowired
	private TickerService tickerSvc = null;
	@Autowired
	private YahooDownload yahoo = null;
	@Autowired
	private KeyStatService keyStatSvc = null;
	@Autowired
	private BatchStatusService batchStatSvc = null;
	
	private Logger logger = LoggerFactory.getLogger(KeyStatLoader.class);
	
	private void processTicker(String tickerSymbol) {
		YahooKeyStatistic keyStat = null;
		Map<String,Object> keyStatMap = yahoo.retrieveKeyStat(tickerSymbol);
		keyStat = YahooKeyStatFactory.createKeyStat(tickerSymbol, keyStatMap);
		keyStatSvc.saveStatistic(keyStat);
	}
	
	@Override
	public BatchStatus run() {
		BatchStatus status = new BatchStatus(KeyStatLoader.class);
		List<StockTicker> tickerList = tickerSvc.retrieveTickerList();
		for (StockTicker ticker: tickerList) {
			logger.info("run - downloading key stats for " + ticker.getTickerSymbol());
			this.processTicker(ticker.getTickerSymbol());
		}
		status.setSuccess(true);
		status.setFinishDate(LocalDateTime.now());
		status.setCompletionMsg("Finished downloading Yahoo Key Statistics for " + tickerList.size() + " ticker symbols");
		batchStatSvc.saveBatchStatus(status);
		return status;
	}

}
