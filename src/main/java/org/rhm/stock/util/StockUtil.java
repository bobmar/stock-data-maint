package org.rhm.stock.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StockUtil {
	private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Date toMongoDate(Date inDate) throws ParseException {
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(inDate);
//		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
//		cal.set(Calendar.HOUR_OF_DAY, 0);
//		return cal.getTime();
		String dateStr = df.format(inDate);
		return df.parse(dateStr);
	}
	
	public static Date stringToDate(String dateStr) {
		Date dateObj = null;
		try {
			dateObj = df.parse(dateStr);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
		return dateObj;
	}
	
	public static String dateToString(Date date) {
		String dateStr = null;
		dateStr = df.format(date);
		return dateStr;
	}
}
