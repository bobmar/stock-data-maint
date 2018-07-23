package org.rhm.stock.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.rhm.stock.controller.dto.TickerInfo;
import org.rhm.stock.domain.StockTicker;
import org.rhm.stock.dto.FinanceProfile;
import org.rhm.stock.handler.ticker.ExcelTransformer;
import org.rhm.stock.io.YahooDownload;
import org.rhm.stock.repository.TickerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class TickerService {
	
	@Autowired
	private TickerRepo tickerRepo = null;
	@Autowired
	private YahooDownload download = null;
	@Autowired
	private ExcelTransformer excel = null;
	private Logger logger = LoggerFactory.getLogger(TickerService.class);
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
	
	public int saveTickerList(List<TickerInfo> tickerList) {
		int tickersSavedCnt = 0;
		String status = null;
		for (TickerInfo info: tickerList) {
			status = this.createTicker(info.getTickerSymbol());
			if (status.contains("successfully created")) {
				tickersSavedCnt++;
			}
		}
		return tickersSavedCnt;
	}
	
	public List<TickerInfo> retrieveTickerInfo(byte[] workbookBytes) {
		List<TickerInfo> tickerInfoList = new ArrayList<TickerInfo>();
		List<String> tickerList = excel.extractTickerSymbols(workbookBytes);
		FinanceProfile profile = null;
		TickerInfo ticker = null;
		for (String tickerSymbol: tickerList) {
			profile = this.findCompanyProfile(tickerSymbol);
			ticker = new TickerInfo();
			ticker.setTickerSymbol(tickerSymbol);
			if (profile != null) {
				ticker.setCompanyName(profile.getLongName());
				if (this.tickerExists(tickerSymbol)) {
					ticker.setStatus("Ticker already exists; will not be created"); 
				}
				else {
					ticker.setStatus("OK");
				}
			}
			else {
				ticker.setStatus("Ticker symbol was not found in Yahoo Finance");
			}
			tickerInfoList.add(ticker);
			logger.info("retrieveTickerInfo - " + ticker.getTickerSymbol() + ":" + ticker.getStatus());
		}
		return tickerInfoList.stream()
				.sorted((o1,o2)->{return o1.getTickerSymbol().compareTo(o2.getTickerSymbol());})
				.collect(Collectors.toList());
	}
	
	public boolean tickerExists(String tickerSymbol) {
		boolean exists = false;
		exists = tickerRepo.existsById(tickerSymbol);
		return exists;
	}
	
	public FinanceProfile findCompanyProfile(String tickerSymbol) {
		Map<String,Object> profile = download.retrieveProfile(tickerSymbol);
		FinanceProfile fp = null;
		if (profile != null) {
			fp = new FinanceProfile(profile);
		}
		return fp;
	}

	public Page<StockTicker> findPage(Pageable pageable) {
		Sort sort = Sort.by(Direction.ASC, "tickerSymbol");
		PageRequest pageReq = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		return tickerRepo.findAll(pageReq);
	}
	
}
