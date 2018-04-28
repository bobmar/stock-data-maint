package org.rhm.stock.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class YahooDownload {
	private String protocol = "http://";
	private String domain = "query2.finance.yahoo.com";
	private String baseUri = "/v10/finance";
	private String profilePrefix = "/quoteSummary/";
	private String profileSuffix = "?formatted=true&lang=en-US&region=US&modules=assetProfile%2Cprice&corsDomain=finance.yahoo.com";
	private String keyStatPrefix = "/quoteSummary/";
	private String keyStatSuffix = "?formatted=true&lang=en-US&region=US&modules=defaultKeyStatistics%2CfinancialData%2CcalendarEvents&corsDomain=finance.yahoo.com";

	private Logger logger = LoggerFactory.getLogger(YahooDownload.class);
	//https://query2.finance.yahoo.com/v10/finance/quoteSummary/WFC?formatted=true&crumb=%2FsTY4E5V.U%2F&lang=en-US&region=US&modules=defaultKeyStatistics%2CfinancialData%2CcalendarEvents&corsDomain=finance.yahoo.com

	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getBaseUri() {
		return baseUri;
	}
	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}
	public String getProfileSuffix() {
		return profileSuffix;
	}
	public void setProfileSuffix(String profileSuffix) {
		this.profileSuffix = profileSuffix;
	}
	public String getKeyStatPrefix() {
		return keyStatPrefix;
	}
	public void setKeyStatPrefix(String keyStatPrefix) {
		this.keyStatPrefix = keyStatPrefix;
	}
	public String getKeyStatSuffix() {
		return keyStatSuffix;
	}
	public void setKeyStatSuffix(String keyStatSuffix) {
		this.keyStatSuffix = keyStatSuffix;
	}
	public String getProfilePrefix() {
		return profilePrefix;
	}
	public void setProfilePrefix(String profilePrefix) {
		this.profilePrefix = profilePrefix;
	}
	public StringBuilder baseUrl() {
		StringBuilder domainBuilder = new StringBuilder(domain);
		if (domainBuilder.toString().startsWith("query1")) {
			domainBuilder.replace(0, 6, "query2");
		}
		else {
			domainBuilder.replace(0, 6, "query1");
		}
		domain = domainBuilder.toString();
		StringBuilder sb = new StringBuilder();
		sb.append(protocol);
		sb.append(domain);
		sb.append(baseUri);
		return sb;
	}
	
	public String profileUrl(String tickerSymbol) {
		StringBuilder sb = this.baseUrl();
		sb.append(profilePrefix.trim());
		sb.append(tickerSymbol);
		sb.append(profileSuffix.trim());
		return sb.toString();
	}
	
	public String keyStatUrl(String tickerSymbol) {
		StringBuilder sb = this.baseUrl();
		sb.append(keyStatPrefix.trim());
		sb.append(tickerSymbol);
		sb.append(keyStatSuffix.trim());
		return sb.toString();
	}
	
	public String optionsUrl(String tickerSymbol) {
		StringBuilder sb = new StringBuilder();
		sb.append(protocol);
		sb.append(domain);
		sb.append("/v7/finance/options/");
		sb.append(tickerSymbol);
		sb.append("?formatted=true&lang=en-US&region=US&corsDomain=finance.yahoo.com");
		return sb.toString();
	}
	
	public HttpURLConnection createConnection(String urlStr) {
		URL url = null;
		HttpURLConnection conn = null;
		logger.debug("URL:" + urlStr);
		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection)url.openConnection();
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public String retrieve(HttpURLConnection urlConn) {
		InputStream is = null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] chunk = new byte[1024*256];
		try {
			is = urlConn.getInputStream();
			int bytesRead = -1;
			while ((bytesRead = is.read(chunk)) != -1) {
				os.write(chunk,0, bytesRead);
			}
			os.flush();
			is.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(os.toByteArray());
	}
	
	public InputStream retrieveStream(HttpURLConnection urlConn) {
		InputStream is = null;
		try {
			is = urlConn.getInputStream();
		} 
		catch (IOException e) {
			logger.error(e.getMessage());
		}
		return is;
	}
	
	private Map<String,Object> jsonToMap(String jsonStr) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String,Object> map = null;
		try {
			map = mapper.readValue(jsonStr, new TypeReference<Map<String, Object>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private String streamToJsonStr(InputStream is) {
		byte[] chunk = new byte[512];
		int bytesRead = 0;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			while ((bytesRead = is.read(chunk)) > -1) {
				os.write(chunk, 0, bytesRead);
			}
			os.flush();
			os.close();
			is.close();
			logger.debug(os.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return os.toString();
	}
	
	public Map<String,Object> retrieveProfile(String tickerSymbol) {
		Map<String,Object> profile = null;
		String urlStr = this.profileUrl(tickerSymbol);
		InputStream is = this.retrieveStream(this.createConnection(urlStr));
		if (is != null) {
			profile = this.jsonToMap(this.streamToJsonStr(is));
		}
		return profile;
	}
	
	public Map<String,Object> retrieveKeyStat(String tickerSymbol) {
		Map<String,Object> keyStat = null;
		String urlStr = this.keyStatUrl(tickerSymbol);
		InputStream is = this.retrieveStream(this.createConnection(urlStr));
		if (is != null) {
			keyStat = jsonToMap(this.streamToJsonStr(is));
		}
		return keyStat;
	}
	
	public Map<String,Object> retrieveOptionChain(String tickerSymbol, String expirationDate) {
		Map<String,Object> optionChain = null;
		String urlStr = this.optionsUrl(tickerSymbol);
		if (expirationDate != null) {
			urlStr = urlStr + "&date=" + expirationDate;
		}
		InputStream is = this.retrieveStream(this.createConnection(urlStr));
		if (is != null) {
			optionChain = jsonToMap(this.streamToJsonStr(is));
		}
		return optionChain;
	}
}
