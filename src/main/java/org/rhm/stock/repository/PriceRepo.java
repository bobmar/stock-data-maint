package org.rhm.stock.repository;

import java.util.List;

import org.rhm.stock.domain.StockPrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceRepo extends MongoRepository<StockPrice, String>, PriceCustomRepo {
	public List<StockPrice> findByTickerSymbol(String tickerSymbol);
	public StockPrice findTopByTickerSymbolOrderByPriceDateDesc(String tickerSymbol);
}
