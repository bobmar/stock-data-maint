package org.rhm.stock.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.PostConstruct;

import org.rhm.stock.domain.StockTicker;
import org.rhm.stock.handler.signal.SignalScanner;
import org.rhm.stock.service.TickerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Qualifier("signalScan")
public class SignalScanJob implements BatchJob {
	@Autowired
	@Qualifier("priceBreakout")
	private SignalScanner priceBreakout = null;
	@Autowired
	@Qualifier("priceGap")
	private SignalScanner priceGap = null;
	@Autowired
	@Qualifier("closeNearHigh")
	private SignalScanner closeNearHigh = null;
	@Autowired
	private TickerService tickerSvc = null;
	private Logger logger = LoggerFactory.getLogger(SignalScanJob.class);
	private List<SignalScanner> scannerList = null;
	
	private List<SignalScanner> scannerList() {
		List<SignalScanner> scannerList = new ArrayList<SignalScanner>();
		scannerList.add(priceBreakout);
		scannerList.add(priceGap);
		scannerList.add(closeNearHigh);
		logger.debug("scannerList - loaded " + scannerList.size() + " signal scanners");
		return scannerList;
	}
	
	@PostConstruct
	private void init() {
		this.scannerList = this.scannerList();
	}
	
	@Override
	public CompletableFuture<BatchStatus> run() {
		List<StockTicker> tickerList = tickerSvc.retrieveTickerList();
		BatchStatus batchStatus = new BatchStatus();
		batchStatus.setStatusDate(new Date());
		batchStatus.setSuccess(true);
		batchStatus.setCompletionMsg("Triggered the signal scanner successfully");
		for (StockTicker ticker: tickerList) {
			for (SignalScanner scanner: scannerList) {
				logger.debug("run - processing ticker " + ticker.getTickerSymbol());
				scanner.scan(ticker.getTickerSymbol());
			}
		}
		return CompletableFuture.completedFuture(batchStatus);
	}

}
