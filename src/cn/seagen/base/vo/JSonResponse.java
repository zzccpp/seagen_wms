package cn.seagen.base.vo;
/**
 * JSON页面回复实体类
 * @author zcp
 *
 */
public class JSonResponse {

	private int code;
	private String message;
	
	public JSonResponse() {
		super();
	}
	public JSonResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "JSonResponse [code=" + code + ", message=" + message + "]";
	}
}
