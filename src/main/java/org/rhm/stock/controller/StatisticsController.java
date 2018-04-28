package org.rhm.stock.controller;

import org.rhm.stock.controller.dto.GeneralResponse;
import org.rhm.stock.domain.StatisticType;
import org.rhm.stock.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {
	@Autowired
	private StatisticService statSvc = null;
	
	@RequestMapping(value="/stocks/stat/type", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	private GeneralResponse createStatType(@RequestBody StatisticType statType) {
		GeneralResponse response = new GeneralResponse();
		if (statSvc.createStatType(statType) != null) {
			response.setMessageText("Created statistic type successfully: " + statType.getStatisticDesc());
		}
		return response;
	}
}
