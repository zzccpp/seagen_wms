package cn.seagen.base.bean;

/**
 * 系统日志实体
 */
public class SystemLog {
	/** 主键 */
	private long fRecno;
	/** 终端IP*/
	private String ip;
	/** 用户名 */
	private String userName;
	/** Controller方法名 */
	private String methodName;
	/** 类型 0正常   1异常 */
	private int type;
	/** (方法参数值)*/
	private String methodArgs;
	/** 备注 (异常消息等)*/
	private String logMark;
	/** 耗时*/
	private int time;
	/** 生成时间 */
	private String createTime;
	/** 修改时间 */
	private String modifyTime;
	public long getfRecno() {
		return fRecno;
	}
	public void setfRecno(long fRecno) {
		this.fRecno = fRecno;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getLogMark() {
		return logMark;
	}
	public void setLogMark(String logMark) {
		this.logMark = logMark;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
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
	public String getMethodArgs() {
		return methodArgs;
	}
	public void setMethodArgs(String methodArgs) {
		this.methodArgs = methodArgs;
	}
	@Override
	public String toString() {
		return "SystemLog [fRecno=" + fRecno + ", ip=" + ip + ", userName="
				+ userName + ", methodName=" + methodName + ", type=" + type
				+ ", methodArgs=" + methodArgs + ", logMark=" + logMark
				+ ", time=" + time + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + "]";
	}
}
