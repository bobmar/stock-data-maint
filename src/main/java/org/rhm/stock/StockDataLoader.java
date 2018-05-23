package org.rhm.stock;

import java.util.ArrayList;
import java.util.List;

import org.rhm.stock.batch.BatchJob;
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
	private Logger logger = LoggerFactory.getLogger(StockDataLoader.class);
	
	public static void main(String...args) {
//		SpringApplication.run(StockDataLoader.class, args);
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
			}
		}
		for (BatchJob job: jobList) {
			logger.debug("run - batch job: " + job.getClass().getName());
			job.run();
		}
	}
}
