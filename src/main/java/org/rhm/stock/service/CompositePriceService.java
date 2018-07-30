package org.rhm.stock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.dto.CompositePrice;
import org.rhm.stock.repository.PriceRepo;
import org.rhm.stock.repository.StatisticRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompositePriceService {
	@Autowired
	private StatisticRepo statRepo = null;
	@Autowired
	private PriceRepo priceRepo = null;
	@Autowired
	private SignalService signalSvc = null;
//	@Autowired
//	private AveragePriceService avgPriceSvc = null;
	
	private Map<String, List<StockSignal>> signalListToMap(List<StockSignal> signalList) {
		Map<String, List<StockSignal>> signalMap = new HashMap<String, List<StockSignal>>();
		List<StockSignal> currSignalList = null;
		for (StockSignal signal: signalList) {
			currSignalList = signalMap.get(signal.getPriceId());
			if (currSignalList == null) {
				currSignalList = new ArrayList<StockSignal>();
				signalMap.put(signal.getPriceId(), currSignalList);
			}
			currSignalList.add(signal);
		}
		return signalMap;
	}
	
	public List<CompositePrice> findSignals(List<String> signalTypeList, Integer lookBackDays) {
		List<StockSignal> signalList = signalSvc.findSignalsByType(signalTypeList, lookBackDays);
		return this.transformSignalList(signalList);
	}
	
	public List<CompositePrice> transformSignalList(List<StockSignal> signalList) {
		List<CompositePrice> compPriceList = new ArrayList<CompositePrice>();
		Map<String, List<StockSignal>> signalMap = this.signalListToMap(signalList);
		CompositePrice cPrice = null;
		for (String priceId: signalMap.keySet()) {
			cPrice = new CompositePrice();
			cPrice.setPriceId(priceId);
			cPrice.setPrice(priceRepo.findById(priceId).get());
			cPrice.setSignalList(signalMap.get(priceId));
			cPrice.setStatisticList(statRepo.findByPriceId(priceId));
			cPrice.setTickerSymbol(cPrice.getStatisticList().get(0).getTickerSymbol());
			compPriceList.add(cPrice);
		}
		return compPriceList;
	}

	public List<CompositePrice> compositePriceFactory(List<String> priceIdList) {
		List<CompositePrice> compPriceList = new ArrayList<CompositePrice>();
		CompositePrice cPrice = null;
		for (String priceId: priceIdList) {
			cPrice = this.compositePriceFactory(priceId);
			compPriceList.add(cPrice);
		}
		return compPriceList;
	}
	
	public CompositePrice compositePriceFactory(String priceId) {
		CompositePrice cPrice = null;
		cPrice = new CompositePrice();
		cPrice.setPriceId(priceId);
		cPrice.setPrice(priceRepo.findById(priceId).get());
		cPrice.setSignalList(signalSvc.findSignalsByPriceId(priceId));
		cPrice.setStatisticList(statRepo.findByPriceId(priceId));
		cPrice.setTickerSymbol(cPrice.getPrice().getTickerSymbol());
//		cPrice.setAvgPrices(avgPriceSvc.findRecentAvgPriceList(cPrice.getTickerSymbol()));
		return cPrice;
	}
	
}
