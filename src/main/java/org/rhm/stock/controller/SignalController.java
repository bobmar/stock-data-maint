package org.rhm.stock.controller;

import java.util.Date;
import java.util.List;

import org.rhm.stock.controller.dto.CompositePriceRequest;
import org.rhm.stock.controller.dto.GeneralResponse;
import org.rhm.stock.controller.dto.SignalRequest;
import org.rhm.stock.domain.SignalType;
import org.rhm.stock.domain.StockSignal;
import org.rhm.stock.dto.CompositePrice;
import org.rhm.stock.service.CompositePriceService;
import org.rhm.stock.service.SignalService;
import org.rhm.stock.util.StockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignalController {
	@Autowired
	private SignalService sigSvc = null;
	@Autowired
	private CompositePriceService cPriceSvc = null;
	private Logger logger = LoggerFactory.getLogger(SignalController.class);
	@RequestMapping(value="/stocks/signal/type", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public GeneralResponse createSignalType(@RequestBody SignalType signalType) {
		GeneralResponse response = new GeneralResponse();
		SignalType newSignalType = sigSvc.createSignalType(signalType);
		response.setRequestDate(new Date());
		if (newSignalType == null) {
			response.setMessageText("Creation of " + signalType.getSignalCode() + "/" + signalType.getSignalDesc() + " failed");
		}
		else {
			response.setMessageText(signalType.getSignalDesc() + " was created successfully");
		}
		return response;
	}
	
	@RequestMapping(value="/stocks/signal/list/{signalType}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<StockSignal> findSignals(@PathVariable String signalType) {
		return sigSvc.findSignals(signalType);
	}
	
	@PostMapping(value="/stocks/signal/datetype/list")
	public List<StockSignal> findSignalsByDateAndType(@RequestBody SignalRequest request) {
		String signalDate = request.getSignalDate();
		if (signalDate == null) {
			signalDate = StockUtil.dateToString(sigSvc.findMaxDate().getPriceDate());
		}
		else {
			logger.debug(request.getSignalDate().toString());
		}
		return sigSvc.findSignalsByTypeAndDate(request.getSignalType(), signalDate);
	}
	
	@RequestMapping(value="/stocks/signal/list/multi", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<CompositePrice> findSignals(@RequestBody SignalRequest request) {
		List<StockSignal> signalList = sigSvc.findSignalsByType(request.getSignalTypeList(), request.getLookBackDays());
		return cPriceSvc.transformSignalList(signalList);
	}

	@PostMapping(value="/stocks/signal/cprice")
	public CompositePrice findCompositPrice(CompositePriceRequest request) {
		CompositePrice cPrice = null;
		cPrice = cPriceSvc.compositePriceFactory(request.getPriceId());
		return cPrice;
	}
	
	@GetMapping(value="/stocks/signal/type")
	public List<SignalType> signalTypeList() {
		List<SignalType> signalTypeList = null;
		signalTypeList = sigSvc.signalTypes();
		return signalTypeList;
	}
	
}
