package org.rhm.stock.controller.dto;

import java.util.Date;

public class StatListRequest {
	private Date statDate = null;
	private String statCode = null;
	public Date getStatDate() {
		return statDate;
	}
	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}
	public String getStatCode() {
		return statCode;
	}
	public void setStatCode(String statCode) {
		this.statCode = statCode;
	}
	
}
