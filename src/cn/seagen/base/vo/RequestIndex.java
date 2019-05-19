package cn.seagen.base.vo;

/** 报表请求结构体 */
public class RequestIndex {
	// 报表ID
	protected int id = 0;
	// 外部接入应用编码
	protected String app_code = "";
	// 临时时间戳
	protected long timestamp = 0;

	//
	protected String message = "";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApp_code() {
		return app_code;
	}

	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "RequestIndex [id=" + id + ", app_code=" + app_code
				+ ", timestamp=" + timestamp + ", message=" + message + "]";
	}

}
