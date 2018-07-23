package org.rhm.stock.batch;

import java.util.Date;

public class BatchStatus {
	private String jobClass = null;
	private Boolean success = false;
	private String completionMsg = null;
	private Date statusDate = null;
	private Date startDate = null;
	private Date finishDate = null;
	
	public BatchStatus(Class cls) {
		this.jobClass = cls.getName();
		this.startDate = new Date();
	}
	
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
}
