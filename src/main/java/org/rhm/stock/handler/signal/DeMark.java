package org.rhm.stock.handler.signal;

import java.util.List;

import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.service.PriceService;
import org.rhm.stock.service.SignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("deMark")
public class DeMark implements SignalScanner {
	@Autowired
	private PriceService priceSvc = null;
	@Autowired
	private SignalService signalSvc = null;
	private Logger logger = LoggerFactory.getLogger(DeMark.class);
	private static final String BEARISH_PRICE_FLIP = "BEARFLIP";
	private static final String BUY_SETUP = "BUYSETUP";
	
	private boolean bearishPriceFlip(List<StockPrice> priceList) {
		int conditionFound = 0, currIdx = 0, earlierIdx = 4;
		StockPrice currPrice = null, earlierPrice = null;
		if (priceList.size() >= 6) {
			currPrice = priceList.get(currIdx);
			earlierPrice = priceList.get(earlierIdx);
			if (currPrice.getClosePrice().compareTo(earlierPrice.getClosePrice()) == -1) {
				conditionFound++;
			}
			currIdx++;
			earlierIdx++;
			currPrice = priceList.get(currIdx);
			earlierPrice = priceList.get(earlierIdx);
			if (currPrice.getClosePrice().compareTo(earlierPrice.getClosePrice()) == 1) {
				conditionFound++;
			}
		}
		return conditionFound == 2;
	}
	 
	private boolean buySetup(List<StockPrice> priceList) {
		int conditionFound = 0;
		StockPrice currPrice = null, earlierPrice = null;
		if (priceList.size() > 13) {
			for (int i = 0; i < 9; i++) {
				currPrice = priceList.get(i);
				earlierPrice = priceList.get(i+4);
				if (currPrice.getClosePrice().compareTo(earlierPrice.getClosePrice()) == -1) {
					conditionFound++;
				}
			}
		}
		
		return conditionFound == 9;
	}
	 
	private void detectDeMarkIndicators(List<StockPrice> priceList) {
		if (this.bearishPriceFlip(priceList)) {
			this.signalSvc.createSignal(new StockSignal(priceList.get(0), BEARISH_PRICE_FLIP));
		}
		if (this.buySetup(priceList)) {
			this.signalSvc.createSignal(new StockSignal(priceList.get(0), BUY_SETUP));
		}
	}
	
	@Override
	public void scan(String tickerSymbol) {
		List<StockPrice> priceList = priceSvc.retrievePrices(tickerSymbol);
		logger.info("scan - found " + priceList.size() + " prices for " + tickerSymbol);
		while (priceList.size() > 14) {
			this.detectDeMarkIndicators(priceList);
			priceList.remove(0);
		}
	}

}
