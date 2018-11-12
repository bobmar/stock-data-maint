package org.rhm.stock.repository;

import java.util.List;

import org.rhm.stock.domain.IbdStatistic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IbdStatisticRepo extends MongoRepository<IbdStatistic, String> {

	public List<IbdStatistic> findByTickerSymbol(String tickerSymbol);
}
