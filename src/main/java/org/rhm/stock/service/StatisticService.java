package org.rhm.stock.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.rhm.stock.domain.StatisticType;
import org.rhm.stock.domain.StockStatistic;
import org.rhm.stock.repository.StatisticRepo;
import org.rhm.stock.repository.StatisticTypeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
@Service
public class StatisticService {
	@Autowired
	private StatisticRepo statRepo = null;
	@Autowired
	private StatisticTypeRepo statTypeRepo = null;
	
	private Logger logger = LoggerFactory.getLogger(StatisticService.class);
	
	public StockStatistic createStatistic(StockStatistic stat) {
		return this.createStatistic(stat, true);
	}
	
	public StockStatistic createStatistic(StockStatistic stat, boolean forceUpdate) {
		StockStatistic newStat = null;
		if (!statRepo.exists(Example.of(stat))) {
			newStat = statRepo.save(stat);
			logger.debug("createStatistic - " + stat.getStatId() + " doesn't exist; created new");
		}
		else {
			if (forceUpdate) {
				newStat = statRepo.save(stat);
				logger.debug("createStatistic - " + stat.getStatId() + " already exists; updated");
			}
			else {
				logger.debug("createStatistic - " + stat.getStatId() + " already exists and force update not specified; skipping");
			}
		}
		return newStat;
	}
	
	public List<StockStatistic> retrieveStatList(String tickerSymbol) {
		return statRepo.findByTickerSymbol(tickerSymbol)
				.stream()
				.sorted((o1,o2)->{
						int compare = o1.getPriceId().compareTo(o2.getPriceId()) * -1;
						if (compare == 0) {
							compare = o1.getStatisticType().compareTo(o2.getStatisticType());
						}
						return compare;
					})
				.collect(Collectors.toList());
	}
	
	public List<StockStatistic> retrieveStatList(String tickerSymbol, String statisticType) {
		return statRepo.findByTickerSymbolAndStatisticType(tickerSymbol, statisticType)
				.stream()
				.sorted((o1,o2)->{return o1.getStatId().compareTo(o2.getStatId()) * -1;})
				.collect(Collectors.toList());
	}
	
	public Map<String, List<StockStatistic>> retrieveStatMap(String tickerSymbol) {
		Map<String, List<StockStatistic>> statMap = new HashMap<String, List<StockStatistic>>();
		List<StockStatistic> fullStatList = this.retrieveStatList(tickerSymbol), currStatList = null;
		for (StockStatistic stat: fullStatList) {
			currStatList = statMap.get(stat.getPriceId());
			if (currStatList == null) {
				currStatList = new ArrayList<StockStatistic>();
				statMap.put(stat.getPriceId(), currStatList);
			}
			currStatList.add(stat);
		}
		return statMap;
	}
	
	public StatisticType createStatType(StatisticType statType) {
		return statTypeRepo.save(statType);
	}
	
	public List<StatisticType> retrieveStatTypeList() {
		return statTypeRepo.findAll();
	}
}
