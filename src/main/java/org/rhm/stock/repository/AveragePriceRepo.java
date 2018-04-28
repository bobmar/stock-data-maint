package org.rhm.stock.repository;

import org.rhm.stock.domain.StockAveragePrice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AveragePriceRepo extends MongoRepository<StockAveragePrice, String> {

}
