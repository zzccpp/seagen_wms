package cn.seagen.base.vo;

public class SixSpike {
	private String reportDate;
	private int scanNum;
	
	public SixSpike() {}
	public SixSpike(String reportDate, int scanNum) {
		this.reportDate = reportDate;
		this.scanNum = scanNum;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public int getScanNum() {
		return scanNum;
	}
	public void setScanNum(int scanNum) {
		this.scanNum = scanNum;
	}
	@Override
	public String toString() {
		return "SixSpike [reportDate=" + reportDate + ", scanNum=" + scanNum
				+ "]";
	}
}
