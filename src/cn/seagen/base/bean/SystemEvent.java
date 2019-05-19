package cn.seagen.base.bean;
/**
 * 系统事件
 * @author zcp
 */
public class SystemEvent {
	/**主键*/
	private long fRecno;
	/**事件代号*/
	private int eventId;
	/**事件级别*/
    private int eventLevel;
    /**事件类型*/
    private String eventType;
    /**事件名称*/
    private String eventName;
	/**事件属性*/
	private String eventVal;
	/**事件说明*/
	private String eventMark;
	/**事件时间*/
	private String eventTime;
	/**事件状态*/
	private String eventStatus;
	/**上传事件*/
	private String uploadTime;
	/**线体层级*/
	private int layerId;
	
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public long getfRecno() {
		return fRecno;
	}
	public void setfRecno(long fRecno) {
		this.fRecno = fRecno;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public int getEventLevel() {
		return eventLevel;
	}
	public void setEventLevel(int eventLevel) {
		this.eventLevel = eventLevel;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventVal() {
		return eventVal;
	}
	public void setEventVal(String eventVal) {
		this.eventVal = eventVal;
	}
	public String getEventMark() {
		return eventMark;
	}
	public void setEventMark(String eventMark) {
		this.eventMark = eventMark;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public int getLayerId() {
		return layerId;
	}
	public void setLayerId(int layerId) {
		this.layerId = layerId;
	}
	@Override
	public String toString() {
		return "SystemEvent [fRecno=" + fRecno + ", eventId=" + eventId
				+ ", eventLevel=" + eventLevel + ", eventType=" + eventType
				+ ", eventName=" + eventName + ", eventVal=" + eventVal
				+ ", eventMark=" + eventMark + ", eventTime=" + eventTime
				+ ", eventStatus=" + eventStatus + ", uploadTime=" + uploadTime
				+ ", layerId=" + layerId + "]";
	}
	
}
