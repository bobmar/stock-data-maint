package org.rhm.stock.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.rhm.stock.domain.SignalType;
import org.rhm.stock.domain.SignalTypeCount;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.dto.StockSignalDisplay;
import org.rhm.stock.repository.SignalRepo;
import org.rhm.stock.repository.SignalTypeCountRepo;
import org.rhm.stock.repository.SignalTypeRepo;
import org.rhm.stock.util.StockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignalService {
	@Autowired
	private SignalTypeRepo signalTypeRepo = null;
	@Autowired
	private SignalRepo signalRepo = null;
	@Autowired
	private SignalTypeCountRepo sigCntRepo = null;
	private Logger logger = LoggerFactory.getLogger(SignalService.class);
	
	public SignalType createSignalType(SignalType signalType) {
		SignalType sigType = signalTypeRepo.save(signalType);
		return sigType;
	}
	
	public List<SignalType> signalTypes() {
		return signalTypeRepo.findAll().stream()
				.sorted((o1,o2)->o1.getSignalDesc()
					.compareTo(o2.getSignalDesc()))
				.collect(Collectors.toList());
	}
	
	public StockSignal createSignal(StockSignal signal) {
		StockSignal newSignal = null;
		newSignal = signalRepo.save(signal);
		return newSignal;
	}
	
	public List<StockSignal> findSignals(String signalType) {
		return signalRepo.findBySignalTypeOrderByPriceId(signalType);
	}
	
	public List<StockSignal> findSignalsUnsorted(String signalType) {
		return signalRepo.findBySignalType(signalType);
	}
	
	public List<StockSignal> findSignalsByType(List<String> signalTypes) {
		return this.findSignalsByType(signalTypes, 7);
	}
	
	public List<StockSignal> findSignalsByPriceId(String priceId) {
		return signalRepo.findByPriceId(priceId);
	}
	
	public List<StockSignal> findSignalsByType(List<String> signalTypes, int lookBackDays) {
		logger.debug("findSignalsByType - signalTypes: " + signalTypes.toString() + "; lookBackDays: " + lookBackDays);
		return signalRepo.findSignalsByType(signalTypes, lookBackDays);
	}
	
	public StockSignal findMaxDate() {
		return signalRepo.findTopByOrderByPriceDateDesc();
	}
	
	public List<StockSignal> findSignalsByTypeAndDate(String signalType, String priceDate) {
		List<StockSignal> signalList = null;
		try {
			signalList = this.findSignalsByTypeAndDate(signalType, StockUtil.stringToDate(priceDate));
		} 
		catch (ParseException e) {
			logger.warn("findSignalsByTypeAndDate - " + e.getMessage());
		}
		return signalList;
	}
	
	private List<String> extractTickerFromSignal(List<StockSignal> signalList) {
		List<String> tickerList = new ArrayList<String>();
		for (StockSignal signal: signalList) {
			tickerList.add(signal.getTickerSymbol());
		}
		logger.debug("extractTickerFromSignal - " + tickerList.toString());
		return tickerList;
	}
	
	public List<StockSignalDisplay> findSignalsByTypeAndDate(String signalType, String overlaySignalType, String priceDate) {
		logger.debug("findSignalsByTypeAndDate - signalType=" + signalType + "; overlaySignalType=" + overlaySignalType + "; priceDate=" + priceDate);
		List<StockSignal> baseSignalList = null;
		try {
			baseSignalList = this.findSignalsByTypeAndDate(signalType, StockUtil.stringToDate(priceDate));
		} 
		catch (ParseException e) {
			logger.warn("findSignalsByTypeAndDate (base) - " + e.getMessage());
		}
		List<String> overlayTickerList = null;
		try {
			overlayTickerList =	this.extractTickerFromSignal(this.findSignalsByTypeAndDate(overlaySignalType, StockUtil.stringToDate(priceDate)));
		} 
		catch (ParseException e) {
			logger.warn("findSignalsByTypeAndDate (overlay) - " + e.getMessage());
		}
		logger.debug("findSignalsByTypeAndDate - " + overlayTickerList.size() + " tickers found for " + overlaySignalType + " signals");
		List<StockSignalDisplay> mergedSignalList = new ArrayList<StockSignalDisplay>();
		StockSignalDisplay signalDisplay = null;
		for (StockSignal signal: baseSignalList) {
			signalDisplay = new StockSignalDisplay(signal);
			if (overlayTickerList.contains(signalDisplay.getTickerSymbol())) {
				signalDisplay.setMultiList(true);
				logger.debug("findSignalsByTypeAndDate - multiList set to true");
			}
			else {
				signalDisplay.setMultiList(false);
			}
			mergedSignalList.add(signalDisplay);
		}
		return mergedSignalList;
	}
	
	public List<StockSignal> findSignalsByTypeAndDate(String signalType, Date priceDate) {
		logger.debug("findSignalsByTypeAndDate - signalType:" + signalType + ";priceDate:" + priceDate);
		return signalRepo.findBySignalTypeAndPriceDateOrderByTickerSymbol(signalType, priceDate);
	}
	
	public List<StockSignal> findSignalsByTickerAndDate(String tickerSymbol, Date priceDate) {
		return signalRepo.findByTickerSymbolAndPriceDateOrderBySignalType(tickerSymbol, priceDate);
	}
	
	public long deleteOlderThan(Date deleteBefore) {
		return signalRepo.deleteOlderThan(deleteBefore);
	}
	
	public List<StockSignal> findSignalsByTicker(String tickerSymbol) {
		return signalRepo.findByTickerSymbolOrderByPriceDateDesc(tickerSymbol);
	}
	
	public void saveSignalCounts(List<SignalTypeCount> sigCntList) {
		sigCntRepo.saveAll(sigCntList);
	}
	
	public List<SignalTypeCount> findSignalCounts() {
		return sigCntRepo.findAll();
	}
}
