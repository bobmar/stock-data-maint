package org.rhm.stock.repository;

import java.util.List;

import org.rhm.stock.domain.StockSignal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SignalRepo extends MongoRepository<StockSignal, String> {
	public List<StockSignal> findBySignalTypeOrderByPriceId(String signalType);
}
