package org.rhm.stock.controller.dto;

import java.util.List;

public class SignalRequest {

	private List<String> signalTypeList = null;
	private Integer lookBackDays = null;

	public List<String> getSignalTypeList() {
		return signalTypeList;
	}

	public void setSignalTypeList(List<String> signalTypeList) {
		this.signalTypeList = signalTypeList;
	}

	public Integer getLookBackDays() {
		return lookBackDays;
	}

	public void setLookBackDays(Integer lookBackDays) {
		this.lookBackDays = lookBackDays;
	}
	

}
