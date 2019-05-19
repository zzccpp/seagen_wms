package cn.seagen.base.bean;

public class ChuteArea {
	private int fRecno;
	private String areaName;
	private String chuteNumList;
	private String updateTime;
	private String reMark;
	private String createTime;
	private String modifyTime;
	
	public int getfRecno() {
		return fRecno;
	}
	public void setfRecno(int fRecno) {
		this.fRecno = fRecno;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getChuteNumList() {
		return chuteNumList;
	}
	public void setChuteNumList(String chuteNumList) {
		this.chuteNumList = chuteNumList;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
		return "ChuteArea [fRecno=" + fRecno + ", areaName=" + areaName
				+ ", chuteNumList=" + chuteNumList + ", updateTime="
				+ updateTime + ", reMark=" + reMark + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + "]";
	}
}
