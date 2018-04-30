package org.rhm.stock.service;

import java.util.List;
import java.util.Optional;

import org.rhm.stock.domain.StockAveragePrice;
import org.rhm.stock.repository.AveragePriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AveragePriceService {
	@Autowired
	private AveragePriceRepo avgPriceRepo = null;
	
	public StockAveragePrice createAveragePrice(StockAveragePrice avgPrice) {
		return avgPriceRepo.save(avgPrice);
	}
	
	public StockAveragePrice findAvgPrice(String priceId) {
		Optional<StockAveragePrice> avgPrice = avgPriceRepo.findById(priceId);
		return avgPrice.get();
	}
	
	public List<StockAveragePrice> findAvgPriceList(String tickerSymbol) {
		List<StockAveragePrice> avgPriceList = null;
		avgPriceList = avgPriceRepo.findByTickerSymbol(tickerSymbol);
		return avgPriceList;
	}
}
