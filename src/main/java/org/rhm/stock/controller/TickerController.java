package org.rhm.stock.controller;

import java.util.Date;

import org.rhm.stock.controller.dto.GeneralResponse;
import org.rhm.stock.service.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TickerController {
	@Autowired
	private TickerService tickerSvc = null;
	
	@RequestMapping(value="/stocks/ticker/{tickerSymbol}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public GeneralResponse createTicker(@PathVariable String tickerSymbol) {
		GeneralResponse response = new GeneralResponse();
		response.setMessageText(tickerSvc.createTicker(tickerSymbol.toUpperCase()));
		response.setRequestDate(new Date());
		return response;
	}
}
