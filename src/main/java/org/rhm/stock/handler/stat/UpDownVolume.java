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
@Qualifier("upDownVolCalc")
public class UpDownVolume implements StatisticCalculator {
	@Autowired
	private StatisticService statSvc = null;
	private static final String UP_DOWN_VOL_50 = "UPDNVOL50";
	private Logger logger = LoggerFactory.getLogger(UpDownVolume.class);
	
	private void calcUpDownRatio(List<StockPrice> priceList) {
		double upVolume = 0, downVolume = 0;
		double upDownRatio = 0.0;
		double priceDiff = 0.0;
		StockPrice currPrice = null, prevPrice = null;
		for (int i = 0; i < 49; i++) {
			currPrice = priceList.get(i);
			prevPrice = priceList.get(i+1);
			priceDiff = currPrice.getClosePrice().subtract(prevPrice.getClosePrice()).doubleValue();
			logger.debug("calcUpDownRatio - " + currPrice.getTickerSymbol() + " curr price=" + currPrice.getClosePrice() + "; prev price=" + prevPrice.getClosePrice() + "; diff=" + priceDiff);
			if (priceDiff >= 0) {
				upVolume += currPrice.getVolume().longValue();
				logger.debug("calcUpDownRatio - up volume=" + upVolume);
			}
			else {
				downVolume += currPrice.getVolume().longValue();
				logger.debug("calcUpDownRatio - down volume=" + downVolume);
			}
		}
		logger.debug("calcUpDownRatio - " + currPrice.getTickerSymbol() + " up volume=" + upVolume + "/ down volume=" + downVolume);
		if (downVolume > 0) {
			upDownRatio = upVolume / downVolume;
			logger.debug("calcUpDownRatio - up/down ratio=" + upDownRatio);
			statSvc.createStatistic(new StockStatistic(currPrice.getPriceId(), UP_DOWN_VOL_50, BigDecimal.valueOf(upDownRatio), currPrice.getTickerSymbol(), currPrice.getPriceDate()));
		}
	}
	
	@Override
	public void calculate(List<StockPrice> priceList) {
		while (priceList.size() > 51) {
			logger.debug("calculate - price list contains " + priceList.size() + " entries");
			this.calcUpDownRatio(priceList.subList(0, 50));
			priceList.remove(0);
		}
	}

}
