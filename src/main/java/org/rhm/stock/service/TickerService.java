package org.rhm.stock.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.rhm.stock.domain.StockTicker;
import org.rhm.stock.dto.FinanceProfile;
import org.rhm.stock.io.YahooDownload;
import org.rhm.stock.repository.TickerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TickerService {
	
	@Autowired
	private TickerRepo tickerRepo = null;
	@Autowired
	private YahooDownload download = null;
	
	public String createTicker(String tickerSymbol) {
		StockTicker stockTicker = null;
		String message = null;
		Map<String, Object> companyInfo = download.retrieveProfile(tickerSymbol);
		if (companyInfo == null) {
			message = "Unable to find " + tickerSymbol + " in Yahoo Finance";
		}
		else {
			if (tickerRepo.existsById(tickerSymbol)) {
				message = "Ticker " + tickerSymbol + " already exists";
			}
			else {
				FinanceProfile profile = new FinanceProfile(companyInfo);
				stockTicker = new StockTicker();
				stockTicker.setTickerSymbol(tickerSymbol);
				stockTicker.setCompanyName(profile.getLongName());
				stockTicker.setIndustryName(profile.getIndustry());
				stockTicker.setSectorName(profile.getSector());
				if (tickerRepo.insert(stockTicker) != null) {
					message = tickerSymbol + "/" + stockTicker.getCompanyName() + " was successfully created";
				}
				else {
					message = "Failed to create entry for " + tickerSymbol;
				}
			}
		}
		return message;
	}
	
	public List<StockTicker> retrieveTickerList() {
		return tickerRepo.findAll().stream()
				.sorted((o1,o2)->{return o1.getTickerSymbol().compareTo(o2.getTickerSymbol());})
				.collect(Collectors.toList());
	}
}
