package org.rhm.stock.handler.stat;

import java.math.BigDecimal;
import java.util.List;

import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockStatistic;
import org.rhm.stock.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("priceChgCalc")
public class PriceChg implements StatisticCalculator {
	@Autowired
	private StatisticService statSvc = null;
	private static final String STAT_DLY_PCT_CHG = "DYPCTCHG";
	private static final String STAT_DLY_PRC_CHG = "DYPRCCHG";
	private static final String STAT_WK_PCT_CHG = "WKCLSPCT";
	private static final String STAT_WK_PRC_CHG = "WKCLSPRC";

	private Logger logger = LoggerFactory.getLogger(StatisticService.class);
	
	private void calcChange(StockPrice currPrice, StockPrice prevPrice, String statPctChgCode, String statPrcChgCode) {
		BigDecimal priceChg = currPrice.getClosePrice().subtract(prevPrice.getClosePrice());
		logger.debug("calcChange - price change amt for " + currPrice.getPriceId() + " is " + priceChg.doubleValue());
		logger.debug("calcChange - " + prevPrice.getPriceId() + " closing price=" + prevPrice.getClosePrice());
		logger.debug("calcChange - " + currPrice.getPriceId() + " closing price=" + currPrice.getClosePrice());
		double quotient = priceChg.doubleValue() / prevPrice.getClosePrice().doubleValue();
		BigDecimal pctChg = BigDecimal.valueOf(quotient * 100);
		statSvc.createStatistic(new StockStatistic(currPrice.getPriceId(), statPctChgCode, pctChg, currPrice.getTickerSymbol(), currPrice.getPriceDate()));
		statSvc.createStatistic(new StockStatistic(currPrice.getPriceId(), statPrcChgCode, priceChg, currPrice.getTickerSymbol(), currPrice.getPriceDate()));
	}
	
	@Override
	public void calculate(List<StockPrice> priceList) {
		while (priceList.size() > 2) {
			this.calcChange(priceList.get(0), priceList.get(1), STAT_DLY_PCT_CHG, STAT_DLY_PRC_CHG);
			if (priceList.size() > 5) {
				this.calcChange(priceList.get(0), priceList.get(4), STAT_WK_PCT_CHG, STAT_WK_PRC_CHG);
			}
			priceList.remove(0);
		}
	}

}
