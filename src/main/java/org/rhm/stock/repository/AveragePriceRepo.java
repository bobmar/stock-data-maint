package org.rhm.stock.repository;

import java.util.List;

import org.rhm.stock.domain.StockAveragePrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AveragePriceRepo extends MongoRepository<StockAveragePrice, String> {

	public List<StockAveragePrice> findByTickerSymbol(String tickerSymbol);
}
