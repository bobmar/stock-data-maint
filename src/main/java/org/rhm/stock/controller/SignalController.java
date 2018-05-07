package org.rhm.stock.controller;

import java.util.Date;

import org.rhm.stock.controller.dto.GeneralResponse;
import org.rhm.stock.domain.SignalType;
import org.rhm.stock.service.SignalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignalController {
	@Autowired
	private SignalService sigSvc = null;
	
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
}
