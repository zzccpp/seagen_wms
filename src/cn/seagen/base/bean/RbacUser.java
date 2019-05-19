package cn.seagen.base.bean;

/** 操作员 */
public class RbacUser {
	/** 主键 */
	private int id = 0;
	/** 用户名称 len32 */
	private String userName = "";
	/** 用户密码 len32 */
	private String pwd = "";
	/** 电话 len15 */
	private String telNo;
	/** 邮箱 len48 */
	private String email;
	/** 启动状态，0启用 1不启用 */
	private String useFlag;
	/** 备注 len100 */
	private String remark;
	/** 创建时间 0000-00-00 00:00:00 */
	private String createTime;
	/** 修改时间 0000-00-00 00:00:00 */
	private String modifyTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
		return "RbacUser [id=" + id + ", userName=" + userName + ", pwd=" + pwd
				+ ", telNo=" + telNo + ", email=" + email + ", useFlag="
				+ useFlag + ", remark=" + remark + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + "]";
	}
}
