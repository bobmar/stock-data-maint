package org.rhm.stock.handler.maint;

import java.util.Date;

import org.rhm.stock.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("pruneStat")
public class PruneStatistic implements MaintHandler {
	@Autowired
	private StatisticService statSvc = null;
	
	@Override
	public void prune(Date deleteBefore) {
		// TODO Auto-generated method stub

	}

}
