package org.rhm.stock.handler.signal;

import java.util.List;

import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.service.SignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("signalCombo")
public class SignalCombo implements SignalScanner {
	@Autowired
	private SignalService signalSvc = null;
	private static final String CONFIRM_BUY_SETUP = "SEQUENTIAL";
	private Logger logger = LoggerFactory.getLogger(SignalCombo.class);
	private StockSignal cloneSignal(StockSignal signal) {
		StockSignal newSignal = new StockSignal(signal);
		newSignal.setSignalType(CONFIRM_BUY_SETUP);
		newSignal.setSignalId(signal.getPriceId() + ":" + CONFIRM_BUY_SETUP);
		return newSignal;
	}
	
	private void evaluateTdSequential(List<StockSignal> signalList) {
		StockSignal firstSignal = signalList.get(0);
		if (firstSignal.getSignalType().equals("BUYSETUP") || firstSignal.getSignalType().equals("PBUYSETUP")) {
			for (int i = 1; i < 4; i++) {
				if (signalList.get(i).getSignalType().equals("BEARFLIP")) {
					signalSvc.createSignal(this.cloneSignal(firstSignal));
					break;
				}
			}
		}
	}
	
	@Override
	public void scan(String tickerSymbol) {
		List<StockSignal> signalList = signalSvc.findSignalsByTicker(tickerSymbol);
		logger.info("scan - found " + signalList.size() + " signals for " + tickerSymbol);
		while (signalList.size() > 5) {
			this.evaluateTdSequential(signalList);
			signalList.remove(0);
		}
	}

}
