package org.rhm.stock.repository;

import java.util.List;

import org.rhm.stock.domain.StockTicker;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TickerRepo extends MongoRepository<StockTicker, String> {
}
