package org.rhm.stock.repository;

import java.util.Date;
import java.util.List;

import org.rhm.stock.domain.StockSignal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SignalRepo extends MongoRepository<StockSignal, String>, SignalCustomRepo {
	public List<StockSignal> findBySignalTypeOrderByPriceId(String signalType);
	public StockSignal findTopByOrderByPriceDateDesc();
	public List<StockSignal> findBySignalTypeAndPriceDateOrderByTickerSymbol(String signalType, Date priceDate);
	public List<StockSignal> findByTickerSymbolAndPriceDateOrderBySignalType(String tickerSymbol, Date priceDate);
	public List<StockSignal> findByPriceId(String priceId);
}
