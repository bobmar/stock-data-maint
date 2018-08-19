package org.rhm.stock;

import java.util.ArrayList;
import java.util.List;

import org.rhm.stock.batch.BatchJob;
import org.rhm.stock.batch.BatchStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockDataLoader implements CommandLineRunner {
	@Autowired
	@Qualifier("avgPriceCalc")
	private BatchJob avgPriceCalc = null;
	@Autowired
	@Qualifier("priceLoader")
	private BatchJob priceLoader = null;
	@Autowired
	@Qualifier("signalScan")
	private BatchJob signalScan = null;
	@Autowired
	@Qualifier("statsCalcJob")
	private BatchJob statCalc = null;
	@Autowired
	@Qualifier("pruner")
	private BatchJob pruner = null;

	private Logger logger = LoggerFactory.getLogger(StockDataLoader.class);
	
	public static void main(String...args) {
		SpringApplication app = new SpringApplication(StockDataLoader.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<BatchJob> jobList = new ArrayList<BatchJob>();
		for (String arg: args) {
			switch (arg) {
			case "AVGPRICE":
				jobList.add(avgPriceCalc);
				break;
			case "PRICELOAD":
				jobList.add(priceLoader);
				break;
			case "SIGSCAN":
				jobList.add(signalScan);
				break;
			case "STATSCALC":
				jobList.add(statCalc);
				break;
			case "PRUNER":
				jobList.add(pruner);
				break;
			}
		}
		for (BatchJob job: jobList) {
			logger.info("run - batch job: " + job.getClass().getName());
			BatchStatus status = job.run();
			logger.info("run - batch job status: " + status.getCompletionMsg());
			logger.info("run - batch job start: " + status.getStartDate());
			logger.info("run - batch job finish: " + status.getFinishDate());
		}
	}
}
