package cn.seagen.base.bean;

/** 系统设置表 */
public class SystemSet {
	/** 主键 */
	private int fRecno;
	/** 参数中文名称 */
	private String setNameCn;
	/** 参数英文名称 */
	private String setName;
	/** 参数值 */
	private String setValue;
	/** 备注 */
	private String setMark;
	/** 生成时间 */
	private String createTime;
	/** 修改时间 */
	private String modifyTime;

	public int getfRecno() {
		return fRecno;
	}

	public void setfRecno(int fRecno) {
		this.fRecno = fRecno;
	}

	public String getSetNameCn() {
		return setNameCn;
	}

	public void setSetNameCn(String setNameCn) {
		this.setNameCn = setNameCn;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getSetValue() {
		return setValue;
	}

	public void setSetValue(String setValue) {
		this.setValue = setValue;
	}

	public String getSetMark() {
		return setMark;
	}

	public void setSetMark(String setMark) {
		this.setMark = setMark;
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
		return "SystemSet [fRecno=" + fRecno + ", setNameCn=" + setNameCn + ", setName=" + setName + ", setValue=" + setValue + ", setMark=" + setMark + ", createTime=" + createTime + ", modifyTime="
				+ modifyTime + "]";
	}

}
