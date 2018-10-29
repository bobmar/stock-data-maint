package org.rhm.stock.handler.ticker;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.rhm.stock.domain.IbdStatistic;

public class IbdStatRowExtractor {
	/*
	 * Symbol, Company Name, Price, Price Chg., Price % Chg., Volume (000), Volume % Chg., EPS % Chg (Latest Qtr), EPS % Chg (Prior Qtr), Sale % Chg (Last Qtr), EPS Est % Chg (Currrent Qtr), EPS Est % Chg (Current Yr), Composite Rating, EPS Rating, RS Rating, SMR Rating, Acc/Dis Rating, Group Rel Str Rating
	 * Symbol, Company Name, Industry Group Rank, Price, Price Change, Price % Change, % off High, Volume, Volume % Change, Composite Rating, EPS Rating, RS Rating, SMR Rating, ACC/DIS Rating, Group Rel Str Rating, EPS % Change(Latest Qtr), EPS % Change(Prev Qtr), EPS EST % Change(Current Qtr), EPS EST % Change(Current Yr), Sales % Change(Last Qtr), Annual ROE, Annual Profit Margin (Latest Yr), Mgmt Own %, Qtrs of Rising Sponsorship
	 */

	private static final String STAT_SYMBOL = 	"Symbol"; 
	private static final String STAT_COMPOSITE = "Composite Rating";
	private static final String STAT_EPS = "EPS Rating";
	private static final String STAT_RS = "RS Rating";
	private static final String STAT_SMR = "SMR Rating";
	private static final String STAT_ACC_DIS = "Acc/Dis Rating";
	private static final String STAT_GRP = "Group Rel Str Rating";
	
	private int columnIndex(List<String> columnNames, String columnName) {
		int col = -1;
		for (String colName: columnNames) {
			col++;
			if (colName.equals(columnName)) {
				break;
			}
		}
		return col;
	}
	
	public IbdStatistic transformRow(Row row, List<String> columnNames) {
		IbdStatistic ibdStat = new IbdStatistic();
		ibdStat.setAccumDist(row.getCell(columnIndex(columnNames, STAT_ACC_DIS)).getStringCellValue());
		ibdStat.setCompositeRating(row.getCell(columnIndex(columnNames, STAT_COMPOSITE)).getStringCellValue());
		ibdStat.setEpsRating(row.getCell(columnIndex(columnNames, STAT_EPS)).getStringCellValue());
		ibdStat.setGroupStrength(row.getCell(columnIndex(columnNames, STAT_GRP)).getStringCellValue());
		ibdStat.setRelativeStrength(row.getCell(columnIndex(columnNames, STAT_RS)).getStringCellValue());
		ibdStat.setSalesMarginRoe(row.getCell(columnIndex(columnNames, STAT_SMR)).getStringCellValue());
		ibdStat.setTickerSymbol(row.getCell(columnIndex(columnNames, STAT_SYMBOL)).getStringCellValue());
		return ibdStat;
	}
}
