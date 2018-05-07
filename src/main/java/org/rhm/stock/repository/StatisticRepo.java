package org.rhm.stock.repository;

import java.util.List;

import org.rhm.stock.domain.StockStatistic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticRepo extends MongoRepository<StockStatistic, String> {
	public List<StockStatistic> findByTickerSymbol(String tickerSymbol);
	public List<StockStatistic> findByTickerSymbolAndStatisticType(String tickerSymbol, String statisticType);
}
