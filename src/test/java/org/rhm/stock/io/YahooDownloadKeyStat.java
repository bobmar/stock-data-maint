package org.rhm.stock.io;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rhm.stock.domain.YahooKeyStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootApplication
public class YahooDownloadKeyStat {

	@Autowired
	private YahooDownload download = null;

	@Test
	public void downloadKeyStat() {
		String tickerSymbol = "NOW";
		Map<String,Object> keyStatMap = download.retrieveKeyStat(tickerSymbol);
		YahooKeyStatistic keyStat = YahooKeyStatFactory.createKeyStat(tickerSymbol, keyStatMap);
		Assert.assertNotNull(keyStat);
	}
}
