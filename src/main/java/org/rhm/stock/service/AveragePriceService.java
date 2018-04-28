package org.rhm.stock.service;

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
}
