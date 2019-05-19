package cn.seagen.base.bean;

public class Printer {
	/**ID*/
	private int fRecno;
	/**打印机编码*/
	private String printerNum;
	/**打印机的Ip*/
	private String printerIp;
	/**格口集合，格口之间逗号分隔*/
	private String chuteNumList;
	/**备注*/
	private String reMark;
	/** 创建时间 0000-00-00 00:00:00 */
	private String createTime;
	/** 修改时间 0000-00-00 00:00:00 */
	private String modifyTime;
	public int getfRecno() {
		return fRecno;
	}
	public void setfRecno(int fRecno) {
		this.fRecno = fRecno;
	}
	public String getPrinterNum() {
		return printerNum;
	}
	public void setPrinterNum(String printerNum) {
		this.printerNum = printerNum;
	}
	public String getPrinterIp() {
		return printerIp;
	}
	public void setPrinterIp(String printerIp) {
		this.printerIp = printerIp;
	}
	public String getChuteNumList() {
		return chuteNumList;
	}
	public void setChuteNumList(String chuteNumList) {
		this.chuteNumList = chuteNumList;
	}
	public String getReMark() {
		return reMark;
	}
	public void setReMark(String reMark) {
		this.reMark = reMark;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	public String toString() {
		return "Printer [fRecno=" + fRecno + ", printerNum=" + printerNum
				+ ", printerIp=" + printerIp + ", chuteNumList=" + chuteNumList
				+ ", reMark=" + reMark + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + "]";
	}
}
