package org.rhm.stock.batch;

import java.util.Date;

public class BatchStatus {
	private String jobClass = null;
	private Boolean success = false;
	private String completionMsg = null;
	private Date statusDate = null;
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getCompletionMsg() {
		return completionMsg;
	}
	public void setCompletionMsg(String completionMsg) {
		this.completionMsg = completionMsg;
	}
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
}
