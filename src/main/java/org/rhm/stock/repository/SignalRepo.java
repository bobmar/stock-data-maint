package org.rhm.stock.repository;

import org.rhm.stock.domain.StockSignal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SignalRepo extends MongoRepository<StockSignal, String> {

}
