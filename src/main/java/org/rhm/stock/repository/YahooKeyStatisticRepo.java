package org.rhm.stock.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.rhm.stock.domain.YahooKeyStatistic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface YahooKeyStatisticRepo extends MongoRepository<YahooKeyStatistic, String> {
	public int deleteByCreateDateBefore(Date priceDate);
	public int deleteByTickerSymbol(String tickerSymbol);
}
