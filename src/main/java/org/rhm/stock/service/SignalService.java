package org.rhm.stock.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.rhm.stock.domain.IbdStatistic;
import org.rhm.stock.domain.SignalType;
import org.rhm.stock.domain.SignalTypeCount;
import org.rhm.stock.domain.StockAveragePrice;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.dto.StockSignalDisplay;
import org.rhm.stock.repository.AveragePriceRepo;
import org.rhm.stock.repository.IbdStatisticRepo;
import org.rhm.stock.repository.SignalRepo;
import org.rhm.stock.repository.SignalTypeCountRepo;
import org.rhm.stock.repository.SignalTypeRepo;
import org.rhm.stock.util.StockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class SignalService {
	@Autowired
	private SignalTypeRepo signalTypeRepo = null;
	@Autowired
	private SignalRepo signalRepo = null;
	@Autowired
	private SignalTypeCountRepo sigCntRepo = null;
	@Autowired
	private IbdStatisticRepo ibdRepo = null;
	@Autowired
	private AveragePriceRepo avgPriceRepo = null;

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
		Example<StockSignal> example = Example.of(signal);
		if (!signalRepo.exists(example)) {
			newSignal = signalRepo.save(signal);
		}
		else {
			logger.debug("createSignal - signal " + signal.getSignalId() + " already exists");
		}
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
	
	public List<StockSignalDisplay> findSignalsByTypeAndDate(String signalType, String priceDateStr) {
		List<StockSignal> signalList = null;
		List<StockSignalDisplay> sigDisplayList = new ArrayList<StockSignalDisplay>();
		Date priceDate = null;
		try {
			priceDate = StockUtil.stringToDate(priceDateStr);
		} 
		catch (ParseException e) {
			logger.warn("findSignalsByTypeAndDate - " + e.getMessage());
		}
		Map<String, IbdStatistic> ibdStatMap = this.latestIbdStats();
		Map<String, StockAveragePrice> avgPriceMap = this.avgPricesByDate(priceDate);
		signalList = this.findSignalsByTypeAndDate(signalType, priceDate);
		if (avgPriceMap != null) {
			signalList.forEach((signal)->{
				StockSignalDisplay sigDisp = new StockSignalDisplay(signal);
				sigDisp.setAvgPrice(avgPriceMap.get(signal.getTickerSymbol()));
				sigDisp.setIbdLatestStat(ibdStatMap.get(signal.getTickerSymbol()));
				sigDisplayList.add(sigDisp);
				
			});
		}
		return sigDisplayList;
	}
	
	private List<String> extractTickerFromSignal(List<StockSignal> signalList) {
		List<String> tickerList = new ArrayList<String>();
		for (StockSignal signal: signalList) {
			tickerList.add(signal.getTickerSymbol());
		}
		logger.debug("extractTickerFromSignal - " + tickerList.toString());
		return tickerList;
	}
	
	private Map<String, IbdStatistic> latestIbdStats() {
		Map<String, IbdStatistic> ibdStatMap = new HashMap<String, IbdStatistic>();
		IbdStatistic latestStat = ibdRepo.findTopByOrderByPriceDateDesc();
		List<IbdStatistic> latestStats = null;
		if (latestStat != null) {
			latestStats = ibdRepo.findByPriceDate(latestStat.getPriceDate());
			latestStats.forEach((stat)->{ibdStatMap.put(stat.getTickerSymbol(), stat);});
		}
		return ibdStatMap;
	}
	
	private Map<String, StockAveragePrice> avgPricesByDate(Date priceDate) {
		Map<String, StockAveragePrice> avgPriceMap = new HashMap<String, StockAveragePrice>();
		List<StockAveragePrice> avgPrices = avgPriceRepo.findByPriceDate(priceDate);
		if (avgPrices != null) {
			avgPrices.forEach((avgPrice)->{avgPriceMap.put(avgPrice.getTickerSymbol(), avgPrice);});
		}
		return avgPriceMap;
	}
	
	public List<StockSignalDisplay> findSignalsByTypeAndDate(String signalType, String overlaySignalType, String priceDateParam) {
		logger.debug("findSignalsByTypeAndDate - signalType=" + signalType + "; overlaySignalType=" + overlaySignalType + "; priceDate=" + priceDateParam);
		List<StockSignal> baseSignalList = null;
		Date priceDate = null;
		try {
			priceDate = StockUtil.stringToDate(priceDateParam);
		} 
		catch (ParseException e) {
			logger.warn("findSignalsByTypeAndDate - parse price date: " + e.getMessage());
		}
		Map<String, IbdStatistic> ibdStatMap = this.latestIbdStats();
		Map<String, StockAveragePrice> avgPriceMap = this.avgPricesByDate(priceDate);
		baseSignalList = this.findSignalsByTypeAndDate(signalType, priceDate);
		List<String> overlayTickerList = null;
		overlayTickerList =	this.extractTickerFromSignal(this.findSignalsByTypeAndDate(overlaySignalType, priceDate));
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
			signalDisplay.setAvgPrice(avgPriceMap.get(signal.getTickerSymbol()));
			signalDisplay.setIbdLatestStat(ibdStatMap.get(signal.getTickerSymbol()));
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
	
	public List<SignalTypeCount> findSignalCounts(String signalCode) {
		return sigCntRepo.findBySignalCodeOrderBySignalDateDesc(signalCode);
	}
}
