package org.rhm.stock.domain;

import org.springframework.data.annotation.Id;

public class StatisticType {
	@Id
	private String statisticCode = null;
	private String statisticDesc = null;
	private String className = null;
	public String getStatisticCode() {
		return statisticCode;
	}
	public void setStatisticCode(String statisticCode) {
		this.statisticCode = statisticCode;
	}
	public String getStatisticDesc() {
		return statisticDesc;
	}
	public void setStatisticDesc(String statisticDesc) {
		this.statisticDesc = statisticDesc;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
}
