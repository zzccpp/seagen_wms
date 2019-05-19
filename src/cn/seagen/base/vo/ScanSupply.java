package cn.seagen.base.vo;
/**
 * 导入台快手VO
 * @author zcp
 */
public class ScanSupply {

	/**导入台编号*/
	private String supplyNo;
	/**导入台上件峰值1分钟*/
	private int spikeNum;
	/**导入台连续60分钟峰值*/
	private int sixSpikeNum;
	/**导入台上件总量*/
	private int allCount;
	/**导入台上件有效操作时间*/
	private int valid_time;
	/**页面显示*/
	private String validTime;
	/**导入台上件效率*/
	private String efficiency;
	
	public ScanSupply() {}
	public ScanSupply(String supplyNo, int spikeNum,int sixSpikeNum, int allCount,
			String validTime, String efficiency) {
		this.supplyNo = supplyNo;
		this.spikeNum = spikeNum;
		this.sixSpikeNum = sixSpikeNum;
		this.allCount = allCount;
		this.validTime = validTime;
		this.efficiency = efficiency;
	}
	public String getSupplyNo() {
		return supplyNo;
	}
	public void setSupplyNo(String supplyNo) {
		this.supplyNo = supplyNo;
	}
	public int getSpikeNum() {
		return spikeNum;
	}
	public void setSpikeNum(int spikeNum) {
		this.spikeNum = spikeNum;
	}
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public int getValid_time() {
		return valid_time;
	}
	public void setValid_time(int valid_time) {
		this.valid_time = valid_time;
	}
	public String getValidTime() {
		return validTime;
	}
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}
	public String getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(String efficiency) {
		this.efficiency = efficiency;
	}
	public int getSixSpikeNum() {
		return sixSpikeNum;
	}
	public void setSixSpikeNum(int sixSpikeNum) {
		this.sixSpikeNum = sixSpikeNum;
	}
	@Override
	public String toString() {
		return "ScanSupply [supplyNo=" + supplyNo + ", spikeNum=" + spikeNum
				+ ", allCount=" + allCount + ", valid_time=" + valid_time
				+ ", validTime=" + validTime + ", efficiency=" + efficiency
				+ "]";
	}
}
