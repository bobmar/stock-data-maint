package org.rhm.stock.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.rhm.stock.domain.StockPrice;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.dto.CompositePrice;
import org.rhm.stock.dto.PriceBean;
import org.rhm.stock.io.YahooPriceDownloader;
import org.rhm.stock.repository.PriceRepo;
import org.rhm.stock.repository.StatisticRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
	@Autowired
	private PriceRepo priceRepo = null;
	@Autowired
	private CompositePriceService cpSvc = null;
	@Autowired
	private YahooPriceDownloader priceDownloader = null;
	private Logger logger = LoggerFactory.getLogger(PriceService.class);
	private DateFormat dtFmt = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<StockPrice> retrieveSourcePrices(String tickerSymbol, int days) {
		List<PriceBean> priceBeanList = priceDownloader.downloadPrices(tickerSymbol, days);
		List<StockPrice> priceList = new ArrayList<StockPrice>();
		for (PriceBean bean: priceBeanList) {
			StockPrice price = new StockPrice();
			price.setPriceId(tickerSymbol + ":" + dtFmt.format(bean.getDate()));
			price.setClosePrice(BigDecimal.valueOf(bean.getClosePrice()));
			price.setHighPrice(BigDecimal.valueOf(bean.getHighPrice()));
			price.setLowPrice(BigDecimal.valueOf(bean.getLowPrice()));
			price.setOpenPrice(BigDecimal.valueOf(bean.getOpenPrice()));
			price.setPriceDate(bean.getDate());
			price.setTickerSymbol(tickerSymbol);
			price.setVolume(bean.getVolume());
			priceList.add(price);
		}
		return priceList;
	}
	
	public List<StockPrice> retrievePrices(String tickerSymbol) {
		List<StockPrice> priceList = priceRepo.findByTickerSymbol(tickerSymbol);
		return priceList.stream()
				.sorted((o1,o2) -> {return o1.getPriceDate().compareTo(o2.getPriceDate()) * -1;})
				.collect(Collectors.toList());
	}
	
	public StockPrice saveStockPrice(StockPrice price) {
		StockPrice savedPrice = priceRepo.save(price);
		return savedPrice;
	}
	
	public List<StockPrice> saveStockPrice(List<StockPrice> priceList) {
		List<StockPrice> savedPrices = priceRepo.saveAll(priceList);
		logger.debug("saveStockPrice(List) - saved " + priceList.size() + " prices");
		return savedPrices;
	}
	
	public CompositePrice retrieveCurrentPrice(String tickerSymbol) {
		StockPrice price = priceRepo.findTopByTickerSymbolOrderByPriceDateDesc(tickerSymbol);
		CompositePrice cPrice = cpSvc.compositePriceFactory(price.getPriceId());
		return cPrice;
	}
	
	public StockPrice findStockPrice(String priceId) {
		return priceRepo.findById(priceId).get();
				
	}
	
}
