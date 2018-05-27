package org.rhm.stock.batch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockTicker;
import org.rhm.stock.handler.stat.DailyPriceVsAvg;
import org.rhm.stock.handler.stat.StatisticCalculator;
import org.rhm.stock.handler.stat.StdDeviation;
import org.rhm.stock.handler.stat.UpDownVolume;
import org.rhm.stock.service.PriceService;
import org.rhm.stock.service.TickerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("statsCalcJob")
public class StatisticsCalcJob implements BatchJob {
	
	@Autowired
	@Qualifier("priceChgCalc")
	private StatisticCalculator priceChgCalc = null;

	@Autowired
	@Qualifier("highLowPriceCalc")
	private StatisticCalculator highLowPriceCalc = null;
	
	@Autowired
	@Qualifier("upDownVolCalc")
	private UpDownVolume upDownVolCalc = null;
	
	@Autowired
	@Qualifier("stdDeviation")
	private StdDeviation stdDeviation = null;
	
	@Autowired
	@Qualifier("dailyPriceVsAvg")
	private DailyPriceVsAvg dailyPriceVsAvg = null;
	
	@Autowired
	private PriceService priceSvc = null;
	@Autowired
	private TickerService tickerSvc = null;
	
	private List<StatisticCalculator> calcList = null;
	private Logger logger = LoggerFactory.getLogger(StatisticsCalcJob.class);

	@PostConstruct
	public void init() {
		this.calcList = this.calcList();
	}
	
	private List<StatisticCalculator> calcList() {
		List<StatisticCalculator> calcList = new ArrayList<StatisticCalculator>();
		calcList.add(priceChgCalc);
		calcList.add(highLowPriceCalc);
		calcList.add(upDownVolCalc);
		calcList.add(stdDeviation);
		calcList.add(dailyPriceVsAvg);
		return calcList;
	}

	private void processPriceList(List<StockPrice> priceList) {
		List<StockPrice> workingList = new ArrayList<StockPrice>();
		for (StatisticCalculator calc: calcList) {
			workingList.clear();
			priceList.forEach(workingList::add);
			calc.calculate(workingList);
		}
	}
	
	private int processTicker(String tickerSymbol) {
		List<StockPrice> priceList = this.priceSvc.retrievePrices(tickerSymbol);
		logger.debug("processTicker - retrieved " + priceList.size() + " prices for " + tickerSymbol);
		this.processPriceList(priceList);
		return 0;
	}
	
	@Override
	public BatchStatus run() {
		BatchStatus status = new BatchStatus();
		List<StockTicker> tickerList = this.tickerSvc.retrieveTickerList();
		logger.debug("run - processing " + tickerList.size() + " tickers");
		int processedCnt = 0;
		for (StockTicker ticker: tickerList) {
			this.processTicker(ticker.getTickerSymbol());
			processedCnt++;
			logger.debug("run - " + processedCnt + ") " + ticker.getTickerSymbol() + " processed" );
		}
		status.setCompletionMsg("Processed " + processedCnt + " tickers");
		status.setJobClass(this.getClass().getName());
		status.setStatusDate(new Date());
		status.setSuccess(true);
		logger.debug("run - finished processing statistics");
		return status;
	}

}
