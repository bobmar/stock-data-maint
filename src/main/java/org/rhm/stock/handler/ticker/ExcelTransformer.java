package org.rhm.stock.handler.ticker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.rhm.stock.domain.IbdStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExcelTransformer {

	private Logger logger = LoggerFactory.getLogger(ExcelTransformer.class);
	public List<String> extractTickerSymbols(byte[] workbookBytes) {
		List<String> tickerSymbols = new ArrayList<String>();
		Workbook workbook = this.transformBytes(workbookBytes);
		Sheet sheet = workbook.getSheetAt(0);
		logger.info("extractTickerSymbols - first row=" + sheet.getFirstRowNum() + "; last row=" + sheet.getLastRowNum());
		this.processIbdRows(tickerSymbols, sheet);
		return tickerSymbols;
	}

	private Workbook transformBytes(byte[] workbookBytes) {
		Workbook workbook = null;
		InputStream is = new ByteArrayInputStream(workbookBytes);
		try {
			workbook = new HSSFWorkbook(is);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}
	
	private void processIbdRows(List<String> tickerList, Sheet sheet) {
		Row row = null;
		IbdStatRowExtractor extractor = new IbdStatRowExtractor();
		IbdStatistic ibd = null;
		List<String> columnNames = new ArrayList<String>();
		boolean foundSymbolHdr = false;
		String tickerSymbol = null;
		for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			if (row != null && row.getCell(0) != null) {
				if (foundSymbolHdr) {
					tickerSymbol = row.getCell(0).getStringCellValue();
					logger.info("processIbdRows - " + i + ": " + tickerSymbol);
					if (tickerSymbol.length() > 0) {
						tickerList.add(tickerSymbol);
					}
					ibd = extractor.transformRow(row, columnNames);
					logger.info("processIbdRows - " + ibd.toString());
				}
				else {
					if (row.getCell(0).getStringCellValue().equals("Symbol")) {
						foundSymbolHdr = true;
						Cell cell = null;
						for (int j = 0; j < row.getLastCellNum(); j++) {
							cell = row.getCell(j);
							if (cell.getStringCellValue() != null && cell.getStringCellValue().length() > 0) {
								columnNames.add(cell.getStringCellValue());
							}
						}
					}
				}
			}
			else {
				if (foundSymbolHdr) {
					break;
				}
			}
		}
	}
}
