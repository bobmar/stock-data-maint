package org.rhm.stock.batch;

import java.util.Date;

import org.rhm.stock.handler.CountSignals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
@Component
@Qualifier("signalCount")
public class SignalCountJob implements BatchJob {
	@Autowired
	private CountSignals countSignals = null;

	@Override
	public BatchStatus run() {
		BatchStatus status = new BatchStatus(CountSignals.class);
		status.setStartDate(new Date());
		countSignals.run();
		status.setFinishDate(new Date());
		return status;
	}

}
