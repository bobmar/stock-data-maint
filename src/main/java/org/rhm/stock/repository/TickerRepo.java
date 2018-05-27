package org.rhm.stock.repository;

import org.rhm.stock.domain.StockTicker;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TickerRepo extends MongoRepository<StockTicker, String> {
}
